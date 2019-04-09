package com.bth.Model;

public enum TruckColourEnum {
  BLUE(1), GREEN(2), RED(3);

  int id;

  TruckColourEnum(int id) {
    this.id = id;
  }

  public int colorId() {
    return id;
  }
}
