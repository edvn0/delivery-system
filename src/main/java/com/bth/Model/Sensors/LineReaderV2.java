package com.bth.Model.Sensors;

import ev3dev.sensors.BaseSensor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import lejos.hardware.port.Port;

/**
 * Mindsensors LineReader V2 sensor driver
 *
 * ev3dev hardware driver: http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/sensor_data.html#ms-line-leader
 *
 * Example: https://github.com/ev3dev-lang-java/ev3dev-lang-java/blob/develop/src/main/java/ev3dev/sensors/mindsensors/NXTCamV5.java
 * Example: https://github.com/ev3dev-lang-java/ev3dev-lang-java/blob/develop/src/main/java/ev3dev/hardware/EV3DevDevice.java
 * Example: https://github.com/ev3dev-lang-java/ev3dev-lang-java/blob/develop/src/main/java/ev3dev/hardware/EV3DevSensorDevice.java
 *
 * BaseSensor class: https://github.com/ev3dev-lang-java/ev3dev-lang-java/blob/develop/src/main/java/ev3dev/sensors/BaseSensor.java
 */


public class LineReaderV2 extends BaseSensor {

  private static final String MINDSENSORS_LINEREADERV2 = "ms-line-leader";

  private String lineColorMode = "black";

  //MODES

  /**
   * Line Follower
   */
  public static final String PID = "PID";

  /**
   * Line Follower - all values
   */
  public static final String PID_ALL = "PID-ALL";

  /**
   * Calibrated values
   */
  public static final String CAL = "CAL";

  /**
   * Uncalibrated values
   */
  public static final String RAW = "RAW";

  private final Set<String> trackingAllowedModeList = new HashSet<>(
      Arrays.asList(PID, PID_ALL, CAL, RAW));

  //???
  private void initModes() {
    this.setStringAttribute("mode", "PID");
  }

  private void setMode(String mode) {
    //TODO: allow only allowed modes
    this.setStringAttribute("mode", mode);
  }

  public LineReaderV2(final Port portName) {
    super(portName, LEGO_I2C, MINDSENSORS_LINEREADERV2);
    this.initModes();
  }

  /**
   * Get the PID mode value
   *
   * @return PID value (percentage -100;100)
   */
  public int getPIDValue() {
    this.setMode("PID");
    return this.getIntegerAttribute("value0");
  }

  /**
   * Get the PID-ALL mode values
   *
   * @return array of values: value0: Steering (-100 to 100) value1: Average (0 to 80) value2:
   * Result (as bits)
   */
  public int[] getPIDALLValues() {
    int lightArrayLength = 3;
    int[] intArray = new int[lightArrayLength];

    this.setMode("PID-ALL");
    for (int i = 0; i < lightArrayLength; i++) {
      String value = "value" + i;
      intArray[i] = this.getIntegerAttribute(value);
    }

    return intArray;
  }

  /**
   * Get the CAL values
   *
   * @return return array of values from individual IR lights (0 - 7)
   */
  public int[] getCALValues() {
    int lightArrayLength = 8;
    int[] intArray = new int[lightArrayLength];
    this.setMode("CAL");

    for (int i = 0; i < lightArrayLength; i++) {
      String value = "value" + i;
      intArray[i] = this.getIntegerAttribute(value);
    }

    return intArray;
  }

  /**
   * Get the RAW values
   *
   * @return return array of values from individual IR lights (0 - 7)
   */
  public int[] getRAWValues() {
    int lightArrayLength = 8;
    int[] intArray = new int[lightArrayLength];
    this.setMode("RAW");

    for (int i = 0; i < lightArrayLength; i++) {
      String value = "value" + i;
      intArray[i] = this.getIntegerAttribute(value);
    }

    return intArray;
  }

  /**
   * Get LineReader line color mode
   *
   * @return color name of mode
   */

  public String getLineColorMode() {
    return this.lineColorMode;
  }

  //COMMANDS

  /**
   * Command	Description CAL-WHITE	Calibrate white CAL-BLACK	Calibrate black SLEEP [67]	Put sensor
   * to sleep WAKE [68]	Wake up the sensor INV-COL	Color inversion (White line on a black
   * background) RST-COL	Reset Color inversion (black line on a white background). SNAP [69]	Take a
   * snapshot. 60HZ	Configures sensor for 60Hz electrical mains 50HZ	Configures sensor for 50Hz
   * electrical mains UNIVERSAL	Configures sensor for any (50/60Hz) electrical mains
   */

