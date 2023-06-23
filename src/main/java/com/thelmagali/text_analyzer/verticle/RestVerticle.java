package com.thelmagali.text_analyzer.verticle;

import com.thelmagali.text_analyzer.service.AnalysisService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class RestVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> future) {

    Router router = Router.router(vertx);
    router.post("/analyze")
      .handler(this::analyzeAndSave);

    vertx.deployVerticle(new StartupVerticle());
    vertx.deployVerticle(new PeriodicFileUpdateVerticle());

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(config().getInteger("http.port", 8080), result -> {
        if (result.succeeded()) {
          future.complete();
        } else {
          future.fail(result.cause());
        }
      });
  }

  private void analyzeAndSave(RoutingContext routingContext) {
    routingContext.request().bodyHandler(bodyHandler -> {
        var body = (JsonObject) bodyHandler.toJson();
        var text = body.getString("text");
        var analysis = AnalysisService.analyzeAndSave(text);
        routingContext.response()
          .putHeader("content-type", "application/json")
          .setStatusCode(200)
          .end(Json.encodePrettily(analysis));
      }
    );
  }
}
