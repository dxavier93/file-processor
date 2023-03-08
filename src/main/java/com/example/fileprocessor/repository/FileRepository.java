package com.example.fileprocessor.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.example.fileprocessor.util.Utils.COUNTRY_FILE;
import static com.example.fileprocessor.util.Utils.INPUT_FILE;

@Repository
@Slf4j
public class FileRepository {
  private final Path pathCountry;
  private final Path pathInput;

  public FileRepository(Optional<Path> pathCountry, Optional<Path> pathInput) {
    this.pathCountry = pathCountry.orElse(Path.of(COUNTRY_FILE));
    this.pathInput = pathInput.orElse(Path.of(INPUT_FILE));
  }

  public List<String> findAllCountries() {
    return findAll(pathCountry);
  }

  public List<String> findAllInputs() {
    return findAll(pathInput);
  }

  private List<String>  findAll(Path path){
    try {
      return Files.lines(path).toList();
    } catch (IOException ex) {
      log.error("Error at trying to read all rows in the file.");
      return null;
    }
  }
}
