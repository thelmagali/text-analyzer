package com.thelmagali.text_analyzer.dto;

public class AnalysisDTO {
  private final String value;

  private final String lexical;

  public AnalysisDTO(String value, String lexical) {
    this.value = value;
    this.lexical = lexical;
  }

  public String getValue() {
    return value;
  }

  public String getLexical() {
    return lexical;
  }
}
