package com.thelmagali.text_analyzer.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class PeriodicTaskVerticle extends AbstractVerticle {
  @Override
  public void start() {
    long delay = 30000; // 30 seconds
    vertx.setPeriodic(delay, handler -> {
      // Code to execute periodically
      System.out.println("Executing periodic task...");
    });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new PeriodicTaskVerticle());
  }
}
