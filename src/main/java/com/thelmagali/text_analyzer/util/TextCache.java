package com.thelmagali.text_analyzer.util;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TextCache {
  private static final TextCache INSTANCE = new TextCache();
  private final Set<String> textSet;

  private TextCache() {
    textSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
  }

  public static TextCache getInstance() {
    return INSTANCE;
  }

  public void add(String str) {
    textSet.add(str);
  }

  public boolean contains(String str) {
    return textSet.contains(str);
  }

  public Set<String> getAll() {
    return Collections.unmodifiableSet(textSet);
  }

  public int size() {
    return textSet.size();
  }
}
