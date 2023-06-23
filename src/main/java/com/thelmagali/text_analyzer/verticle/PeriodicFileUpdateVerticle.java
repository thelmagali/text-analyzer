package com.thelmagali.text_analyzer.verticle;

import com.thelmagali.text_analyzer.service.CacheService;
import io.vertx.core.AbstractVerticle;

public class PeriodicFileUpdateVerticle extends AbstractVerticle {
  @Override
  public void start() {
    long initialDelay = 30000; // 30 seconds
    long delay = 10000; // 10 seconds
    vertx.setPeriodic(initialDelay, delay, handler -> {
      System.out.println("Executing periodic file update...");
      // Code to execute periodically
      CacheService.writeCacheToFile(vertx)
        .onSuccess(_void -> System.out.println("File update completed successfully"));
    });
  }
}
