package com.thelmagali.text_analyzer.service;

import com.thelmagali.text_analyzer.comparator.LexicalDistanceComparator;
import com.thelmagali.text_analyzer.comparator.ValueDistanceComparator;
import com.thelmagali.text_analyzer.dto.AnalysisDTO;
import com.thelmagali.text_analyzer.util.TextCache;
import java.util.Set;

public class AnalysisService {
  public static AnalysisDTO analyzeAndSave(String newText) {
    var textCache = TextCache.getInstance();
    if (textCache.contains(newText)) {
      return new AnalysisDTO(newText, newText);
    }
    var result = analyze(textCache.getAll(), newText);
    textCache.add(newText);
    return result;
  }

  private static AnalysisDTO analyze(Set<String> textSet, String newText) {
    var valueDistanceComparator = new ValueDistanceComparator(newText);
    var lexicalDistanceComparator = new LexicalDistanceComparator(newText);
    var closestByValue = textSet.parallelStream().min(valueDistanceComparator).orElse(null);
    var closestByLexical = textSet.parallelStream().min(lexicalDistanceComparator).orElse(null);
    return new AnalysisDTO(closestByValue, closestByLexical);
  }
}
