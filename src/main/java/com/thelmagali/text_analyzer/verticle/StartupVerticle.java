package com.thelmagali.text_analyzer.verticle;

import com.thelmagali.text_analyzer.service.FileService;
import com.thelmagali.text_analyzer.util.TextCache;
import io.vertx.core.AbstractVerticle;

public class StartupVerticle extends AbstractVerticle {

  @Override
  public void start() {
    var cache = TextCache.getInstance();
    FileService.getTextSet(vertx)
      .onSuccess(textSet -> {
        cache.load(textSet);
        cache.setReady(true);
        System.out.println("Startup code executed. The cache is ready.");
      });
  }
}
