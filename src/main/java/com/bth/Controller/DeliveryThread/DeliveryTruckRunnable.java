package com.bth.Controller.DeliveryThread;

public class DeliveryTruckRunnable extends Thread implements RunInterface {

  private Thread thread;
  private int id;

  public DeliveryTruckRunnable(int id) {
    this.id = id;
  }

  public void run() {
    System.out.println("Running motors for id: " + id);

    this.startMotors();

    System.out.println("Exiting thread for id: " + id);
  }

  // TODO: fix implementation.
  private boolean startMotors() {
    //TODO: YOUR CODE HERE
    //TODO: CHECK THIS DOCUMENTATION TO UNDERSTAND HOW TO RUN THIS TRUCK
    //TODO: AND HOW TO WRITE CODE:
    return true;
  }

  public void start() {
    System.out.println("Starting thread with id: " + id);
    if (thread == null) {
      thread = new Thread(this, String.valueOf(id));
      thread.start();
    }
  }
}
