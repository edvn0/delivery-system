package com.bth.Model.Trucks;

import com.bth.Model.Sensors.LineReaderV2;
import com.bth.Model.Truck;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.Port;

import java.util.HashMap;

public class DeliveryTruck2 extends Truck {

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

  private String name;
  private int id;
  private HashMap<Port, Double> speeds;

  public DeliveryTruck2(String name, int id, Port[] ports) {
    motorDrive = new EV3MediumRegulatedMotor(ports[0]);   // PORT C
    motorSteer = new EV3MediumRegulatedMotor(ports[1]);   // PORT D
    lineReader = new LineReaderV2(ports[2]);              // PORT S3
    sensorProximity = new EV3UltrasonicSensor(ports[3]);  // PORT S1

    this.name = name;
    this.id = id;

    this.speeds = new HashMap<>();

    System.out.println(this.speeds);
  }

  @Override
  public void move(int dir) {
    boolean checkBattery = this.checkBattery();
    if (checkBattery) {
      switch (dir) {
        case 0:
          //Move Forward
            System.out.println("DSometqagahfwadwad");
        case 1:
          //Back up
        case 2:
          //Turn Right
        case 3:
          //Turn Left
      }
    }
  }

  @Override
  protected void stop() {

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

  private void initSpeeds(double[] speeds) {
    int k = 0;
    for (double d : speeds) {
      this.speeds.put(Truck.ports[k++], d);
    }
  }

  public void setSpeed(Port p, double speed) {
    this.speeds.replace(p, speed);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getId() {
    return id;
  }
}
