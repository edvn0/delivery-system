package com.bth.Controller.Thread;

import com.bth.Model.Trucks.DeliveryTruck;
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
    if (DeliveryTruck.outputCommandSCS.equals("FINISHED")) {

      System.out.println("worker-FINISHED");

      try {
        writer.write(DeliveryTruck.outputCommandSCS + "\n");
        writer.flush();
        DeliveryTruck.outputCommandSCS = "none";
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
    while (DeliveryTruck.isRunning) {

      System.out.println("worker running");

      if (DeliveryTruck.outputCommandSCS.equals("FINISHED")) {

        System.out.println("worker-FINISHED");
        DeliveryTruck.outputCommandSCS = "none";
      }

      try {
        line = reader.readLine();
        System.out.println("RECEIVED " + line);
      } catch (IOException e) {
        e.printStackTrace();
      }

      switch (line) {
        case "RUN":
          DeliveryTruck.inputCommandSCS = line;
          break;
        case "LEFT-PRESS":
          DeliveryTruck.inputCommandSCS = line;
          break;
        case "STOP":
          DeliveryTruck.inputCommandSCS = line;
          DeliveryTruck.runThreadIsExecuted = true;
          break;
        case "KILL":
          DeliveryTruck.inputCommandSCS = line;
          DeliveryTruck.isRunning = false;
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