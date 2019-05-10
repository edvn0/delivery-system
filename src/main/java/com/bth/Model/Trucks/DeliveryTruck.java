package com.bth.Model.Trucks;

import static com.bth.Utilities.ShippingSystemUtilities.followTheLine;
import static com.bth.Utilities.ShippingSystemUtilities.splitArray;
import static lejos.utility.Delay.msDelay;

import com.bth.Model.Sensors.LineReaderV2;
import com.bth.Model.Truck;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

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
  private final HashMap<Port, Double> speeds;
  private final ArrayList<Double> history;

  private int prevDirection = 0;

  public DeliveryTruck(String name, int id) {

    motorDrive = new EV3MediumRegulatedMotor(Truck.motorPorts[3]);   // PORT D
    motorSteer = new EV3MediumRegulatedMotor(Truck.motorPorts[2]);   // PORT C
    extender = new EV3MediumRegulatedMotor(Truck.motorPorts[1]); // PORT S2
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
    this.history = new ArrayList<>();

    // DEV only.
    this.speeds = initSpeeds(new double[]{100, 1300, 500, 500});
  }

  @Override
  public void move(int dir) {
    boolean checkBattery = this.checkBattery();
    if (checkBattery) {
      switch (dir) {
        case 0:
          //Move Forward
          double speedMotor = this.speeds.get(Truck.motorPorts[3]);
          this.setMotorDriveSpeed(speedMotor);
          msDelay(HALF_SECOND);
          motorDrive.forward();
          msDelay(HALF_SECOND);
          this.stop();
          break;
        case 1:
          //Back up
          double speed = this.getSpeed(Truck.motorPorts[3]);
          this.setMotorDriveSpeed(speed);
          msDelay(HALF_SECOND);
          motorDrive.backward();
          msDelay(2500);
          this.stop();
          break;
        //This is for development only!
        case 2:
          System.out.println("Start case 2");

          this.motorDrive.setSpeed((int) this.getSpeed(Truck.motorPorts[3]));
          msDelay(1500);

          this.motorDrive.backward();
          msDelay(10000);

          System.out.println("Finished case 2!");
          break;
        case 3:
          System.out.println("Start case 3");
          // Do stuff here Joe

          this.motorDrive.setSpeed(500);
          msDelay(HALF_SECOND);
          this.motorDrive.backward();
          msDelay(10000);
          this.motorSteer.setSpeed(200);
          msDelay(HALF_SECOND);
          this.motorSteer.rotate(110, true);
          msDelay(HALF_SECOND);
          this.motorSteer.forward();
          for (int i = 0; i < 10; i++) {
            System.out.println(motorSteer.getPosition());

            int[] info = lineReader.getCALValues();
            System.out.println(Arrays.toString(info));

            msDelay(1000);
          }
          msDelay(1000);
          this.motorDrive.forward();
          msDelay(10000);

          System.out.println("Finished case 3!");
          break;
        case 4:
          System.out.println("Start case 4");
          this.motorSteer.setSpeed(200);
          msDelay(800);

          this.motorSteer.rotate(-140, true);
          msDelay(900);

          this.motorSteer.forward();
          msDelay(5000);

          this.motorDrive.setSpeed(500);
          msDelay(1000);

          // "Async" lol
          long time = System.currentTimeMillis();
          long end = time + 25000;
          while (System.currentTimeMillis() < end) {
            if (end - System.currentTimeMillis() < 12500) {
              motorDrive.backward();
            } else {
              motorDrive.forward();
            }
          }
          motorDrive.stop();

          System.out.println("Finished case 4!");

          break;
        case 5:
          System.out.println("Start case 5");

          this.motorSteer.setSpeed(240);
          msDelay(1500);

          this.motorSteer.rotate(140, true);
          msDelay(3500);

          this.motorSteer.stop();
          System.out.println("Finished case 5!");
        case 6:
          extender.setSpeed(80);
          msDelay(500);
          extender.backward();
          msDelay(6000);
          extender.forward();
          msDelay(6000);

        case 7:

      }
    }
  }

  public void runTruck() {

    motorDrive.setSpeed(400);
    motorSteer.setSpeed(1000);

    Delay.msDelay(500);

    //checkRotationalPosition();

    readLinesAndMoveTruck();
  }

  private void readLinesAndMoveTruck() {
    int i = 0;
    int iterations = 200;
    boolean shouldRun = true;
    // TODO: check for black line
    while (i < iterations) {
      readLines();
      System.out.println("Loop " + i);
      i++;
    }

    this.motorSteer.rotateTo(0, true);
    Delay.msDelay(750);
    this.stop();
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

  private double getSpeed(Port port) {
    return this.speeds.get(port);
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

  @Override
  public void readLines() {
    int dirNow;
    List<int[]> values = splitArray(lineReader.generateValues(1), 8,
        new int[]{1, 1, 1, 1, 1, 1, 1, 1});

    //previousDirection = directionToMove(values);
    dirNow = followTheLine(values);

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

  private HashMap<Port, Double> initSpeeds(double[] speeds) {
    int k = 0;
    HashMap<Port, Double> portHashMap = new HashMap<>();
    for (double d : speeds) {
      portHashMap.put(Truck.motorPorts[k++], d);
    }
    return portHashMap;
  }

  public void setSpeed(Port p, double speed) {
    this.speeds.replace(p, speed);
  }

  private void setMotorDriveSpeed(double speed) {
    this.motorDrive.setSpeed((int) speed);
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