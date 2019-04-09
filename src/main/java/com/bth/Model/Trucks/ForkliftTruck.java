package com.bth.Model.Trucks;

import com.bth.Model.Truck;
import com.bth.Model.TruckColourEnum;
import java.util.Vector;

public class ForkliftTruck implements Truck {

  private Vector<Double> position;

  private String name;
  private int colorForLines;
  private int id;

  public ForkliftTruck(String name, int id) {
    this.name = name;
    this.colorForLines = TruckColourEnum.BLUE.colorId();
    this.id = id;
  }


  @Override
  public void move(int dir) {
  }

  @Override
  public void stop() {
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
    return "Forklift Truck \"" + this.name + "\"\n";
  }


  @Override
  public Vector<Double> getPosition() {
    return this.position;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getId() {
    return this.id;
  }

  public void setName(String name) {
    this.name = name;
  }
}
