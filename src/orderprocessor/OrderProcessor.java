package orderprocessor;


import facility.FacilityService;
import item.ItemService;
import utilities.ShortestPathService;

import java.util.HashMap;
import java.util.Map;


/**
 * orderprocessor.java
 * LogisticsApplication
 */
final public class OrderProcessor {
    private static OrderProcessor orderProcessorInstance;

    private OrderProcessor() {

    }
    public static OrderProcessor getOrderProcessorInstance() {
        if(orderProcessorInstance == null) {
            orderProcessorInstance = new OrderProcessor();
        }
        return orderProcessorInstance;
    }

    public boolean loadItemsFromSource(String itemSource) {
        return ItemService.getItemServiceInstance().loadItemsFromSource(itemSource);
    }

    public boolean loadFacilityNetworkFromSource(String facilityNetworkSource) {
        FacilityService facilityService = FacilityService.getFacilityServiceInstance();
        String facilitySource = "facilitynetwork.xml";
        String inventorySource = "inventories.xml";
        boolean loadFacilityNetworkStatus = facilityService.loadFacilityNetworkFromSource(facilitySource);
        facilityService.loadInventoryFromSource(inventorySource);
        return  loadFacilityNetworkStatus;
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
}
