package com.bth.Model;

import com.bth.Model.Trucks.DeliveryTruck;
import ev3dev.sensors.Battery;

public abstract class Truck {

  private static int HALF_SECOND = 500;
  public static double minVoltage = 7.200d;

  //Synchronization variables between threads to allow intra thread communication
  //Main variable for stopping execution
  public static boolean isRunning = true;
  //Variables for commands from/to SCS
  public static String inputCommandSCS = "";
  public static String outputCommandSCS = "none";
  //Variables for controlling task thread
  public static boolean runThreadIsStarted = false;
  public static boolean runThreadIsExecuted = false;

  /***
   * Moves the truck in a direction,
   * @param dir dir \in (0,1,2,3) \equiv (left, right, up, down)
   */
  public abstract void move(int dir);

  /**
   * Stops the truck.
   */
  protected abstract void stop();

  /***
   * Moves the crane in some direction.
   */
  public abstract void craneStart();

  /***
   * Stops the crane.
   */
  public abstract void craneStop();

  /**
   * Reads information regarding packages or delivery tasks in a barcode.
   *
   * @param barcode input barcode for the truck.
   * @return true if read correctly
   */
  public abstract boolean readInfo(int barcode);

  /***
   * Checks floor for lines
   * @param color color of the lines. Is associated with type of truck.
   */
  public abstract void readLines(int color);

  public abstract String getName();

  public abstract int getId();

  public boolean checkBattery() {
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
