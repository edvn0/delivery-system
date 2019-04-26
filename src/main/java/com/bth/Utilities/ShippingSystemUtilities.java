package com.bth.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShippingSystemUtilities {

  /***
   * Splits an integer array into n parts, with arbitrary sizes.
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

}
