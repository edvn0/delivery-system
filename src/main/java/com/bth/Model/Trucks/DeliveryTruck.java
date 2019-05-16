package com.bth.Model.Trucks;

import com.bth.Model.Sensors.LineReaderV2;
import com.bth.Model.Truck;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.utility.Delay;

import java.util.List;

import static com.bth.Utilities.ShippingSystemUtilities.directionToMove;
import static com.bth.Utilities.ShippingSystemUtilities.splitArray;
import static lejos.utility.Delay.msDelay;

public class DeliveryTruck extends Truck {

  private static final double leastDistance = 0.25; // 25 centimeters.

  //motor for drive forwards and backwards - connected to motor port D
  private final EV3MediumRegulatedMotor motorDrive;
  //motor for steering - connected to motor port C
  private final EV3MediumRegulatedMotor motorSteer;

  //motor for crane lifting - connected multiplexer port M1
  private EV3LargeRegulatedMotor craneRotation;
  //motor for crane lifting - connected to motor port B
  private final EV3MediumRegulatedMotor extender;
  //motor for grabber - connected to motor port A
  public EV3MediumRegulatedMotor craneGrabber;

  //sensor for proximity - connect to sensor port S1
  private final EV3UltrasonicSensor sensorProximity;
  //sensor for line reading - connected to sensor port S3
  private final LineReaderV2 lineReader;
  //sensor for crane rotation movement detection S4
  public EV3TouchSensor touchSensor;

  private final String name;
  private final int id;

  private int prevDirection = 0;

  public DeliveryTruck(String name, int id) {

    motorDrive = new EV3MediumRegulatedMotor(Truck.motorPorts[3]);   // PORT D
    motorSteer = new EV3MediumRegulatedMotor(Truck.motorPorts[2]);   // PORT C
      extender = new EV3MediumRegulatedMotor(Truck.motorPorts[1]); // PORT B

    lineReader = new LineReaderV2(Truck.sensorPorts[2]);   // PORT S3
    sensorProximity = new EV3UltrasonicSensor(Truck.sensorPorts[0]);   // PORT S1

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Emergency Stop!");

      Truck.inputCommandSCS = "";
      Truck.runThreadIsExecuted = true;
      Truck.isRunning = false;

      motorSteer.rotateTo(0, true);

      motorDrive.stop();
      motorSteer.stop();
    }));

    this.name = name;
    this.id = id;
  }

  @Override
  public void move(int dir) {
    boolean checkBattery = this.checkBattery();
  }

  public void runTruck() {
    motorDrive.setSpeed(400);
    motorSteer.setSpeed(1000);
      extender.setSpeed(1000);
    Delay.msDelay(500);
    //checkRotationalPosition();
      //readLinesAndMoveTruck();
      //openPlateu();
      //closePlateu();
      rotate180Degrees();
      zeroPosition();
  }


    private void zeroPosition() {

        msDelay(500);

        motorSteer.setSpeed(1000);
        motorDrive.setSpeed(300);

        motorDrive.backward();
        msDelay(500);
        motorSteer.rotateTo(0, true);
        msDelay(200);
        motorDrive.stop();

        msDelay(3000);

    }

    private void rotate180Degrees() {

        msDelay(500);
        motorDrive.setSpeed(1000);

        //Forward drive with turn on wheels
        motorSteer.rotateTo(70, true);
        motorDrive.backward();
        msDelay(6500);
        motorDrive.stop();

        //Straight wheels
        motorSteer.rotateTo(0, true);
        motorDrive.forward();
        msDelay(2200);
        motorDrive.stop();

        //Turned wheels backwards
        motorSteer.rotateTo(-70, true);
        motorDrive.forward();
        msDelay(4200);
        motorDrive.stop();

    }

    private void closePlateu() {

        extender.backward();
        msDelay(5000);
        extender.stop();
        msDelay(100);
    }

    private void openPlateu() {

        extender.forward();
        msDelay(5000);
        extender.stop();
        msDelay(100);

    }


  private void readLinesAndMoveTruck() {
    boolean shouldRun = true;
    // TODO: check for black line
    while (shouldRun) {
      int iterationsToCheckForBlack = 5;
      shouldRun = this.checkForBlackLine(5, 60);
      Delay.msDelay(10 * iterationsToCheckForBlack);
      readLines();
    }

    this.motorSteer.rotateTo(0, true);
    Delay.msDelay(750);
    this.stop();
  }

  private boolean checkForBlackLine(int iterations, int threshold) {
    List<int[]> valuesForSensor = this.getLineSensorValues(iterations);
    // Check if all the sensors read a black line, return value to stop.
    // If at least one value is > a threshold (it is white somewhere), return false.
    for (int[] ints : valuesForSensor) {
      int valueForSensor = ints[0];
      if (valueForSensor > threshold) {
        return false;
      }
    }
    return true;
  }


  private void checkRotationalPosition() {
    motorDrive.setSpeed(300);
    this.motorSteer.setSpeed(400);
    this.motorSteer.rotateTo(50, true);
    System.out.println(80);
    motorDrive.backward();
    Delay.msDelay(5000);

    this.motorSteer.rotateTo(0, true);
    System.out.println(0);
    motorDrive.backward();
    Delay.msDelay(5000);

    this.motorSteer.rotateTo(-50, true);
    System.out.println(-80);
    motorDrive.backward();
    Delay.msDelay(5000);

    this.motorSteer.rotateTo(0, true);
    System.out.println(0);
    motorDrive.backward();
    Delay.msDelay(5000);
    motorDrive.stop();
  }

  private void runTruckWithWebServer() {
    for (; ; ) {
      if (!Truck.inputCommandSCS.equals("")) {
        this.handleCommand(inputCommandSCS);
      }
    }
  }

  private void handleCommand(String s) {
    if ("kill".equals(s)) {
    }
  }

  private void startingPosition() {
    System.out.println("Started!");

    motorDrive.stop();
    motorSteer.stop();
    //motorSteer.rotateTo(0, true);
    msDelay(1500);
    motorSteer.rotateTo(0, true);
    System.out.println(
        motorSteer.getPosition() + " " + motorSteer.getTachoCount() + " " + motorSteer
            .getRotationSpeed() + " returned!");
    msDelay(1500);
    motorSteer.stop();
    System.out.println("ended starting position!");

  }

  @Override
  protected void stop() {
    motorDrive.stop();
    msDelay(800);
    motorSteer.stop();
    msDelay(800);
  }

  @Override
  public void craneStart() {
    extender.setSpeed(200);
    extender.forward();
    msDelay(2500);
  }

  @Override
  public void craneStop() {
    extender.setSpeed(200);
    extender.backward();
    msDelay(2500);
  }

  @Override
  public boolean readInfo(int barcode) {
    return false;
  }

  private List<int[]> getLineSensorValues(int iterations) {
    return splitArray(lineReader.generateValues(iterations), 8,
        new int[]{1, 1, 1, 1, 1, 1, 1, 1});
  }

  @Override
  public void readLines() {
    int dirNow;
    List<int[]> values = getLineSensorValues(1);
    dirNow = directionToMove(values);
    if (dirNow == 402) {
      System.out.println("Lost line.");
      int returnSpeed = prevDirection * -1;
      motorSteer.rotateTo(returnSpeed, true);
      msDelay(100);
      System.out.println("Rotated by " + (prevDirection));

      motorDrive.backward();
      return;
    }

    motorSteer.rotateTo(dirNow, true);
    motorDrive.backward();
    prevDirection = dirNow;
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