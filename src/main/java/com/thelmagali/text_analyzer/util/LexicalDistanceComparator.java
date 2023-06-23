package com.thelmagali.text_analyzer.util;

import com.thelmagali.text_analyzer.service.LexicalDistanceCalculator;

public class LexicalDistanceComparator extends TextComparator {

  public LexicalDistanceComparator(String referenceString) {
    super(referenceString);
  }

  @Override
  protected int distance(String text1, String text2) {
    return LexicalDistanceCalculator.distance(text1, text2);
  }
}
