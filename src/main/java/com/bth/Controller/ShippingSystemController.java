package com.bth.Controller;

import com.bth.Controller.DeliveryThread.DeliveryTruckRunnable;
import com.bth.Controller.Thread.DTThreadPooledServer;
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

  private static final double leastDistance = 0.25; // 25 centimeters.

  // Server stuff
  private DTThreadPooledServer pooledServer;
  private DeliveryTruck truck;

  // Models
  private ArrayList<ForkliftTruck> forkliftTrucks;
  private ArrayList<ContainerTruck> containerTrucks;
  private ArrayList<DeliveryTruck> deliveryTrucks;

  // View
  private ShippingSystemView view;

  public ShippingSystemController(ShippingSystemView view, DeliveryTruck truck) {
    this.view = view;
    this.truck = truck;

    // Null for now! Todo: fix.
    this.containerTrucks = null;
    this.deliveryTrucks = null;
    this.forkliftTrucks = null;
  }

  public boolean initalizeRunThread(int id) {
    DeliveryTruckRunnable run;
    run = new DeliveryTruckRunnable(id);
    DeliveryTruck.runThreadIsExecuted = true;
    DeliveryTruck.runThreadIsStarted = true;
    run.setTruck(truck);
    run.start();
    return true;
  }

  public void updateView() {
    while (!DeliveryTruck.runThreadIsExecuted) {
      System.out.println("thread executed " + DeliveryTruck.runThreadIsExecuted);

      try {
        Thread.sleep(10 * 100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if (DeliveryTruck.runThreadIsExecuted) {
        DeliveryTruck.inputCommandSCS = "";
        DeliveryTruck.runThreadIsStarted = false;
        DeliveryTruck.isRunning = false;
      }
    }
    this.view.printInfo(forkliftTrucks, containerTrucks, deliveryTrucks);
  }

  public void setPooledServer(DTThreadPooledServer dtThreadPooledServer) {
    this.pooledServer = dtThreadPooledServer;
  }

  public void runPooledServer() {
    while (DeliveryTruck.isRunning) {
      if (DeliveryTruck.inputCommandSCS.equals("KILL")) {
        DeliveryTruck.isRunning = false;
      }

      if (DeliveryTruck.inputCommandSCS.equals("RUN") && (!DeliveryTruck.runThreadIsStarted)) {
        initalizeRunThread(1);
      }

      if (!DeliveryTruck.runThreadIsExecuted) {
        try {
          Thread.sleep(10 * 100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } else {
        DeliveryTruck.inputCommandSCS = "";
        DeliveryTruck.runThreadIsStarted = false;
      }

      if (DeliveryTruck.outputCommandSCS.equals("FINISHED")) {
        System.out.println("Main is finished.");
        pooledServer.isRunning();
      }

    }
  }

  public DTThreadPooledServer getPooledServer() {
    return this.pooledServer;
  }

  public DeliveryTruck getDeliveryTruck() {
    return this.truck;
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
}
