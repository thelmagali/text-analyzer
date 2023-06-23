package com.thelmagali.text_analyzer.util;

import java.util.Comparator;

public abstract class TextComparator implements Comparator<String> {
  private final String referenceString;

  protected TextComparator(String referenceString) {
    this.referenceString = referenceString;
  }

  protected abstract int distance(String text1, String text2);

  @Override
  public final int compare(String o1, String o2) {
    var value1 = distance(o1, referenceString);
    var value2 = distance(o2, referenceString);
    return Integer.compare(value1, value2);
  }
}