  public void wake() {
    this.sendCommand("WAKE");
  }

  public void sleep() {
    this.sendCommand("SLEEP");
  }

  public void calibrateWhite() {
    this.sendCommand("CAL-WHITE");
  }

  public void calibrateBlack() {
    this.sendCommand("CAL-BLACK");
  }


  /**
   * @param color: line color
   */
  public void invertColor(String color) {
    if (color.equals("black")) {
      this.sendCommand("RST-COL");
      this.lineColorMode = color;
    }
    if (color.equals("white")) {
      this.sendCommand("INV-COL");
      this.lineColorMode = color;
    }

  }

  /**
   * Send a single byte command represented by a letter
   *
   * @param cmd the letter that identifies the command
   */
  public void sendCommand(final String cmd) {
    this.setStringAttribute("command", cmd);
  }


  public boolean isFollowing() {
    int iterations = 3;
    int threshold = 65;

    int mean = IntStream.of(this.generateValues(iterations)).sum();

    return mean >= threshold;
  }

  /***
   * Returns the direction for the vehicle to move, based on values sent in by
   * the line reader sensors.
   * @param values CAL-values from the sensors.
   * @return element in {-1,0,1} isomorphic to {left, middle, right}.
   *
   * TODO: think of something better than a hashmap and a switch statement moron...
   */
  public static int directionToMove(List<int[]> values) {

    boolean shouldStop = shouldStop(values);
    if (shouldStop) {
      return 402;
    }

    HashMap<Integer, Double> positionAndValueMap = new HashMap<>();
    double[] results = new double[values.size()];
    int comparisons = results.length;

    for (int i = 0; i < results.length; i++) {
      int[] tempValues = values.get(i);
      results[i] = (double) IntStream.of(tempValues).sum() / tempValues.length;
      positionAndValueMap.put(i, results[i]);
    }

    double min = findMinimum(results, comparisons);
    int index = getKeyByValue(positionAndValueMap, min);

    if (DoubleStream.of(results).sum() / results.length <= 80) {
      if (comparisons % 2 == 0) {
        switch (index) {
          case 0:
          case 1:
            return -1;
          case 2:
          case 3:
            return 0;
          case 4:
          case 5:
            return 1;
        }
      } else {
        switch (index) {
          case 0:
            return -1;
          case 1:
            return 0;
          case 2:
            return 1;
        }
      }
    }
    return 0;
  }

  /**
   * Compares all the values in the input array, if they are all the same, and are not white, we
   * have reached a stop.
   *
   * @param values input CAL values from the linereader.
   * @return should this vehicle stop? true for yes.
   */
  private static boolean shouldStop(List<int[]> values) {
    boolean white = values.get(0)[0] >= 96;
    if (white) {
      return true;
    }
    int compare = values.get(0)[0];
    for (int[] ints : values) {
      for (int i : ints) {
        if (i != compare) {
          return false;
        }
      }
    }
    return true;
  }

  private int[] generateValues(int iterations) {
    int[] values = new int[8];

    for (int i = 0; i < iterations; i++) {
      int[] newValues = this.getCALValues();
      for (int j = 0; j < values.length; j++) {
        values[j] += newValues[j];
      }
    }

    for (int i = 0; i < iterations; i++) {
      values[i] /= iterations;
    }

    return values;
  }

  private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
    for (Entry<T, E> entry : map.entrySet()) {
      if (Objects.equals(value, entry.getValue())) {
        return entry.getKey();
      }
    }
    return null;
  }

  private static double findMinimum(double[] results, int comparisons) {
    double midIndex = 1000.0d;
    if (comparisons % 2 == 1) {
      midIndex = results[(comparisons - 1) / 2];
    }

    double min = 100;

    for (int i = 0; i < comparisons / 2; i++) {
      double indexI = results[i];
      double indexJ = results[comparisons - 1 - i];

      if (indexI < indexJ && indexI < min) {
        min = indexI;
      } else if (indexJ < indexI && indexJ < min) {
        min = indexJ;
      }

    }

    if (midIndex < min) {
      return midIndex;
    }

    return min;
  }
}