package com.bth.Model;

import java.util.Vector;

public interface Truck {

  /***
   * Moves the truck in a direction,
   * @param dir dir \in (0,1,2,3) \equiv (left, right, up, down)
   */
  void move(int dir);

  /**
   * Stops the truck.
   */
  void stop();

  /**
   * Reads information regarding packages or delivery tasks in a barcode.
   *
   * @param barcode input barcode for the truck.
   * @return true if read correctly
   */
  boolean readInfo(int barcode);

  /***
   * Checks floor for lines
   * @param color color of the lines. Is associated with type of truck.
   */
  void readLines(int color);

  Vector<Double> getPosition();

  String getName();

  int getId();
}
