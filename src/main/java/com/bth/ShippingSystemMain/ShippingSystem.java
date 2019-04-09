package com.bth.ShippingSystemMain;

import com.bth.Model.Trucks.ContainerTruck;
import com.bth.Model.Trucks.DeliveryTruck;
import com.bth.Model.Trucks.ForkliftTruck;
import java.util.ArrayList;

public class ShippingSystem {

  public static ArrayList<ForkliftTruck> forklifts;
  public static ArrayList<ContainerTruck> containers;
  public static ArrayList<DeliveryTruck> deliveries;

  public ShippingSystem() {
    forklifts = new ArrayList<>();
    forklifts.add(new ForkliftTruck("First FT", 1));
    forklifts.add(new ForkliftTruck("Second FT", 2));

    containers = new ArrayList<>();
    containers.add(new ContainerTruck("First CT", 1));
    containers.add(new ContainerTruck("Second CT", 2));

    deliveries = new ArrayList<>();
    deliveries.add(new DeliveryTruck("First DT", 1));
    deliveries.add(new DeliveryTruck("Second DT", 2));
  }

  public static ArrayList<ForkliftTruck> getForklifts() {
    return forklifts;
  }

  public static ArrayList<ContainerTruck> getContainers() {
    return containers;
  }

  public static ArrayList<DeliveryTruck> getDeliveries() {
    return deliveries;
  }
}
