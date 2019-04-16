package com.bth.ShippingSystemMain;

import com.bth.Controller.ShippingSystemController;
import com.bth.View.ShippingSystemView;

public class ShippingSystemMain {

  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> {
      ShippingSystem shippingSystem = new ShippingSystem();
      ShippingSystemView view = new ShippingSystemView(
          shippingSystem.getForklifts(),
          shippingSystem.getContainers(),
          shippingSystem.getDeliveries());

      ShippingSystemController controller = new ShippingSystemController(
          view);

      controller.getDeliveryTruck("Delivery Truck One", 1).initializeMotors();
      controller.getDeliveryTruck("Delivery Truck One", 1).initializeSensors();

      controller
          .initalizeRunThread(controller.getDeliveryTruck("Delivery Truck One", 1).getRunThread(),
              1);

      controller.updateView();

    });
  }

}
