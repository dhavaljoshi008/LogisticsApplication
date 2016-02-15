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
public class OrderProcessor {
    public static void main(String[] args) {
        ItemService itemService = ItemService.getItemServiceInstance();
        String itemSource = "items.xml";
        itemService.loadItemsFromSource(itemSource);

        FacilityService facilityService = FacilityService.getFacilityServiceInstance();
        String facilitySource = "facilitynetwork.xml";
        String inventorySource = "inventories.xml";
        facilityService.loadFacilityNetworkFromSource(facilitySource);
        facilityService.loadInventoryFromSource(inventorySource);

        ShortestPathService shortestPathService = ShortestPathService.getShortestPathServiceInstance();
        System.out.println();
        facilityService.generateFacilityStatusOutputForAllFacilities();

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
            System.out.println(i + ". " + " " + source + " to" + " " + sourceDestination.get(source));
            System.out.println("\t" + "<> " + shortestPathService.getShortestPathBetween(source, sourceDestination.get(source)));
            System.out.println("\t" + "<> " + shortestPathService.getShortestDistanceBetween(source, sourceDestination.get(source)) + "mi/" + "(" + drivingHoursPerDay + " * " + milesPerHour + ")" + " = " + String.format("%.2f", shortestPathService.getShortestPathDaysBetween(source, sourceDestination.get(source), drivingHoursPerDay, milesPerHour)) + " days");
            System.out.println();
        }
    }
}
