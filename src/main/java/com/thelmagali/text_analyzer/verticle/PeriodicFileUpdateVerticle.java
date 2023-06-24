package com.thelmagali.text_analyzer.verticle;

import com.thelmagali.text_analyzer.service.FileService;
import com.thelmagali.text_analyzer.util.TextCache;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodicFileUpdateVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(PeriodicFileUpdateVerticle.class);

  @Override
  public void start() {
    long initialDelay = 30000; // 30 seconds
    long delay = 30000; // 30 seconds
    var cache = TextCache.getInstance();
    vertx.setPeriodic(initialDelay, delay, handler -> {
      logger.debug("Executing periodic file update...");
      if (cache.isReady()) {
        var textsToWrite = cache.getAll();
        FileService.writeToFile(vertx, textsToWrite)
          .onSuccess(_void -> logger.debug("File update completed successfully"));
      } else {
        logger.info("Skipping the file update. The cache is not ready.");
      }
    });
  }
}
