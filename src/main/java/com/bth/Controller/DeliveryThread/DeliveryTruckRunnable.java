package com.bth.Controller.DeliveryThread;

import com.bth.Model.Trucks.DeliveryTruck;

public class DeliveryTruckRunnable extends Thread implements RunInterface {

  private Thread thread;
  private int id;
  private DeliveryTruck truck;

  public DeliveryTruckRunnable(int id) {
    this.id = id;
    this.truck = null;
  }

  public void run() {
    System.out.println("Running motors for id: " + id);
    if (this.truck == null) {
      throw new NullPointerException("Truck is null");
    } else {
      this.startMotors();
    }
    System.out.println("Exiting thread for id: " + id);
  }

  // TODO: fix implementation.
  private boolean startMotors() {
    //TODO: YOUR CODE HERE
    //TODO: CHECK THIS DOCUMENTATION TO UNDERSTAND HOW TO RUN THIS TRUCK
    //TODO: AND HOW TO WRITE CODE:

    try {
      while (DeliveryTruck.isRunning && !DeliveryTruck.runThreadIsExecuted) {
        this.truck.motorDrive.setSpeed(this.truck.getSpeed());
        this.truck.motorSteer.setSpeed(50);
        this.truck.motorSteer.rotate(10);
        this.truck.motorDrive.forward();

        Thread.sleep(2000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return true;
  }

  public void start() {
    System.out.println("Starting thread with id: " + id);
    if (thread == null) {
      thread = new Thread(this, String.valueOf(id));
      thread.start();
    }
  }

  public void setTruck(DeliveryTruck truck) {
    this.truck = truck;
  }
}
