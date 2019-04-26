package com.bth.Model.Trucks;

import com.bth.Controller.WebAPI.WebServer;
import com.bth.Model.Sensors.LineReaderV2;
import com.bth.Model.Truck;
import com.bth.Utilities.ShippingSystemUtilities;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class DeliveryTruck extends Truck {

  private static final double leastDistance = 0.25; // 25 centimeters.

  //motor for drive forwards and backwards - connected to motor port D
  public EV3MediumRegulatedMotor motorDrive;
  //motor for steering - connected to motor port C
  public EV3MediumRegulatedMotor motorSteer;

  //motor for crane lifting - connected multiplexer port M1
  private EV3LargeRegulatedMotor craneRotation;
  //motor for crane lifting - connected to motor port B
  public EV3MediumRegulatedMotor extender;
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

  public DeliveryTruck(String name, int id) {

    motorDrive = new EV3MediumRegulatedMotor(Truck.motorPorts[3]);   // PORT D
    motorSteer = new EV3MediumRegulatedMotor(Truck.motorPorts[2]);   // PORT C
    extender = new EV3MediumRegulatedMotor(Truck.motorPorts[1]);
    lineReader = new LineReaderV2(Truck.sensorPorts[2]);   // PORT S3
    sensorProximity = new EV3UltrasonicSensor(Truck.sensorPorts[0]);   // PORT S1

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Emergency Stop!");

      Truck.inputCommandSCS = "";
      Truck.runThreadIsExecuted = true;
      Truck.isRunning = false;

      motorDrive.stop();
      motorSteer.stop();
    }));

    this.name = name;
    this.id = id;

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
          Delay.msDelay(HALF_SECOND);
          motorDrive.forward();
          Delay.msDelay(HALF_SECOND);
          this.stop();
          break;
        case 1:
          //Back up
          double speed = this.getSpeed(Truck.motorPorts[3]);
          this.setMotorDriveSpeed(speed);
          Delay.msDelay(HALF_SECOND);
          motorDrive.backward();
          Delay.msDelay(2500);
          this.stop();
          break;
        //This is for development only!
        case 2:
          System.out.println("Start case 2");

          this.motorDrive.setSpeed(this.getSpeed(Truck.motorPorts[3]));
          Delay.msDelay(1500);

          this.motorDrive.backward();
          Delay.msDelay(10000);

          System.out.println("Finished case 2!");
          break;
        case 3:
          System.out.println("Start case 3");
          // Do stuff here Joe

          this.motorDrive.setSpeed(500);
          Delay.msDelay(HALF_SECOND);
          this.motorDrive.backward();
          Delay.msDelay(10000);
          this.motorSteer.setSpeed(200);
          Delay.msDelay(HALF_SECOND);
          this.motorSteer.rotate(110, true);
          Delay.msDelay(HALF_SECOND);
          this.motorSteer.forward();
          for (int i = 0; i < 10; i++) {
            System.out.println(motorSteer.getPosition());

            int[] info = lineReader.getCALValues();
            System.out.println(Arrays.toString(info));

            Delay.msDelay(1000);
          }
          Delay.msDelay(1000);
          this.motorDrive.forward();
          Delay.msDelay(10000);

          System.out.println("Finished case 3!");
          break;
        case 4:
          System.out.println("Start case 4");
          this.motorSteer.setSpeed(200);
          Delay.msDelay(800);

          this.motorSteer.rotate(-140, true);
          Delay.msDelay(900);

          this.motorSteer.forward();
          Delay.msDelay(5000);

          this.motorDrive.setSpeed(500);
          Delay.msDelay(1000);

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
          Delay.msDelay(1500);

          this.motorSteer.rotate(140, true);
          Delay.msDelay(3500);

          this.motorSteer.stop();
          System.out.println("Finished case 5!");
        case 6:
          extender.setSpeed(80);
          Delay.msDelay(500);
          extender.backward();
          Delay.msDelay(6000);
          extender.forward();
          Delay.msDelay(6000);

        case 7:

      }
    }
  }

  public void runTruck() {
    lineReader.wake();
    motorDrive.setSpeed(100);
    motorSteer.setSpeed(150);
    int previousDirection = 0;
    int i = 0;
    while (true) {
      if (lineReader.isFollowing()) {
        List<int[]> values = ShippingSystemUtilities.
            splitArray(lineReader.getCALValues(), 3, new int[]{3, 2, 3});

        if (values != null) {
          previousDirection = LineReaderV2.directionToMove(values);
        }

        motorSteer.rotate(previousDirection * 100, true);
        System.out.println(motorSteer.getTachoCount() + " " + motorDrive.getPosition());
        System.out.println(motorSteer.getPosition() + " " + motorDrive.getTachoCount());

        Delay.msDelay(500);
        motorDrive.backward();
        motorSteer.forward();

        System.out.println("Loop:" + i++);
        if (i > 20) {
          break;
        }
      }
    }
    motorDrive.stop();
  }

  // TODO: fix the integration with a localhost server.
  public static void doStuff(WebServer server) {
    System.out.println("Running!");
    while (server.isRunning) {

      System.out.println(server.getCommand());
      Delay.msDelay(500);
    }
    System.out.println("Stop running!");
  }

  private int getSpeed(Port port) {
    return this.speeds.get(port).intValue();
  }

  @Override
  protected void stop() {
    motorDrive.stop();
    Delay.msDelay(800);
    motorSteer.stop();
    Delay.msDelay(800);
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