package com.bth.Model.Sensors;

import static com.bth.Utilities.ShippingSystemUtilities.followTheLine;
import static com.bth.Utilities.ShippingSystemUtilities.splitArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LineReaderV2Test {

  private int[] values;


  @Before
  public void setUp() throws Exception {
    values = new int[]{92, 92, 10, 4, 7, 2, 1, 1};
  }

  @After
  public void tearDown() throws Exception {
    values = null;
    assertNull(values);
  }

  @Test
  public void directionToMoveTestOne() {
    List<int[]> finalValues = splitArray(values);

    int ret = followTheLine(finalValues);

    assertEquals("Sensors found a black line to the right, should return 80 (right)", 80, ret);
  }

  @Test
  public void directionToMoveTestTwo() {
    values = new int[]{1, 7, 10, 4, 15, 2, 100, 68};
    List<int[]> finalValues = splitArray(values);
    int ret = followTheLine(finalValues);
    assertEquals("Sensors found a black line to the left, should return -80 (left)", -80, ret);
  }

  @Test
  public void directionToMoveTestThree() {
    values = new int[]{95, 98, 100, 18, 18, 96, 98, 100};

    List<int[]> finalValues = splitArray(values);

    int ret = followTheLine(finalValues);

    assertEquals("Sensors found a black line in the middle, should return 0 (do nothing)", 0,
        ret);
  }

  @Test
  public void directionToMoveTestFour() {
    values = new int[]{16, 15, 13, 85, 88, 86, 85, 90};

    List<int[]> finalValues = splitArray(values);

    int ret = followTheLine(finalValues);

    assertEquals("Sensors found a black line to the left, direction should be 20 (little left)",
        -20,
        ret);
  }

  @Test
  public void directionToMoveTestFive() {
    values = new int[]{90, 94, 91, 85, 88, 98, 14, 13};

    List<int[]> finalValues = splitArray(values);

    int ret = followTheLine(finalValues);

    assertEquals("Sensors found a black line to the right, direction should be 80 (right)", 80,
        ret);
  }

  @Test
  public void directionToMoveTestSix() {
    values = new int[]{17, 17, 17, 17, 17, 17, 17, 17};

    List<int[]> finalValues = splitArray(values);

    int ret = followTheLine(finalValues);

    assertEquals("Sensors found a black line everywhere, must stop. Will return 402.", 402,
        ret);
  }

  @Test
  public void directionToMoveTestSeven() {
    values = new int[]{17, 17, 17, 17, 16, 17, 17, 17};

    List<int[]> finalValues = splitArray(values);

    int ret = followTheLine(finalValues);

    assertEquals(
        "Sensors did find a black line everywhere, needs to stop. Should return 402.",
        402,
        ret);
  }

  @Test
  public void directionToMoveTestEight() {
    List<int[]> finalValues = splitArray(values);

    int ret = followTheLine(finalValues);

    assertEquals("Sensors found a black line to the right, should return 80 (right)", 80, ret);
  }

  @Test
  public void followTheLineTestOne() {
    values = new int[]{100, 100, 100, 19, 12, 10, 53, 100};
    List<int[]> test = splitArray(values);

    int val = followTheLine(test);

    assertEquals("Should be 20", 20, val);
  }
}