package com.thelmagali.text_analyzer.util;

import com.thelmagali.text_analyzer.service.ValueDistanceCalculator;

public class ValueDistanceComparator extends TextComparator {
  public ValueDistanceComparator(String referenceString) {
    super(referenceString);
  }

  @Override
  protected int distance(String text1, String text2) {
    return ValueDistanceCalculator.distance(text1, text2);
  }
}
