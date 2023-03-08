package com.example.fileprocessor.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.example.fileprocessor.util.Utils.FILE_PATH;

@Repository
@Slf4j
public class FileRepository {
  private final Path path;

  public FileRepository(Optional<Path> path) {
    this.path = path.orElse(Path.of(FILE_PATH));
  }

  public List<String> findAll() {
    try {
      return Files.lines(path).toList();
    } catch (IOException ex) {
      log.error("Error at trying to read all rows in the file.");
      return null;
    }
  }
}
