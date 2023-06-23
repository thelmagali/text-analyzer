package com.thelmagali.text_analyzer.service;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import java.util.HashSet;
import java.util.Set;

public class FileService {
  private static final String fileName = "texts.txt";

  public static Future<Set<String>> getTextSet(Vertx vertx) {
    System.out.println("Starting cache loading");
    final Buffer[] lineBuffer = {Buffer.buffer()};
    Set<String> textSet = new HashSet<>();
    Promise<Set<String>> setPromise = Promise.promise();

    vertx.fileSystem()
      .open(fileName, new OpenOptions().setRead(true))
      .onSuccess(file -> {
        file.handler(chunk -> processChunk(textSet, lineBuffer[0], chunk));
        file.endHandler(v -> processLastLine(lineBuffer[0], textSet, setPromise));
      });

    return setPromise.future();
  }

  private static void processChunk(Set<String> textSet, Buffer lineBuffer, Buffer chunk) {
    System.out.println("Processing a chunk.");
    lineBuffer.appendBuffer(chunk);

    while (true) {
      var lineBufferData = lineBuffer.toString();
      int lineBreakIndex = lineBufferData.indexOf("\n");
      if (lineBreakIndex == -1) {
        break;
      }
      String line = lineBufferData.substring(0, lineBreakIndex);
      textSet.add(line);
      // Remove the processed line from the line buffer
      lineBuffer = lineBuffer.getBuffer(lineBreakIndex + 1, lineBuffer.length());
    }
  }

  private static void processLastLine(Buffer lineBuffer, Set<String> textSet, Promise<Set<String>> promise) {
    System.out.println("Processing the last chunk of the file.");
    if (lineBuffer.length() > 0) {
      String partialLine = lineBuffer.toString();
      textSet.add(partialLine);
      promise.complete(textSet);
    }
  }

  public static Future<Void> writeToFile(Vertx vertx, Set<String> texts) {
    return vertx
      .fileSystem()
      .open(fileName, new OpenOptions().setTruncateExisting(true).setAppend(true))
      .onSuccess(asyncFile -> texts.forEach(text -> asyncFile.write(Buffer.buffer(text + '\n')))
      ).flatMap(AsyncFile::close);
  }
}
