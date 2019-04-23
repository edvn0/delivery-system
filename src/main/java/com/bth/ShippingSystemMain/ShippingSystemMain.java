package com.bth.ShippingSystemMain;

import com.bth.Controller.ShippingSystemController;
import com.bth.Controller.Thread.DTThreadPooledServer;
import com.bth.Model.Trucks.DeliveryTruck;
import com.bth.View.ShippingSystemView;

public class ShippingSystemMain {

  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(ShippingSystemMain::run);
  }

  private static void run() {
    DeliveryTruck truck = new DeliveryTruck("Delivery Truck One", 1);

    ShippingSystem shippingSystem = new ShippingSystem();
    ShippingSystemView view = new ShippingSystemView(
        shippingSystem.getForklifts(),
        shippingSystem.getContainers(),
        shippingSystem.getDeliveries());
    ShippingSystemController controller = new ShippingSystemController(view, truck);

    System.out.println(controller.getDeliveryTruck().toString());

    controller.getDeliveryTruck().initializeMotors();
    controller.getDeliveryTruck().initializeSensors();

    // Pooled Server initialisation
    controller.initalizeRunThread("Main Thread");
    controller.setPooledServer(new DTThreadPooledServer("ServerThread-1", 8000));
    controller.getPooledServer().start();
    controller.runPooledServer(controller.getPooledServer());

    // DT initialisation
    controller.getDeliveryTruck().setSpeed(100);
    controller.getDeliveryTruck().move(1);
    controller.getDeliveryTruck().readLines(controller.getDeliveryTruck().getColor());

    controller.updateView();
  }
}
