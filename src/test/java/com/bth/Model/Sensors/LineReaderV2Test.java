package com.bth.Model.Sensors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.bth.Utilities.ShippingSystemUtilities;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LineReaderV2Test {

  int[] values, indices;

  @Before
  public void setUp() throws Exception {
    values = new int[]{92, 92, 10, 4, 7, 1, 1, 1};
  }

  @After
  public void tearDown() throws Exception {
    values = null;
    assertNull(values);
  }

  @Test
  public void directionToMoveTestOne() {
    indices = new int[]{3, 2, 3};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 3, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    assertEquals("Sensors found a black line to the right, should return 1 (right)", 100, ret);
  }

  @Test
  public void directionToMoveTestTwo() {
    indices = new int[]{3, 2, 3};
    values = new int[]{1, 7, 10, 4, 15, 2, 100, 68};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 3, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    assertEquals("Sensors found a black line to the left, should return -1 (left)", -1, ret);
  }

  @Test
  public void directionToMoveTestThree() {
    indices = new int[]{3, 2, 3};
    values = new int[]{95, 98, 100, 18, 18, 96, 98, 100};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 3, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    assertEquals("Sensors found a black line in the middle, should return 0 (do nothing)", 0,
        ret);
  }

  @Test
  public void directionToMoveTestFour() {
    indices = new int[]{1, 2, 1, 1, 2, 1};
    values = new int[]{16, 15, 13, 85, 88, 86, 85, 90};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, indices.length, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    assertEquals("Sensors found a black line to the left, direction should be -1 (left)", -1,
        ret);
  }

  @Test
  public void directionToMoveTestFive() {
    indices = new int[]{1, 2, 1, 1, 2, 1};
    values = new int[]{90, 94, 91, 85, 88, 13, 14, 13};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, indices.length, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    assertEquals("Sensors found a black line to the right, direction should be 1 (right)", 1,
        ret);

  }

  @Test
  public void directionToMoveTestSix() {
    values = new int[]{17, 17, 17, 17, 17, 17, 17, 17};
    indices = new int[]{1, 1, 1, 1, 1, 1, 1, 1};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 8, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    assertEquals("Sensors found a black line everywhere, must stop. Will return 402.", 402,
        ret);
  }

  @Test
  public void directionToMoveTestSeven() {
    values = new int[]{17, 17, 17, 17, 16, 17, 17, 17};
    indices = new int[]{1, 1, 1, 1, 1, 1, 1, 1};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 8, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    assertNotEquals(
        "Sensors did not find a black line everywhere, does not need to stop. Will not return 402.",
        402,
        ret);
  }
}