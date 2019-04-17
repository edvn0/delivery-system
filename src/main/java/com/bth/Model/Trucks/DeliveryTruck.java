package com.bth.Model.Trucks;

import com.bth.Model.Sensors.LineReaderV2;
import com.bth.Model.Truck;
import com.bth.Model.TruckColourEnum;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class DeliveryTruck extends Truck {

  /**
   * START CONFIG
   */
  //motor for drive forwards and backwards - connected to motor port D
  public EV3MediumRegulatedMotor motorDrive;
  //motor for steering - connected to motor port C
  public EV3MediumRegulatedMotor motorSteer;

  //motor for crane lifting - connected multiplexer port M1
  private EV3LargeRegulatedMotor craneRotation;
  //motor for crane lifting - connected to motor port B
  public EV3MediumRegulatedMotor craneLift;
  //motor for grabber - connected to motor port A
  public EV3MediumRegulatedMotor craneGrabber;

  //sensor for proximity - connect to sensor port S1
  private EV3UltrasonicSensor sensorProximity;
  //sensor for line reading - connected to sensor port S3
  private LineReaderV2 lineReader;
  //sensor for crane rotation movement detection S4
  public EV3TouchSensor touchSensor;
  /**
   * END CONFIG
   */

  private String name;
  private int colorForLines;
  private int id;
  private int speed;

  public DeliveryTruck(String name, int id) {
    this.name = name;
    this.colorForLines = TruckColourEnum.RED.getColor();
    this.id = id;
  }


  @Override
  public void move(int dir) {
    if (super.checkBattery()) {
      switch (dir) {
        case 0:
          this.motorDrive.setSpeed(this.speed);
          // this.motorSteer.rotate(0);
          this.motorDrive.backward();
          break;
        case 1:
          this.motorDrive.setSpeed(this.speed);
          Delay.msDelay(500);
          // this.motorSteer.rotate(0);
          this.motorDrive.backward();
          Delay.msDelay(1300);
          this.motorDrive.stop();
          break;
        case 2:
          this.motorDrive.stop();
          this.motorDrive.setSpeed(this.speed);
          this.motorSteer.rotate(180);
          this.motorDrive.forward();
          break;
        case 3:
          this.motorDrive.stop();
          this.motorDrive.setSpeed(this.speed);
          this.motorSteer.rotate(-90);
          this.motorDrive.forward();
          break;
        default:
          break;
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
    if (this.lineReader != null) {
      // TODO: implementation.
      lineReader.wake();

      int[] values = lineReader.getCALValues();

    }

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

  public void initializeMotors() {
    motorDrive = new EV3MediumRegulatedMotor(MotorPort.C);
    motorSteer = new EV3MediumRegulatedMotor(MotorPort.D);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Emergency Stop");
      motorDrive.stop();
      motorSteer.stop();
    }));
  }

  public void initializeSensors() {
    lineReader = new LineReaderV2(SensorPort.S3);
    sensorProximity = new EV3UltrasonicSensor(SensorPort.S1);
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getColor() {
    return this.colorForLines;
  }
}
