package main;

import exceptions.InvalidArgumentException;
import facility.FacilityService;
import order.OrderService;
import orderprocessor.OrderProcessingService;
import orderprocessor.OrderProcessorTimePriorityImpl;

import java.util.ArrayList;

/**
 * Main.java
 * LogisticsApplication
 */
public class Main {
    public static void main(String[] args) throws InvalidArgumentException {
        OrderProcessingService orderProcessingService = OrderProcessingService.getOrderProcessingServiceInstance();
        orderProcessingService.loadItemsFromSource("items.xml");
        orderProcessingService.loadFacilityNetworkFromSource("facilityNetwork.xml");
        orderProcessingService.loadInventoryFromSource("inventories.xml");
        orderProcessingService.loadOrdersFromSource("orders.xml");
        orderProcessingService.generateFacilityStatusOutputForAllFacilities();

//        Map<String, String> sourceDestination = new HashMap<>();
//        sourceDestination.put("Santa Fe, NM", "Chicago, IL");
//        sourceDestination.put("Atlanta, GA", "St. Louis, MO");
//        sourceDestination.put("Seattle, WA", "Nashville, TN");
//        sourceDestination.put("New York City, NY", "Phoenix, AZ");
//        sourceDestination.put("Fargo, ND", "Austin, TX");
//        sourceDestination.put("Denver, CO", "Miami, FL");
//        sourceDestination.put("Austin, TX", "Norfolk, VA");
//        sourceDestination.put("Miami, FL", "Seattle, WA");
//        sourceDestination.put("Los Angeles, CA", "Chicago, IL");
//        sourceDestination.put("Detroit, MI", "Nashville, TN");
//        int i = 0;
//        double drivingHoursPerDay = 8;
//        double milesPerHour = 50;
//        for (String source : sourceDestination.keySet()) {
//            i++;
//            System.out.print(i + ".");
//            orderProcessingService.printShortestPathBetween(source, sourceDestination.get(source), drivingHoursPerDay, milesPerHour);
//        }

        // Check
       // System.out.println(OrderService.getOrderServiceInstance());

//        System.out.println(OrderService.getOrderServiceInstance().getOrderById("TO-002"));
//        System.out.println(OrderService.getOrderServiceInstance().getOrderList());
//       ArrayList<String> fList = (ArrayList) FacilityService.getFacilityServiceInstance().getFacilityList();
//        for(String facilityId: fList) {
//            if(FacilityService.getFacilityServiceInstance().getFacilityById(facilityId).isItemAvailable("ABC123")) {
//                System.out.println(facilityId + " has item" );
//            }
//        }

        orderProcessingService.processAllOrders();

        orderProcessingService.printAllProcessingSolutions();

        orderProcessingService.generateFacilityStatusOutputForAllFacilities();





    }
}
