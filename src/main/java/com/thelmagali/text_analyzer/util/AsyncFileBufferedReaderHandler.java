package com.thelmagali.text_analyzer.util;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AsyncFileBufferedReaderHandler implements Handler<AsyncResult<AsyncFile>> {
  private final int chunkSize;
  private final Set<String> textSet;
  private final Promise<Set<String>> promise;
  private String partialLine = "";

  public AsyncFileBufferedReaderHandler(Promise<Set<String>> promise, int chunkSize) {
    this.chunkSize = chunkSize;
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

        while (lineBuffer[0].length() >= chunkSize) {
          Buffer chunk = lineBuffer[0].getBuffer(0, chunkSize);
          processChunk(chunk);
          lineBuffer[0] = lineBuffer[0].getBuffer(chunkSize, lineBuffer[0].length());
        }
      });

      file.endHandler(v -> {
        processChunk(lineBuffer[0]); // lineBuffer[0] contains the last chunk
        if (!Objects.equals(partialLine, "")) {
          textSet.add(partialLine);
        }
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
    processStringChunk(chunkData);
  }

  private void processStringChunk(String chunkData) {
    String[] lines = chunkData.split("\n");
    if (lines.length > 0) {
      lines[0] = partialLine + lines[0];
      if (chunkData.endsWith("\n")) {
        // if the chunk ends with /n, there is no partial line, and we can add all the lines
        textSet.addAll(Arrays.asList(lines));
        partialLine = "";
      } else if (lines.length > 1) {
        // if there is more than 1 line, add all but the last one (which is partial)
        partialLine = lines[lines.length - 1];
        textSet.addAll(Arrays.asList(lines).subList(0, lines.length - 1));
      } else {
        // if there is only 1 line, don't add it, as it is a partial line
        partialLine = lines[0];
      }
    } else if (chunkData.equals("\n")) {
      if (!Objects.equals(partialLine, "")) {
        textSet.add(partialLine);
        partialLine = "";
      }
    }
  }
}
