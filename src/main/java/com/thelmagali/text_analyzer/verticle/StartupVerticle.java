package com.thelmagali.text_analyzer.verticle;

import com.thelmagali.text_analyzer.service.CacheService;
import io.vertx.core.AbstractVerticle;

public class StartupVerticle extends AbstractVerticle {

  @Override
  public void start() {
    CacheService.loadCache(vertx);
    System.out.println("Startup code executed.");
  }
}
