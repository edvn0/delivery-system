package com.bth.Controller;

import com.bth.Controller.DeliveryThread.DeliveryTruckRunnable;
import com.bth.Controller.Thread.DTThreadPooledServer;
import com.bth.Model.Truck;
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

  public boolean initalizeRunThread(String id) {
    DeliveryTruckRunnable run;
    run = new DeliveryTruckRunnable(id);

    run.setTruck(truck);
    run.start();
    Truck.runThreadIsStarted = true;
    Truck.runThreadIsExecuted = false;
    return true;
  }

  public void updateView() {
    System.out.println("DO STUFF");

    while (!DeliveryTruck.runThreadIsExecuted) {

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
  }

  public void setPooledServer(DTThreadPooledServer dtThreadPooledServer) {
    this.pooledServer = dtThreadPooledServer;
  }

  public void runPooledServer(DTThreadPooledServer pooledServer) {
    while (Truck.isRunning) {
      if (Truck.inputCommandSCS.equals("KILL")) {
        Truck.isRunning = false;
      }

      if (Truck.inputCommandSCS.equals("RUN") && (!Truck.runThreadIsStarted)) {
        initalizeRunThread("RUN Thread");
      }

      if (!Truck.runThreadIsExecuted) {
        try {
          Thread.sleep(30 * 100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } else {
        Truck.inputCommandSCS = "";
        Truck.runThreadIsStarted = false;
      }

      if (Truck.outputCommandSCS.equals("FINISHED")) {
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

  public ArrayList<ForkliftTruck> getForkliftTrucks() {
    return forkliftTrucks;
  }

  public ArrayList<ContainerTruck> getContainerTrucks() {
    return containerTrucks;
  }

  public ArrayList<DeliveryTruck> getDeliveryTrucks() {
    return deliveryTrucks;
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
