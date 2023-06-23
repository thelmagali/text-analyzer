package com.thelmagali.text_analyzer.util;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextCache {
  private static final TextCache INSTANCE = new TextCache();
  private Set<String> textSet;

  private TextCache() {
    initSet();
  }

  public static TextCache getInstance() {
    return INSTANCE;
  }

  public void add(String str) {
    textSet.add(str);
  }

  public void add(Stream<String> stream) {
    textSet.addAll(stream.collect(Collectors.toSet()));
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

  private void initSet() {
    textSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
  }
}
