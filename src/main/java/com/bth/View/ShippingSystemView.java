package com.bth.View;

import com.bth.Model.Trucks.ContainerTruck;
import com.bth.Model.Trucks.DeliveryTruck;
import com.bth.Model.Trucks.ForkliftTruck;
import java.util.ArrayList;
import java.util.Arrays;

public class ShippingSystemView {

  private ArrayList<ForkliftTruck> forkliftTrucks;
  private ArrayList<ContainerTruck> containerTrucks;
  private ArrayList<DeliveryTruck> deliveryTrucks;

  public ShippingSystemView(ArrayList<ForkliftTruck> forkliftTrucks,
      ArrayList<ContainerTruck> containerTrucks,
      ArrayList<DeliveryTruck> deliveryTrucks) {

    this.forkliftTrucks = forkliftTrucks;
    this.containerTrucks = containerTrucks;
    this.deliveryTrucks = deliveryTrucks;
  }

  public void printInfo(
      ArrayList<ForkliftTruck> forkliftTrucks,
      ArrayList<ContainerTruck> containerTrucks,
      ArrayList<DeliveryTruck> deliveryTrucks) {

    System.out.println(
        "Forklift trucks: [ " + Arrays.toString(forkliftTrucks.toArray()) + " ]\n"
            + "Container trucks: [ " + Arrays.toString(containerTrucks.toArray())
            + " ]\n"
            + "Delivery trucks: [ " + Arrays.toString(deliveryTrucks.toArray())
            + " ]\n"
    );
  }

  public ArrayList<ForkliftTruck> getForkliftTrucks() {
    return forkliftTrucks;
  }

  public void setForkliftTrucks(ArrayList<ForkliftTruck> forkliftTrucks) {
    this.forkliftTrucks = forkliftTrucks;
  }

  public ArrayList<ContainerTruck> getContainerTrucks() {
    return containerTrucks;
  }

  public void setContainerTrucks(ArrayList<ContainerTruck> containerTrucks) {
    this.containerTrucks = containerTrucks;
  }

  public ArrayList<DeliveryTruck> getDeliveryTrucks() {
    return deliveryTrucks;
  }

  public void setDeliveryTrucks(ArrayList<DeliveryTruck> deliveryTrucks) {
    this.deliveryTrucks = deliveryTrucks;
  }
}
