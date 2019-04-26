package com.bth.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShippingSystemUtilities {

  /***
   * Splits an integer array into n parts, with arbitrary sizes. Essentially a
   * custom partition.
   * Example:
   * int[]{10,10,1,1,2,3,4} = @param array, 3 = @param num, int[]{3,1,3} = @param splitIndex
   * int[]{10,10,1,1,2,3,4} |-> int[]{10,10,1}, int[]{1}, int[]{2,3,4}
   * @param array Array to be split.
   * @param num Number of pieces.
   * @param splitIndices Size of pieces.
   * @return ArrayList of the split int arrays.
   *
   * TODO: check sum of splitIndices match with array.length
   * TODO: check that the array can actually be split into num splits..
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
   */
  public static List<int[]> splitArray(int[] array, int[] splitIndices) {
    return splitArray(array, splitIndices.length, splitIndices);
  }

  /**
   * Splits an int array into standard size for the LineReader.
   *
   * @param array int array to be split
   * @return ArrayList with 3 ints, sizes 3,2,3.
   */
  public static List<int[]> splitArray(int[] array) {
    return splitArray(array, 3, new int[]{3, 2, 3});
  }

}
