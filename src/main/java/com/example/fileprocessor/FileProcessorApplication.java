package com.example.fileprocessor;

import com.example.fileprocessor.service.FileProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@AllArgsConstructor
public class FileProcessorApplication {

	private FileProcessorService service;

	public static void main(String[] args) {
		SpringApplication.run(FileProcessorApplication.class, args).close();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx){
		return args -> {
			service.process();
		};
	}
}