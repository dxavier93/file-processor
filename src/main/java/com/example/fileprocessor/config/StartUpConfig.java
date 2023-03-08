package com.example.fileprocessor.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.example.fileprocessor.util.Utils.COUNTRY_FILE;
import static com.example.fileprocessor.util.Utils.INPUT_FILE;

@Component
public class StartUpConfig implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    File countryFile = new File(COUNTRY_FILE);
    File inputFile = new File(INPUT_FILE);
  }
}