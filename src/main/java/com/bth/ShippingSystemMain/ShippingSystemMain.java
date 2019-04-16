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
          shippingSystem.getForklifts(),
          shippingSystem.getContainers(),
          shippingSystem.getDeliveries(),
          view);

      controller.updateView();

      controller.getForkliftTruck("First FT", 1).setName("Edwins FT");
      controller.getDeliveryTruck("First DT", 1).setName("Cajsas DT");

      controller.updateView();
    });
  }

}
