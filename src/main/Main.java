package main;

import orderprocessor.OrderProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Main.java
 * LogisticsApplication
 */
public class Main {
    public static void main(String[] args) {
        OrderProcessor orderProcessor = OrderProcessor.getOrderProcessorInstance();
        orderProcessor.loadItemsFromSource("items.xml");
        orderProcessor.loadFacilityNetworkFromSource("facilityNetwork.xml");
        orderProcessor.loadInventoryFromSource("inventories.xml");
        orderProcessor.generateFacilityStatusOutputForAllFacilities();
        Map<String, String> sourceDestination = new HashMap<>();
        sourceDestination.put("Santa Fe, NM", "Chicago, IL");
        sourceDestination.put("Atlanta, GA", "St. Louis, MO");
        sourceDestination.put("Seattle, WA", "Nashville, TN");
        sourceDestination.put("New York City, NY", "Phoenix, AZ");
        sourceDestination.put("Fargo, ND", "Austin, TX");
        sourceDestination.put("Denver, CO", "Miami, FL");
        sourceDestination.put("Austin, TX", "Norfolk, VA");
        sourceDestination.put("Miami, FL", "Seattle, WA");
        sourceDestination.put("Los Angeles, CA", "Chicago, IL");
        sourceDestination.put("Detroit, MI", "Nashville, TN");
        int i = 0;
        double drivingHoursPerDay = 8;
        double milesPerHour = 50;
        for (String source : sourceDestination.keySet()) {
            i++;
            System.out.print(i + ".");
            orderProcessor.printShortestPathBetween(source, sourceDestination.get(source), drivingHoursPerDay, milesPerHour);
        }
    }
}
