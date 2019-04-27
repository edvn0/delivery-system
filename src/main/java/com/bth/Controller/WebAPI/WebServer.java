package com.bth.Controller.WebAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class.getName());

  public boolean isRunning = false;

  private String command = "";

  private ServerSocket ss;

  private WebServer(final int port) throws IOException {
    LOGGER.info("Creating a simple API");

    ss = new ServerSocket(port);

  }

  public void run() throws IOException {
    isRunning = true;
    while (isRunning) {
      try (Socket sock = ss.accept()) {
        InputStream is = sock.getInputStream();
        OutputStream os = sock.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintStream ps = new PrintStream(os);

        while (isRunning) {
          String cmd = br.readLine();
          if (cmd == null) {
            break;
          }
          String reply = execute(cmd);
          if (reply != null) {
            if (reply.equals("HTTP/1.1 400 ERROR\r\n\r\n" + readFile() + "\r\n")) {
              sock.close();
              isRunning = false;
              break;
            }
            ps.println(reply);
            this.command = reply;
          } else {
            br.close();
            ps.close();
            break;
          }
        }
      }
    }
  }

  private String execute(String cmd) {
    String[] tokens = cmd.split(" ");
    if (tokens.length > 1 && tokens[0].equals("GET")) {
      if (tokens[1].equals("/stop")) {
        LOGGER.info("{}", tokens[1]);
        return "HTTP/1.1 400 ERROR\r\n\r\n";
      } else {
        return tokens[1].substring(1);
      }
    }
    return "HTTP/1.1 200 OK\r\n\r\n";
  }

  private String readFile() {

    StringBuilder document = new StringBuilder();

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

  public String getCommand() {
    if (command.equals("")) {
      return null;
    } else {
      return command.split(" ")[1].toLowerCase();
    }
  }
}
