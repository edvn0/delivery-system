package com.bth.Controller.HTTPServer.WebServer;

import com.bth.Controller.HTTPServer.Worker.WebServerWorker;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

  public WebServer(int port, int maxThreads) {
    ServerSocket socket = null;
    try {
      socket = new ServerSocket(port);
      System.out.println("Server started. Listening on " + socket.getLocalPort() + "...\n");
      ExecutorService service = Executors.newFixedThreadPool(maxThreads);

      while (true) {
        Socket clientSocket = socket.accept();
        WebServerWorker socketWorker = new WebServerWorker(clientSocket);
        Thread t = new Thread(socketWorker);
        service.execute(t);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (socket != null) {
          socket.close();
        }
      } catch (IOException e) {
        e.getMessage();
      }
    }


  }
}
