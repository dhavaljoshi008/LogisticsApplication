package orderprocessor;


import exceptions.InvalidArgumentException;
import facility.FacilityService;
import item.ItemService;
import order.OrderService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import utilities.ShortestPathService;

import java.util.Collections;
import java.util.List;


/**
 * orderprocessor.java
 * LogisticsApplication
 */
final public class OrderProcessingService {
    private static OrderProcessingService orderProcessingServiceInstance;
    private List<String> orderList;
    private OrderProcessor orderProcessingDelegate;
    private OrderProcessingService(String type) {
        orderProcessingDelegate = OrderProcessingAlgorithmFactory.build(type);
    }

    public static OrderProcessingService getOrderProcessingServiceInstance() {
        if(orderProcessingServiceInstance == null) {
            orderProcessingServiceInstance = new OrderProcessingService("time");
        }
        return orderProcessingServiceInstance;
    }

    public void changeOrderProcessingAlgorithm(String type) {
        orderProcessingDelegate = OrderProcessingAlgorithmFactory.build(type);
    }
    public boolean loadItemsFromSource(String itemSource) {
        return ItemService.getItemServiceInstance().loadItemsFromSource(itemSource);
    }

    public boolean loadFacilityNetworkFromSource(String facilityNetworkSource) throws InvalidArgumentException {
        return  FacilityService.getFacilityServiceInstance().loadFacilityNetworkFromSource(facilityNetworkSource);
    }

    public boolean loadInventoryFromSource(String inventorySource) throws InvalidArgumentException {
        return FacilityService.getFacilityServiceInstance().loadInventoryFromSource(inventorySource);
    }

   public void generateFacilityStatusOutputForAllFacilities() {
       System.out.println();
       FacilityService.getFacilityServiceInstance().generateFacilityStatusOutputForAllFacilities();
   }

   public void printShortestPathBetween(String source, String destination, double drivingHoursPerDay, double milesPerHour ) {
       ShortestPathService shortestPathService = ShortestPathService.getShortestPathServiceInstance();
       System.out.println(source + " to" + " " + destination);
       System.out.println("\t" + "<> " + shortestPathService.getShortestPathBetween(source, destination));
       System.out.println("\t" + "<> " + shortestPathService.getShortestDistanceBetween(source, destination) + "mi/" + "(" + drivingHoursPerDay + " * " + milesPerHour + ")" + " = " + String.format("%.2f", shortestPathService.getShortestPathDaysBetween(source, destination, drivingHoursPerDay, milesPerHour)) + " days");
       System.out.println();

   }
   public boolean loadOrdersFromSource(String source) throws InvalidArgumentException {
      OrderService orderService = OrderService.getOrderServiceInstance();
      boolean result = orderService.loadOrdersFromSource(source);
      orderList = orderService.getOrderListSortedByTime();
      return result;
   }

   public boolean processAllOrders() {

       if(orderList != null && !orderList.isEmpty()){
           for(String orderId: orderList) {
               orderProcessingDelegate.processOrder(orderId);
           }
           return true;
       }
       return false;
   }

  public void printProcessingSolutionForOrder(String orderId) {
      if(orderList != null && !orderList.isEmpty() && orderList.contains(orderId)) {
          System.out.println(orderProcessingDelegate.getProcessingSolutionForOrder(orderId));
      }
  }

  public void printAllProcessingSolutions() {
      int i = 0;
      System.out.println("\n");
      for(String orderId: orderList) {
          i++;
          System.out.println("Order #"+ i);
          printProcessingSolutionForOrder(orderId);
      }

  }
}
