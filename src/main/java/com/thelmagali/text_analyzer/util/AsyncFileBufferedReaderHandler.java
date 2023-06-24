package com.thelmagali.text_analyzer.util;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AsyncFileBufferedReaderHandler implements Handler<AsyncResult<AsyncFile>> {
  private final int CHUNK_SIZE;
  private final Set<String> textSet;
  private final Promise<Set<String>> promise;

  public AsyncFileBufferedReaderHandler(Promise<Set<String>> promise, int CHUNK_SIZE) {
    this.CHUNK_SIZE = CHUNK_SIZE;
    this.textSet = new HashSet<>();
    this.promise = promise;
  }

  @Override
  public void handle(AsyncResult<AsyncFile> asyncResult) {
    if (asyncResult.succeeded()) {
      AsyncFile file = asyncResult.result();
      final Buffer[] lineBuffer = {Buffer.buffer()};

      file.handler(buffer -> {
        lineBuffer[0].appendBuffer(buffer);

        if (lineBuffer[0].length() >= CHUNK_SIZE) {
          Buffer chunk = lineBuffer[0].getBuffer(0, CHUNK_SIZE);
          lineBuffer[0] = lineBuffer[0].getBuffer(CHUNK_SIZE, lineBuffer[0].length());
          processChunk(chunk);
        }
      });

      file.endHandler(v -> {
        processChunk(lineBuffer[0]); // lineBuffer[0] contains the last chunk
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
  }

  private void processChunk(Buffer chunk) {
    String chunkData = chunk.toString();
    String[] lines = chunkData.split("\n");
    textSet.addAll(Arrays.asList(lines));
  }
}
