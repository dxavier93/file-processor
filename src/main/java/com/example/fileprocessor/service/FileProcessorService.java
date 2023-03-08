package com.example.fileprocessor.service;


import com.example.fileprocessor.model.CountryCode;
import com.example.fileprocessor.model.PhoneNumber;
import com.example.fileprocessor.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.example.fileprocessor.util.Utils.CODE_IDX;
import static com.example.fileprocessor.util.Utils.COUNTRY_IDX;
import static com.example.fileprocessor.util.Utils.INPUT_REGEX;
import static com.example.fileprocessor.util.Utils.REGEX;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessorService {
  private final FileRepository fileRepository;

  public void process() {
    LocalTime initialTime = LocalTime.now();
    System.out.println("Performing file processor at " + initialTime);

    List<String> countryLines = fileRepository.findAllCountries();

    List<String> inputLines = fileRepository.findAllInputs();

    List<CountryCode> countryCodes = countryLines.stream()
        .map(this::getCountryCodeByLine)
        .toList();

    List<PhoneNumber> phoneNumbers = inputLines.stream()
        .map(it -> processInput(countryCodes, it))
        .toList();

    Map<String, Integer> mapPhones = phoneNumbers.stream()
        .collect(groupingBy(PhoneNumber::getCountry))
        .entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()
        ));

    Map<String, Integer> sortedMapPhones = mapPhones.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(comparator());

    sortedMapPhones.forEach((key, value) -> System.out.println((String.format("%s:%s", key, value))));

    System.out.println(String.format("File processed in [%s] seconds", initialTime.until(LocalTime.now(), ChronoUnit.MILLIS)));
  }

  private static Collector<Map.Entry<String, Integer>, ?, LinkedHashMap<String, Integer>> comparator() {
    return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
        (e1, e2) -> e1, LinkedHashMap::new);
  }

  private String getCountryByCode(List<CountryCode> countryCodes, String code) {
    return countryCodes.stream()
        .filter(it -> it.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(""))
        .getCountry();
  }

  private PhoneNumber processInput(List<CountryCode> countryCodes, String rawLine) {
    String line = rawLine.replace("+", "");
    int length = line.replace(" ", "").length();
    int index = 0;
    String phone = line;
    String countryCode = "999";

    // short portuguese number
    if (length >= 4 && length <= 6) {
      if (!line.startsWith("0"))
        return new PhoneNumber(getCountryByCode(countryCodes, "351"), line);
    }

    // long number
    if (length >= 9 && length <= 14) {
      if (line.contains(INPUT_REGEX) && !line.startsWith(INPUT_REGEX)) {
        index = line.indexOf(INPUT_REGEX);
        countryCode = line.substring(0, index);
      } else if (line.startsWith("00")) {
        index = ("00").length();
        countryCode = "351";
      }

      phone = line.substring(index, length).replace(" ", "");

    }

    return new PhoneNumber(getCountryByCode(countryCodes, countryCode), phone);
  }

  private CountryCode getCountryCodeByLine(String it) {
    String[] split = it.split(REGEX);
    return new CountryCode(split[COUNTRY_IDX], split[CODE_IDX]);
  }
}


