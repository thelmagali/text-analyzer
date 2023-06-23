package com.thelmagali.text_analyzer.service;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.impl.ConcurrentHashSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FileService {
  private static final String fileName = "texts.txt";
  private static final int CHUNK_SIZE = 4096;

  public static Future<Set<String>> getTextSet(Vertx vertx) {
    Promise<Set<String>> promise = Promise.promise();
    Set<String> textSet = new HashSet<>();

    vertx.fileSystem().open(fileName, new OpenOptions().setRead(true), asyncResult -> {
      if (asyncResult.succeeded()) {
        AsyncFile file = asyncResult.result();
        final Buffer[] lineBuffer = {Buffer.buffer()};

        file.handler(buffer -> {
          lineBuffer[0].appendBuffer(buffer);

          if (lineBuffer[0].length() >= CHUNK_SIZE) {
            Buffer chunk = lineBuffer[0].getBuffer(0, CHUNK_SIZE);
            lineBuffer[0] = lineBuffer[0].getBuffer(CHUNK_SIZE, lineBuffer[0].length());
            processChunk(textSet, chunk);
          }
        });

        file.endHandler(v -> {
          processChunk(textSet, lineBuffer[0]); // lineBuffer[0] contains the last chunk
          promise.complete(textSet);
          file.close();
        });

        file.exceptionHandler(throwable -> {
          promise.fail(throwable);
          file.close();
        });
      } else {
        promise.fail(asyncResult.cause());
      }
    });

    return promise.future();
  }

  private static void processChunk(Set<String> textSet, Buffer chunk) {
    String chunkData = chunk.toString();
    String[] lines = chunkData.split("\n");
    textSet.addAll(Arrays.asList(lines));
  }

  public static Future<Void> writeToFile(Vertx vertx, Set<String> texts) {
    return vertx
      .fileSystem()
      .open(fileName, new OpenOptions().setTruncateExisting(true).setAppend(true))
      .onSuccess(asyncFile -> texts.forEach(text -> asyncFile.write(Buffer.buffer(text + '\n')))
      ).flatMap(AsyncFile::close);
  }
}
