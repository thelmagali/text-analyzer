package com.thelmagali.text_analyzer.util;

import com.thelmagali.text_analyzer.service.ValueDistanceCalculator;
import java.util.Comparator;

public class ValueDistanceComparator implements Comparator<String> {
  private final String referenceString;

  public ValueDistanceComparator(String referenceString) {
    this.referenceString = referenceString;
  }


  @Override
  public int compare(String o1, String o2) {
    var value1 = ValueDistanceCalculator.distance(o1, referenceString);
    var value2 = ValueDistanceCalculator.distance(o2, referenceString);
    return Integer.compare(value1, value2);
  }
}
