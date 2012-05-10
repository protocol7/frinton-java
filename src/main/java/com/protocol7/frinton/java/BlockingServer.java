package com.protocol7.frinton.java;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingServer {

  private final File rootDir;
  private final int port;

  public BlockingServer(File rootDir, int port) {
    this.rootDir = rootDir;
    this.port = port;
  }

  public void start() throws IOException {
    ServerSocket ss = new ServerSocket(port);

    while (true) {
      final Socket socket = ss.accept();

      new HttpHandler(rootDir, socket).start();
    }
  }
}
