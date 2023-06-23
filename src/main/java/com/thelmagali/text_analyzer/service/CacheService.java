package com.thelmagali.text_analyzer.service;

import com.thelmagali.text_analyzer.util.TextCache;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;

public class CacheService {
  private static final String fileName = "texts.txt";

  public static void loadCache(Vertx vertx) {
    System.out.println("Starting cache loading");
    var cache = TextCache.getInstance();
    final Buffer[] lineBuffer = {Buffer.buffer()};

    vertx.fileSystem()
      .open(fileName, new OpenOptions().setRead(true))
      .onSuccess(file -> {
        file.handler(chunk -> processChunk(cache, lineBuffer[0], chunk));
        file.endHandler(v -> processPartialLine(lineBuffer[0], cache));
      });
  }

  private static void processChunk(TextCache cache, Buffer lineBuffer, Buffer chunk) {
    lineBuffer.appendBuffer(chunk);

    while (true) {
      var lineBufferData = lineBuffer.toString();
      int lineBreakIndex = lineBufferData.indexOf("\n");
      if (lineBreakIndex == -1) {
        break;
      }
      String line = lineBufferData.substring(0, lineBreakIndex);
      cache.add(line);

      // Remove the processed line from the line buffer
      lineBuffer = lineBuffer.getBuffer(lineBreakIndex + 1, lineBuffer.length());
    }
  }

  private static void processPartialLine(Buffer lineBuffer, TextCache cache) {
    System.out.println("Processing partial line!");
    if (lineBuffer.length() > 0) {
      String partialLine = lineBuffer.toString();
      cache.add(partialLine);
    }
  }

  public static Future<Void> writeCacheToFile(Vertx vertx) {
    return vertx
      .fileSystem()
      .open(fileName, new OpenOptions().setTruncateExisting(true).setAppend(true))
      .onSuccess(asyncFile ->
        TextCache.getInstance().getAll().forEach(text -> asyncFile.write(Buffer.buffer(text + '\n')))
      ).flatMap(AsyncFile::close);
  }
}
