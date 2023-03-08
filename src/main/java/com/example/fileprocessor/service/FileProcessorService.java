package com.example.fileprocessor.service;


import com.example.fileprocessor.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessorService {
  private final FileRepository fileRepository;

  public void process() {
    log.info("...");
    LocalTime initialTime = LocalTime.now();

    List<String> fileLines = fileRepository.findAll();

    fileLines.forEach(line -> log.info(String.format("Info -> [%s]", line)));

    log.info(String.format("File processed in [%s] seconds", initialTime.until(LocalTime.now(), ChronoUnit.MILLIS)));
  }
}


