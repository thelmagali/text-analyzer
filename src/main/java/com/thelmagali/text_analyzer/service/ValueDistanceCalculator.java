package com.thelmagali.text_analyzer.service;

public class ValueDistanceCalculator {
  public static Integer distance(String referenceText, String text) {
    var distance = 0;
    for (int i = 0; i < Math.max(referenceText.length(), text.length()); i++) {
      int referenceCharValue = (i < referenceText.length()) ? referenceText.charAt(i) : 0;
      int textCharValue = (i < text.length()) ? text.charAt(i) : 0;
      distance =+ Math.abs(referenceCharValue - textCharValue);
    }
    return distance;
  }
}
