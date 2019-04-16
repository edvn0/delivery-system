package com.bth.Controller.Thread;

import com.bth.Model.Truck;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Title SocketThread
 *
 * Implements single socket connection via thread that listens to SCS commands and sends commands to
 * SCS.
 *
 * NOTE: Nothing should be changed in this class.
 */

public class SocketThread extends Thread {

  private Thread t;

  protected Socket clientSocket = null;
  protected String serverText = null;
  protected BufferedReader reader;
  protected BufferedWriter writer;

  public SocketThread(Socket clientSocket, String serverText) {
    this.clientSocket = clientSocket;
    this.serverText = serverText;
  }


  private void openReaders() {
    try {
      reader = new BufferedReader(new InputStreamReader(
          clientSocket.getInputStream()));
      writer = new BufferedWriter(new OutputStreamWriter(
          clientSocket.getOutputStream()));

      String outputValue = clientSocket.getLocalSocketAddress().toString();

      writer.write(outputValue + "\n");
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void isRunningWR() {
    if (Truck.outputCommandSCS.equals("FINISHED")) {

      System.out.println("worker-FINISHED");

      try {
        writer.write(Truck.outputCommandSCS + "\n");
        writer.flush();
        Truck.outputCommandSCS = "none";
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public void start() {
    System.out.println("Starting Worker Thread");
    if (t == null) {
      t = new Thread(this, "Worker Thread");
      t.start();
    }
  }

  public void run() {

    this.openReaders();

    long time = System.currentTimeMillis();

    String line = null;
    while (Truck.isRunning) {

      System.out.println("worker running");

      if (Truck.outputCommandSCS.equals("FINISHED")) {

        System.out.println("worker-FINISHED");
        Truck.outputCommandSCS = "none";
      }

      try {
        line = reader.readLine();
        System.out.println("RECEIVED " + line);
      } catch (IOException e) {
        e.printStackTrace();
      }

      switch (line) {
        case "RUN":
          Truck.inputCommandSCS = line;
          break;
        case "LEFT-PRESS":
          Truck.inputCommandSCS = line;
          break;
        case "STOP":
          Truck.inputCommandSCS = line;
          Truck.runThreadIsExecuted = true;
          break;
        case "KILL":
          Truck.inputCommandSCS = line;
          Truck.isRunning = false;
          break;
      }

      System.out.println("Request processed: " + time);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    try {
      System.out.println("Worker closed");
      reader.close();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}