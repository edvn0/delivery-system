package com.bth.Model.Trucks;

import com.bth.Model.Truck;

public class ForkliftTruck extends Truck {

  private String name;
  private int id;

  public ForkliftTruck(String name, int id) {
    this.name = name;
    this.id = id;
  }

  @Override
  public void move(int dir) {
  }

  @Override
  public void runTruck() {

  }

  @Override
  public void craneStart() {

  }

  @Override
  public void craneStop() {

  }

  @Override
  public void stop() {
  }

  @Override
  public boolean readInfo(int barcode) {
    return false;
  }

  @Override
  public void readLines() {

  }

  @Override
  public String toString() {
    return "Forklift Truck \"" + this.name + "\"\n";
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
