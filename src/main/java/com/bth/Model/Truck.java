package com.bth.Model;

import com.bth.Model.Trucks.DeliveryTruck;
import ev3dev.sensors.Battery;

public interface Truck {

  /***
   * Moves the truck in a direction,
   * @param dir dir \in (0,1,2,3) \equiv (left, right, up, down)
   */
  void move(int dir);

  /**
   * Stops the truck.
   */
  void stop();

  void craneStart();

  void craneStop();

  /**
   * Reads information regarding packages or delivery tasks in a barcode.
   *
   * @param barcode input barcode for the truck.
   * @return true if read correctly
   */
  boolean readInfo(int barcode);

  /***
   * Checks floor for lines
   * @param color color of the lines. Is associated with type of truck.
   */
  void readLines(int color);

  String getName();

  int getId();

  default boolean checkBattery() {
    System.out.println("Battery Voltage: " + Battery.getInstance().getVoltage());
    System.out.println("Battery Current: " + Battery.getInstance().getBatteryCurrent());
    if (Battery.getInstance().getVoltage() < DeliveryTruck.minVoltage) {
      System.out.println("Battery voltage to low, shutdown");
      System.out.println("Please change the batteries");
      System.exit(0);
      return false;
    }
    return true;
  }

}
