package com.thelmagali.text_analyzer.service;

import com.thelmagali.text_analyzer.dto.AnalysisDTO;
import com.thelmagali.text_analyzer.util.LexicalDistanceComparator;
import com.thelmagali.text_analyzer.util.ValueDistanceComparator;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.util.Objects;

public class AnalysisService {
  public static Future<AnalysisDTO> analyzeAndSave(Vertx vertx, String newText) {
    return analyze(vertx, newText).andThen(analysis -> {
      if (!Objects.equals(analysis.result().getValue(), newText)) {
        FileService.writeText(vertx, newText);
      }
    });
  }

  private static Future<AnalysisDTO> analyze(Vertx vertx, String newText) {
    var valueDistanceComparator = new ValueDistanceComparator(newText);
    var lexicalDistanceComparator = new LexicalDistanceComparator(newText);
    return FileService.getTexts(vertx).map(textSet -> {
      var closestByValue = textSet.parallelStream().min(valueDistanceComparator).orElse(null);
      var closestByLexical = textSet.parallelStream().min(lexicalDistanceComparator).orElse(null);
      return new AnalysisDTO(closestByValue, closestByLexical);
    });
  }
}
