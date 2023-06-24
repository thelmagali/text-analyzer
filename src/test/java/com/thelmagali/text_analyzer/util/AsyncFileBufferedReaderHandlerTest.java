package com.thelmagali.text_analyzer.util;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.file.impl.FileSystemImpl;
import io.vertx.core.impl.VertxInternal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;


public class AsyncFileBufferedReaderHandlerTest {

  private Vertx vertx = Vertx.vertx();

  private FileSystem fileSystem = new FileSystemImpl((VertxInternal) vertx);

  @Test
  public void testAsyncFileHandler() throws InterruptedException {
    Promise<Set<String>> resultPromise = Promise.promise();
    AsyncFileBufferedReaderHandler myHandler = new AsyncFileBufferedReaderHandler(resultPromise, 1);
    String[] lines = new String[]{"Hello world", "another line"};
    String fileName = writeFile(lines);
    fileSystem.open(fileName, new OpenOptions().setRead(true), myHandler);

    Thread.sleep(200); // waiting for the future
    resultPromise.future().onSuccess(result -> {
      System.out.println(result);
      assert (result.size() == lines.length);
      assert (new ArrayList<>(result).containsAll(Arrays.stream(lines).collect(Collectors.toList())));
    });
  }

  private String writeFile(String[] lines) {
    var fileName = fileSystem.createTempFileBlocking("prefix", ".txt");
    StringJoiner joiner = new StringJoiner("\n");
    Arrays.stream(lines).forEach(joiner::add);
    Buffer buffer = Buffer.buffer(joiner.toString());
    fileSystem.writeFileBlocking(fileName, buffer);
    return fileName;
  }
}
