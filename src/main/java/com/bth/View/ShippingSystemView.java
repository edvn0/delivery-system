package com.bth.View;

import com.bth.Model.Trucks.ContainerTruck;
import com.bth.Model.Trucks.DeliveryTruck;
import com.bth.Model.Trucks.ForkliftTruck;
import java.util.ArrayList;
import java.util.Arrays;

public class ShippingSystemView {

  public ShippingSystemView() {
    // Implement something here.
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

}
