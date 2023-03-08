package com.example.fileprocessor.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.example.fileprocessor.util.Utils.FILE_PATH;

@Component
public class StartUpConfig implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    File file = new File(FILE_PATH);
  }
}