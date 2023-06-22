package com.thelmagali.text_analyzer.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.OpenOptions;
import java.util.Set;

public class FileService {
  private static final String fileName = "texts.txt";

  public static Future<Set<String>> getTexts(Vertx vertx) {
    return createIfNotExist(vertx).flatMap(_void ->
      vertx.fileSystem().readFile(fileName)
        .map(String::valueOf)
        .map(str -> str.split("\n"))
        .map(Set::of)
    );
  }

  public static void writeText(Vertx vertx, String text) {
    var file = vertx.fileSystem().open(fileName, new OpenOptions().setAppend(true));
    file.onSuccess(asyncFile -> asyncFile.write(Buffer.buffer(text + '\n')));
  }

  private static Future<Void> createIfNotExist(Vertx vertx) {
    return vertx.fileSystem().exists(fileName).flatMap(fileExists -> {
        if (!fileExists) {
          return vertx.fileSystem().createFile(fileName);
        } else {
          return Future.succeededFuture();
        }
      }
    );
  }
}
