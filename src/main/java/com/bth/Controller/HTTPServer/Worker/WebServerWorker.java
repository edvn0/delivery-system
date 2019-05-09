package com.bth.Controller.HTTPServer.Worker;

import com.bth.Controller.HTTPServer.Parser.HTTPParser;
import com.bth.Model.Truck;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class WebServerWorker implements Runnable {

  private Socket socket;
  private HTTPParser parser;

  public WebServerWorker(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      InputStream isReader = this.socket.getInputStream();
      OutputStream oStream = this.socket.getOutputStream();

      this.parser = new HTTPParser(isReader);

      int ret = this.parser.parseRequest();
      String method = this.parser.getMethod();
      String path = this.parser.getRequestURL();
      String version = this.parser.getVersion();

      System.out.println(ret + " " + method + " " + path + " " + version);

      Truck.handlePath(path);

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
