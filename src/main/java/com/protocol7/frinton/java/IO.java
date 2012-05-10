package com.protocol7.frinton.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class IO {

  public static void writeFile(OutputStream out, File file) throws FileNotFoundException,
      IOException {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      byte[] b = new byte[4096];
      int read = fis.read(b, 0, 4096);
      while (read > -1) {
        out.write(b, 0, read);
        read = fis.read(b, 0, 4096);
      }
    } finally {
      closeQuitely(fis);
    }
  }

  public static void closeQuitely(Reader in) {
    if (in != null) {
      try {
        in.close();
      } catch (IOException ignored) {}
    }
  }

  public static void closeQuitely(OutputStream out) {
    if (out != null) {
      try {
        out.close();
      } catch (IOException ignored) {}
    }
  }

  public static void closeQuitely(InputStream fis) {
    if (fis != null) {
      try {
        fis.close();
      } catch (IOException ignored) {}
    }
  }

  public static boolean inDir(File dir, File file) {
    dir = dir.getAbsoluteFile();
    file = file.getAbsoluteFile();

    File parent = file;
    while (parent != null) {
      if (dir.equals(parent)) {
        return true;
      }
      parent = parent.getParentFile();
    }
    return false;
  }
}
