package com.thelmagali.text_analyzer.comparator;

import com.thelmagali.text_analyzer.util.LexicalDistance;

public class LexicalDistanceComparator extends TextComparator {

  public LexicalDistanceComparator(String referenceString) {
    super(referenceString);
  }

  @Override
  protected int distance(String text1, String text2) {
    return LexicalDistance.distance(text1, text2);
  }
}
