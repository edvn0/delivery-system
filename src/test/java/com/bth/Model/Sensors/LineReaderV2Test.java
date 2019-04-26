package com.bth.Model.Sensors;

import com.bth.Utilities.ShippingSystemUtilities;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LineReaderV2Test {

  int[] values, indices;

  @Before
  public void setUp() throws Exception {
    values = new int[]{100, 92, 10, 4, 1, 2, 7, 1};
  }

  @After
  public void tearDown() throws Exception {
    values = null;
  }

  @Test
  public void directionToMoveTestOne() {
    indices = new int[]{3, 2, 3};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 3, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    Assert.assertEquals("Should return -1", -1, ret);
  }

  @Test
  public void directionToMoveTestTwo() {
    indices = new int[]{3, 2, 3};
    values = new int[]{1, 7, 10, 4, 15, 2, 100, 68};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 3, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    Assert.assertEquals("Should return 1", 1, ret);
  }

  @Test
  public void directionToMoveTestThree() {
    indices = new int[]{3, 2, 3};
    values = new int[]{1, 5, 10, 100, 100, 2, 7, 1};

    List<int[]> finalValues = ShippingSystemUtilities.splitArray(values, 3, indices);

    int ret = LineReaderV2.directionToMove(finalValues);

    Assert.assertEquals("Should return 0", 0, ret);
  }
}