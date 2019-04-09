package com.bth.Controller;

import com.bth.Model.Trucks.ContainerTruck;
import com.bth.Model.Trucks.DeliveryTruck;
import com.bth.Model.Trucks.ForkliftTruck;
import com.bth.View.ShippingSystemView;
import java.util.ArrayList;

/***
 * Will handle all interactions between my trucks
 * Example of interactions:
 * TODO fix here
 */
public class ShippingSystemController {

  static final double leastDistance = 0.25; // 25 centimeters.

  // Models
  ArrayList<ForkliftTruck> forkliftTrucks;
  ArrayList<ContainerTruck> containerTrucks;
  ArrayList<DeliveryTruck> deliveryTrucks;

  // View
  ShippingSystemView view;

  public ShippingSystemController(ArrayList<ForkliftTruck> forkliftTrucks,
      ArrayList<ContainerTruck> containerTrucks,
      ArrayList<DeliveryTruck> deliveryTrucks, ShippingSystemView view) {

    this.view = view;
    this.containerTrucks = containerTrucks;
    this.deliveryTrucks = deliveryTrucks;
    this.forkliftTrucks = forkliftTrucks;
  }

  private void checkSurroundingsForTrucks() {
    for (ForkliftTruck ft : forkliftTrucks) {
      for (ContainerTruck ct : containerTrucks) {
      }
    }
  }

  public static double getLeastDistance() {
    return leastDistance;
  }

  public ArrayList<ForkliftTruck> getForkliftTrucks() {
    return forkliftTrucks;
  }

  public ArrayList<ContainerTruck> getContainerTrucks() {
    return containerTrucks;
  }

  public ArrayList<DeliveryTruck> getDeliveryTrucks() {
    return deliveryTrucks;
  }

  public ForkliftTruck getForkliftTruck(String name, int id) {
    for (ForkliftTruck ft : this.forkliftTrucks) {
      if (name.equals(ft.getName()) && id == ft.getId()) {
        return ft;
      }
    }
    return null;
  }

  public DeliveryTruck getDeliveryTruck(String name, int id) {
    for (DeliveryTruck dt : this.deliveryTrucks) {
      if (name.equals(dt.getName()) && id == dt.getId()) {
        return dt;
      }
    }
    return null;
  }

  public ContainerTruck getContainerTruck(String name, int id) {
    for (ContainerTruck ct : this.containerTrucks) {
      if (name.equals(ct.getName()) && id == ct.getId()) {
        return ct;
      }
    }
    return null;
  }

  public void setForkliftTrucks(ArrayList<ForkliftTruck> forkliftTrucks) {
    this.forkliftTrucks = forkliftTrucks;
  }

  public void setContainerTrucks(ArrayList<ContainerTruck> containerTrucks) {
    this.containerTrucks = containerTrucks;
  }

  public void setDeliveryTrucks(ArrayList<DeliveryTruck> deliveryTrucks) {
    this.deliveryTrucks = deliveryTrucks;
  }

  public void setView(ShippingSystemView view) {
    this.view = view;
  }

  public ShippingSystemView getView() {
    return view;
  }

  public void updateView() {
    this.view.printInfo(forkliftTrucks, containerTrucks, deliveryTrucks);
  }
}
