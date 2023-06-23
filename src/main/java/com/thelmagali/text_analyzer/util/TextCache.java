package com.thelmagali.text_analyzer.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TextCache {
  private static final TextCache INSTANCE = new TextCache();
  private Set<String> textSet;

  private TextCache() {
    initSet();
  }

  public static TextCache getInstance() {
    return INSTANCE;
  }

  public void load(String[] texts) {
    initSet();
    textSet.addAll(List.of(texts));
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

  private void initSet() {
    textSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
  }
}
