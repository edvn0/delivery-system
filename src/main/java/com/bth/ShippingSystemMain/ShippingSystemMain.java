package com.bth.ShippingSystemMain;

import com.bth.Controller.ShippingSystemController;
import com.bth.Model.Trucks.DeliveryTruck;

public class ShippingSystemMain {

  public static final boolean DEV = true;

  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(ShippingSystemMain::run);
  }

  private static void run() {
    DeliveryTruck truck = new DeliveryTruck("Delivery Truck One", 1);

    ShippingSystemController controller = new ShippingSystemController(null, truck);

    // Pooled Server initialisation
    /*controller.setPooledServer(new DTThreadPooledServer("ServerThread-1", 8000));
    controller.getPooledServer().start();
    controller.runPooledServer(controller.getPooledServer());*/

    // DT initialisation
    controller.getDeliveryTruck().runTruck();

    /*
    long time = System.currentTimeMillis();
    long end = time + 100000;
    while (System.currentTimeMillis() < end) {
      controller.getDeliveryTruck().runTruck();
    }
    */
    System.exit(0);
  }
}
