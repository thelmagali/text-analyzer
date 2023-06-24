package com.thelmagali.text_analyzer.service;

import com.thelmagali.text_analyzer.util.AsyncFileBufferedReaderHandler;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import java.util.Set;

public class FileService {
  private static final String fileName = "texts.txt";

  public static Future<Set<String>> getTextSet(Vertx vertx) {
    Promise<Set<String>> promise = Promise.promise();
    vertx.fileSystem()
      .open(fileName, new OpenOptions().setRead(true), new AsyncFileBufferedReaderHandler(promise));
    return promise.future();
  }

  public static Future<Void> writeToFile(Vertx vertx, Set<String> texts) {
    return vertx
      .fileSystem()
      .open(fileName, new OpenOptions().setTruncateExisting(true).setAppend(true))
      .onSuccess(asyncFile -> texts.forEach(text -> asyncFile.write(Buffer.buffer(text + '\n')))
      ).flatMap(AsyncFile::close);
  }
}
