package com.bth.Model;

import java.awt.Color;

public enum TruckColourEnum {
  BLUE(Color.BLUE), GREEN(Color.GREEN), RED(Color.RED);

  int color;

  TruckColourEnum(Color color) {
    this.color = color.getRGB();
  }

  public int getColor() {
    return color;
  }
}
