package com.bth.Utilities;

import static com.bth.Utilities.ShippingSystemUtilities.checkIfLostLine;
import static com.bth.Utilities.ShippingSystemUtilities.splitArray;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShippingSystemUtilitiesTest {

  private int[] values, test1, test2, test3, indices;

  @Before
  public void setUp() throws Exception {
    values = new int[]{100, 92, 10, 4, 1, 2, 7, 1};
  }

  @After
  public void tearDown() throws Exception {
    values = null;
  }

  @Test
  public void splitIntegerArrayTestOne() {
    test1 = new int[]{100, 92, 10};
    test2 = new int[]{4, 1};
    test3 = new int[]{2, 7, 1};
    indices = new int[]{3, 2, 3};
    List<int[]> ints = splitArray(values, 3, indices);
    boolean test = true;

    if (values != null) {
      for (int[] values : ints) {
        if (!(Arrays.equals(values, test1) || Arrays.equals(values, test2) || Arrays
            .equals(values, test3))) {
          test = false;
          break;
        }
      }
    } else {
      test = false;
    }

    assertTrue("Should be true", test);
  }

  @Test
  public void splitIntegerArrayTestTwo() {
    test1 = new int[]{100, 92};
    test2 = new int[]{10, 4};
    test3 = new int[]{1, 2};
    int[] test4 = new int[]{7, 1};
    indices = new int[]{2, 2, 2, 2};

    boolean test = true;

    List<int[]> tests = splitArray(values, 4, indices);

    if (tests != null) {
      for (int[] in : tests) {
        if (!(Arrays.equals(in, test1)
            || Arrays.equals(in, test2)
            || Arrays.equals(in, test3)
            || Arrays.equals(in, test4))) {
          test = false;
          break;
        }
      }
    } else {
      test = false;
    }
    assertTrue("Should be true", test);
  }

  @Test
  public void checkIfLostLineTestOne() {
    values = new int[]{14, 15, 13, 12, 11, 10, 14, 18};
    List<int[]> test = splitArray(values);

    boolean shouldStop = checkIfLostLine(test);

    assertTrue("Should stop", shouldStop);
  }

  @Test
  public void checkIfLostLineTestTwo() {
    values = new int[]{100, 100, 100, 100, 100, 100, 100, 100};
    List<int[]> test = splitArray(values);

    boolean shouldStop = checkIfLostLine(test);

    assertTrue("Should stop", shouldStop);
  }

  @Test
  public void checkIfLostLineTestThree() {
    values = new int[]{0, 0, 0, 100, 100, 100, 100, 100};
    List<int[]> test = splitArray(values);

    boolean shouldStop = checkIfLostLine(test);

    assertFalse("Should not stop", shouldStop);
  }

  @Test
  public void checkIfLostLineTestFour() {
    values = new int[]{34, 34, 100, 100, 100, 100, 100, 100};
    List<int[]> test = splitArray(values);

    boolean shouldStop = checkIfLostLine(test);

    assertFalse("Should stop", shouldStop);
  }

}
