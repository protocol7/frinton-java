package com.protocol7.frinton.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

final class HttpHandler extends Thread {

  private final File rootDir;
  private final Socket socket;

  public HttpHandler(File rootDir, Socket socket) {
    this.rootDir = rootDir;
    this.socket = socket;
  }

  public void run() {
    OutputStream out = null;
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = socket.getOutputStream();

      String line = in.readLine();

      if (line != null) {
        System.out.println(line);
        String method = line.split(" ")[0];
        if (method.equals("GET")) {

          String path = line.split(" ")[1];
          while (line != null && line.length() > 0) {
            line = in.readLine();
          }

          // remove leading slash
          path = path.substring(1);

          File file = new File(path);
          // file must be in root dir
          if (IO.inDir(rootDir, file)) {
            // file must exists
            if (file.exists()) {
              // file must not be a directory
              if (file.isFile()) {
                // all is good
                writeHttpResponse(out, 200, "OK");

                IO.writeFile(out, file);
              } else {
                // 403
                writeHttpResponse(out, 403, "Directory listing not allowed");
              }
            } else {
              // 404
              writeHttpResponse(out, 404, "Not found");
            }
          } else {
            // not allowed
            writeHttpResponse(out, 403, "Not allowed");
          }
        } else {
          writeHttpResponse(out, 405, "Method not allowed");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      IO.closeQuitely(out);
      IO.closeQuitely(in);
    }
  }

  private void writeHttpResponse(OutputStream out, int statusCode, String message)
      throws IOException {
    Writer writer = new OutputStreamWriter(out);
    writer.write("HTTP/1.0 " + statusCode + " " + message + "\r\n");

    // Thu, 10 May 2012 21:13:57 GMT
    DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
    writer.write("Date: " + dateFormat.format(new Date()) + "\r\n");
    writer.write("Server: frinton-java\r\n");
    //writer.write("Connection: close\r\n");
    writer.write("\r\n");

    writer.flush();
  }
}