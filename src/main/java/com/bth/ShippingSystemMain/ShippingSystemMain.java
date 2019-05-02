package com.bth.ShippingSystemMain;

import com.bth.Controller.ShippingSystemController;
import com.bth.Model.Trucks.DeliveryTruck;

class ShippingSystemMain {

  public static final boolean DEV = true;

  public static void main(String[] args) { java.awt.EventQueue.invokeLater(ShippingSystemMain::run);
  }

  private static void run() {
    DeliveryTruck truck = new DeliveryTruck("Delivery Truck One", 1);

    ShippingSystemController controller = new ShippingSystemController(null, truck);

    //controller.getDeliveryTruck().runTruck();
  controller.getDeliveryTruck().move(0);
    System.exit(0);
  }
}
