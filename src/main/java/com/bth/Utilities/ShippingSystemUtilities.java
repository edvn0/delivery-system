package com.bth.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.IntStream;

public class ShippingSystemUtilities {

  /***
   * Splits an integer array into n parts, with arbitrary sizes. Essentially a
   * custom partition.
   * Example:
   * int[]{10,10,1,1,2,3,4} = @param array, 3 = @param num, int[]{3,1,3} = @param splitIndex
   * int[]{10,10,1,1,2,3,4} becomes int[]{10,10,1}, int[]{1}, int[]{2,3,4}
   * @param array Array to be partitioned.
   * @param num Number of pieces.
   * @param splitIndices Size of pieces.
   * @return ArrayList of the split int arrays.
   */
  public static List<int[]> splitArray(int[] array, int num, int[] splitIndices) {
    List<int[]> ints = new ArrayList<>();

    // Checks to see if you even can split the array into these chunks...
    if (IntStream.of(splitIndices).sum() != array.length
        || num != splitIndices.length) {
      return null;
    }

    int k = 0;
    int index = 0;
    for (int i = 0; i < num; i++) {
      int[] temp = new int[splitIndices[k]];

      if (index + splitIndices[k] - index >= 0) {
        System.arraycopy(array, index, temp, 0, index + splitIndices[k] - index);
      }

      index += splitIndices[k++];

      ints.add(temp);
    }

    return ints;
  }

  /**
   * Splits an int array into splitIndices.length pieces.
   *
   * @param array input array of integers.
   * @param splitIndices amount of ints in each of the split arrays.
   * @return ArrayList of split integer arrays.
   *
   * see {@link #splitArray(int[], int, int[])}
   */
  public static List<int[]> splitArray(int[] array, int[] splitIndices) {
    return splitArray(array, splitIndices.length, splitIndices);
  }

  /**
   * Splits an int array into standard size for the LineReader.
   *
   * @param array int array to be split
   * @return ArrayList with 8 ints, sizes 1,1,1,1,1,1,1,1.
   *
   * see {@link #splitArray(int[], int, int[])}
   */
  public static List<int[]> splitArray(int[] array) {
    return splitArray(array, 8, new int[]{1, 1, 1, 1, 1, 1, 1, 1});
  }

  /**
   * Finds which way the truck should move according to the sensor values.
   *
   * @param values sensor values
   * @return directional velocity for truck, values in (-80,80)
   */
  public static int directionToMove(List<int[]> values) {
    // Check if we lost the line, if so, we return some sort of exit code, this case 402.
    boolean shouldStop = checkIfLostLine(values);
    if (shouldStop) {
      return 402;
    }

    HashMap<Integer, Integer> sensorMap = new HashMap<>();
    int k = 0;
    for (int[] ints : values) {
      int average = IntStream.of(ints).sum() / ints.length;
      sensorMap.put(k++, average);
    }

    // Get the lowest value in the array, and find corresponding sensor by that value.
    int minValue = minimumFinder(values);
    int sensor = getKeyByValue(sensorMap, minValue);

    System.out.println("Sensor " + sensor + " returned the lowest value of " + minValue);

    switch (sensor) {
      case 0:
        return -80;
      case 1:
      case 2:
        return -20;
      case 3:
      case 4:
        return 0;
      case 5:
        return 20;
      case 6:
      case 7:
        return 80;
    }
    return 0;
  }

  /**
   * Compares all the values in the input array, if they are all the same, and are not white, we
   * have reached a stop. Allows the value to be between 0 <= 25.
   *
   * @param values input CAL values from the line reader.
   * @return should this vehicle stop? true for yes.
   */
  public static boolean checkIfLostLine(List<int[]> values) {
    int compare = 0;
    int offset = 10;

    for (int[] ints : values) {
      compare += IntStream.of(ints).sum() / ints.length;
    }

    compare /= values.size();
    System.out.println(compare);

    // If this one is white, we should stop.
    if (compare >= 90) {
      return true;
    }

    int max = compare + offset;
    int min = compare - offset;

    // Check if all values are black...
    for (int[] ints : values) {
      for (int i : ints) {
        if (!(i <= max && i >= min)) {
          // compare = 25 (approx) => this returns false if i \notin [22,28]
          System.out.println(compare - offset);
          System.out.println(compare + offset);
          System.out.println(i + " " + (i >= compare - offset) + " " + (i - offset <= compare));
          return false;
        }
      }
    }
    return true;
  }

  private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
    for (Entry<T, E> entry : map.entrySet()) {
      if (Objects.equals(value, entry.getValue())) {
        return entry.getKey();
      }
    }
    return null;
  }

  /**
   * Checks the double array pairwise, from start to end. check d[0] vs d[size - 1], d[1] vs d[size
   * - 2] etc. Heuristic for movement.
   *
   * @param results CAL values to determine minimum for
   */
  private static double findMinimum(double[] results) {

    // Pulls out the value in the middle in case of (results.length % 2 == 1).
    // if this later on is less then the established min, use this instead.
    double midIndex = 1000.0d;

    if (results.length % 2 == 1) {
      midIndex = results[(results.length - 1) / 2];
    }

    double min = 100;

    for (int i = 0; i < results.length / 2; i++) {
      double indexI = results[i];
      double indexJ = results[results.length - 1 - i];

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

  private static int minimumFinder(List<int[]> ints) {
    int minValue = ints.get(0)[0];
    for (int j = 1; j < ints.size(); j++) {
      int valueInValues = ints.get(j)[0];
      if (valueInValues < minValue) {
        minValue = valueInValues;
      }
    }
    return minValue;
  }
}
