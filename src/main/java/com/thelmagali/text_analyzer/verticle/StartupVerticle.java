package com.thelmagali.text_analyzer.verticle;

import com.thelmagali.text_analyzer.service.FileService;
import com.thelmagali.text_analyzer.util.TextCache;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartupVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(StartupVerticle.class);

  @Override
  public void start() {
    var cache = TextCache.getInstance();
    FileService.getTextSet(vertx)
      .onSuccess(textSet -> {
        cache.load(textSet);
        cache.setReady(true);
        logger.info("Startup code executed. The cache is ready.");
      });
  }
}
