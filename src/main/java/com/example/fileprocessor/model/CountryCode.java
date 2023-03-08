package com.example.fileprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryCode {
  String country;
  String code;
}
