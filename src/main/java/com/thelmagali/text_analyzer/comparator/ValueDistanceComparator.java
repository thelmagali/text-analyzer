package com.thelmagali.text_analyzer.comparator;

import com.thelmagali.text_analyzer.util.ValueDistance;

public class ValueDistanceComparator extends TextComparator {
  public ValueDistanceComparator(String referenceString) {
    super(referenceString);
  }

  @Override
  protected int distance(String text1, String text2) {
    return ValueDistance.distance(text1, text2);
  }
}
