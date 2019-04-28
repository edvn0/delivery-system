package com.bth.Utilities;

import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.DoubleStream;
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
  public static List<int[]> splitArray(@NotNull int[] array, int num, int[] splitIndices) {
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
   * @return ArrayList with 3 ints, sizes 3,2,3.
   *
   * see {@link #splitArray(int[], int, int[])}
   */
  public static List<int[]> splitArray(int[] array) {
    return splitArray(array, 3, new int[]{3, 2, 3});
  }

  /**
   * Returns the direction for the vehicle to move, based on values sent in by the line reader
   * sensors.
   *
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

    HashMap<Double, Integer> valuesAndSensor = new HashMap<>();

    double[] results = new double[values.size()];
    int comparisons = results.length;

    for (int i = 0; i < results.length; i++) {
      int[] tempValues = values.get(i);
      results[i] = (double) IntStream.of(tempValues).sum() / tempValues.length;
      valuesAndSensor.put(results[i], i);
    }

    double min = findMinimum(results);
    int index = valuesAndSensor.get(min);

    // Busy work heuristic check if we are seeing something not white.
    if (DoubleStream.of(results).sum() / results.length <= 80) {

      // TODO: refactor, this is really bad. Make it more scalable!!!
      // This is a "nice" solution to the hardcoded solution with either 6 inputs or 3.
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
   * have reached a stop. Allows the value to be between 0 <= 25.
   *
   * @param values input CAL values from the line reader.
   * @return should this vehicle stop? true for yes.
   */
  public static boolean shouldStop(List<int[]> values) {
    int compare = 0;
    int offset = 6;

    for (int[] vals : values) {
      compare += IntStream.of(vals).sum() / vals.length;
    }

    compare /= values.size();

    // If this one is white, we should stop.
    if (compare >= 90) {
      return true;
    }

    // Check if all values are black...
    for (int[] ints : values) {
      for (int i : ints) {
        if (!(i >= compare - offset && i - offset <= compare)) {
          // compare = 25 (approx) => this returns false if i \notin [22,28]
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
}
