package com.bth.Controller.DeliveryThread;

import com.bth.Model.Truck;
import com.bth.Model.Trucks.DeliveryTruck;

public class DeliveryTruckRunnable extends Thread implements Runnable {

  private Thread thread;
  private String id;
  private DeliveryTruck truck;

  public DeliveryTruckRunnable(String id) {
    this.id = id;
    this.truck = null;
  }

  public void run() {
    System.out.println("Running motors for id: " + id);
    if (this.truck == null) {
      throw new NullPointerException("Truck is null");
    } else {
      this.startMotors();
      System.out.println("Exiting thread for id: " + id);
    }
  }

  private boolean startMotors() {
    /*TODO: YOUR CODE HERE,
       CHECK THIS DOCUMENTATION TO UNDERSTAND HOW TO RUN THIS TRUCK,
       AND HOW TO WRITE CODE:
    */

    System.out.println("Starting motors, while looping initiating.");
    System.out.println("Value for boolean:" + (Truck.isRunning && !Truck.runThreadIsExecuted));

    while (Truck.isRunning && !Truck.runThreadIsExecuted) {
      // Fixme: this is an indicator for the while loop.
      System.out.println("Sleep for while loop in startMotors();");

      Truck.runThreadIsExecuted = true;
    }
    return true;
  }

  public void start() {
    System.out.println("Starting thread with id: " + id);
    if (thread == null) {
      thread = new Thread(this, id);
      thread.start();
    }
  }

  public void setTruck(DeliveryTruck truck) {
    this.truck = truck;
  }
}
