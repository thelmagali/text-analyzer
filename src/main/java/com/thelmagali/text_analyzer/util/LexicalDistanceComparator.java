package com.thelmagali.text_analyzer.util;

import com.thelmagali.text_analyzer.service.LexicalDistanceCalculator;
import com.thelmagali.text_analyzer.service.ValueDistanceCalculator;
import java.util.Comparator;

public class LexicalDistanceComparator implements Comparator<String> {
  private final String referenceString;

  public LexicalDistanceComparator(String referenceString) {
    this.referenceString = referenceString;
  }


  @Override
  public int compare(String o1, String o2) {
    var value1 = LexicalDistanceCalculator.distance(o1, referenceString);
    var value2 = LexicalDistanceCalculator.distance(o2, referenceString);
    return Integer.compare(value1, value2);
  }
}
