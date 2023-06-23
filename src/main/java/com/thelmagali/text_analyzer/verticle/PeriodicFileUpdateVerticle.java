package com.thelmagali.text_analyzer.verticle;

import com.thelmagali.text_analyzer.service.FileService;
import com.thelmagali.text_analyzer.util.TextCache;
import io.vertx.core.AbstractVerticle;

public class PeriodicFileUpdateVerticle extends AbstractVerticle {
  @Override
  public void start() {
    long initialDelay = 30000; // 30 seconds
    long delay = 30000; // 30 seconds
    var cache = TextCache.getInstance();
    vertx.setPeriodic(initialDelay, delay, handler -> {
      System.out.println("Executing periodic file update...");
      if (cache.isReady()) {
        var textsToWrite = cache.getAll();
        FileService.writeToFile(vertx, textsToWrite)
          .onSuccess(_void -> System.out.println("File update completed successfully"));
      } else {
        System.out.println("Skipping the file update. The cache is not ready.");
      }
    });
  }
}
