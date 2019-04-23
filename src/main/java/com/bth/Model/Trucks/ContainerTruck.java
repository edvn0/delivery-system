package com.bth.Model.Trucks;

import com.bth.Model.Truck;
import com.bth.Model.TruckColourEnum;

public class ContainerTruck extends Truck {

  private String name;
  private int colorForLines;
  private int id;

  public ContainerTruck(String name, int id) {
    this.name = name;
    this.colorForLines = TruckColourEnum.GREEN.getColor();
    this.id = id;
  }


  @Override
  public void move(int dir) {
  }

  @Override
  public void stop() {
  }

  @Override
  public void craneStart() {

  }

  @Override
  public void craneStop() {

  }

  @Override
  public boolean readInfo(int barcode) {
    return false;
  }

  @Override
  public void readLines(int color) {

  }

  @Override
  public String toString() {
    return "Container Truck \"" + this.name + "\"\n";
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getId() {
    return this.id;
  }
}
