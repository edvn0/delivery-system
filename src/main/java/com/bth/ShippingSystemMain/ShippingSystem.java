package com.bth.ShippingSystemMain;

import com.bth.Model.Trucks.ContainerTruck;
import com.bth.Model.Trucks.DeliveryTruck;
import com.bth.Model.Trucks.ForkliftTruck;
import java.util.ArrayList;

public class ShippingSystem {

  private ArrayList<ForkliftTruck> forklifts;
  private ArrayList<ContainerTruck> containers;
  private ArrayList<DeliveryTruck> deliveries;

  ShippingSystem() {
    // TODO full integration with FT and CT.
    forklifts = new ArrayList<>();
    containers = new ArrayList<>();

    // Here we init all dt:s, I presume there will be only one though.
    deliveries = new ArrayList<>();
  }

  public ArrayList<ForkliftTruck> getForklifts() {
    return forklifts;
  }

  public ArrayList<ContainerTruck> getContainers() {
    return containers;
  }

  public ArrayList<DeliveryTruck> getDeliveries() {
    return deliveries;
  }
}
