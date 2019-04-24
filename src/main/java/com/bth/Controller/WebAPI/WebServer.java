package com.bth.Controller.WebAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebServer {

  public static Logger LOGGER = LoggerFactory.getLogger(WebServer.class);

  private ServerSocket ss;
  private Socket sock;

  public WebServer(final int port) throws IOException {
    LOGGER.info("Creating a simple API");

    ss = new ServerSocket(port);

  }

  public void run() throws IOException {
    for (; ; ) {
      sock = ss.accept();
      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();

      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      PrintStream ps = new PrintStream(os);

      for (; ; ) {
        String cmd = br.readLine();
        if (cmd == null) {
          break;
        }
        String reply = execute(cmd);
        if (reply != null) {
          ps.println(reply);
        } else {
          br.close();
          ps.close();
          break;
        }
      }

    }
  }

  public String execute(String cmd) {
    String[] tokens = cmd.split(" ");

    if (tokens.length > 1 && tokens[0].equals("GET")) {

      if (tokens[1].equals("/stop")) {
        LOGGER.info("{}", tokens[1]);
        System.exit(0);
      } else {
        //Your protocol
        System.out.println("HTTP/1.1 200 OK\r\n\r\n" + readFile() + "\r\n");
      }

      return "HTTP/1.1 200 OK\r\n\r\n" + readFile() + "\r\n";
    }
    return null;
  }

  private String readFile() {

    StringBuffer document = new StringBuffer();

    try {

      InputStream in = this.getClass().getClassLoader().getResourceAsStream("index.html");

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in), 1);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        document.append(line);
      }
      in.close();

    } catch (IOException io) {
      LOGGER.error(io.getLocalizedMessage());
    }

    return document.toString();

  }

}
