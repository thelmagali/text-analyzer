package com.thelmagali.text_analyzer.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import java.util.Set;

public class FileService {
  private static final String fileName = "texts.txt";

  public static Future<Set<String>> getTextSet(Vertx vertx) {
    return vertx.fileSystem().readFile(fileName)
      .map(String::valueOf)
      .map(str -> str.split("\n"))
      .map(Set::of);
  }

  public static Future<Void> writeToFile(Vertx vertx, Set<String> texts) {
    return vertx
      .fileSystem()
      .open(fileName, new OpenOptions().setTruncateExisting(true).setAppend(true))
      .onSuccess(asyncFile -> texts.forEach(text -> asyncFile.write(Buffer.buffer(text + '\n')))
      ).flatMap(AsyncFile::close);
  }
}
