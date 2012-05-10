package com.protocol7.frinton.java;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

  private static final int DEFAULT_PORT = 9999;

  public static void main(String[] args) throws IOException {
    Map<String, List<String>> opts = new CommandLineParser().parse(args);

    int port = DEFAULT_PORT;
    if(opts.containsKey("port")) {
      List<String> portList = opts.get("port");
      String portStr = portList.get(0);
      if(portStr != null) {
        port = Integer.valueOf(portStr);
      }
    }

    if(opts.containsKey("non-blocking")) {
      System.out.println("Using non-blocking server");
    } else {
      // default to blocking
      System.out.println("Using blocking server");

      new BlockingServer(new File(""), port).start();
    }

  }
}
