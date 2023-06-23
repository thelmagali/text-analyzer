package com.thelmagali.text_analyzer.util;

import io.vertx.core.impl.ConcurrentHashSet;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TextCache {
  private static final TextCache INSTANCE = new TextCache();
  private final Set<String> textSet;
  private boolean isReady;

  private TextCache() {
    textSet = new ConcurrentHashSet<>();
  }

  public static TextCache getInstance() {
    return INSTANCE;
  }

  public boolean isReady() {
    return this.isReady;
  }

  public void setReady(boolean isReady) {
    this.isReady = isReady;
  }

  public void load(Set<String> textSet) {
    this.textSet.addAll(textSet);
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
