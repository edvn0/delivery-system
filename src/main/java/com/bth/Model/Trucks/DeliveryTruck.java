package com.bth.Model.Trucks;

import com.bth.Controller.DeliveryThread.DeliveryTruckRunnable;
import com.bth.Model.Sensors.LineReaderV2;
import com.bth.Model.Truck;
import com.bth.Model.TruckColourEnum;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

public class DeliveryTruck implements Truck {

  /**
   * START CONFIG
   */
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

  //motor for drive forwards and backwards - connected to motor port D
  public static EV3MediumRegulatedMotor motorDrive;
  //motor for steering - connected to motor port C
  public static EV3MediumRegulatedMotor motorSteer;

  //motor for crane lifting - connected multiplexer port M1
  private static EV3LargeRegulatedMotor craneRotation;
  //motor for crane lifting - connected to motor port B
  public static EV3MediumRegulatedMotor craneLift;
  //motor for grabber - connected to motor port A
  public static EV3MediumRegulatedMotor craneGrabber;

  //sensor for proximity - connect to sensor port S1
  public static EV3UltrasonicSensor sensorProximity;
  //sensor for line reading - connected to sensor port S3
  public static LineReaderV2 lineReader;
  //sensor for crane rotation movement detection S4
  public static EV3TouchSensor touchSensor;
  /**
   * END CONFIG
   */

  private DeliveryTruckRunnable runnable;
  private String name;
  private int colorForLines;
  private int id;

  public DeliveryTruck(String name, int id) {
    this.name = name;
    this.colorForLines = TruckColourEnum.RED.colorId();
    this.id = id;
  }


  @Override
  public void move(int dir) {
    boolean checkBattery = this.checkBattery();
    if (checkBattery) {
      switch (dir) {

      }
    }
  }

  @Override
  public void stop() {
  }

  @Override
  public void craneStart() {

  }

  @Override
  public void craneStop() {

  }

  @Override
  public boolean readInfo(int barcode) {
    return false;
  }

  @Override
  public void readLines(int color) {

  }

  @Override
  public String toString() {
    return "Delivery Truck \"" + this.name + "\"\n";
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getId() {
    return this.id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeliveryTruckRunnable getRunThread() {
    return this.runnable;
  }

  public void initalizeMotors() {
    motorDrive = new EV3MediumRegulatedMotor(MotorPort.B);
    motorSteer = new EV3MediumRegulatedMotor(MotorPort.C);
  }

  public void initalizeSensors() {
    lineReader = new LineReaderV2(SensorPort.S3);
  }

}
