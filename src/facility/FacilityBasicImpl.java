package facility;


import facility.inventory.Inventory;
import facility.schedule.Schedule;
import facility.schedule.ScheduleFactory;
import utilities.ShortestPathService;

import java.util.*;

/**
 * FacilityBasicImpl.java
 * LogisticsApplication
 */
public class FacilityBasicImpl implements Facility {
    private String facilityId;
    private int processingCapacityPerDay;
    private double dailyProcessingCost;
    private Inventory inventory;
    // Connected facility and Distance
    private Map<String, Double> transportationLinksWithDistance;
    private Map<String, Double> transportationLinksWithDays;
    private Schedule schedule;

    FacilityBasicImpl(String facilityId, int processingCapacityPerDay, double dailyProcessingCost, Map<String, Integer> inventory, Map<String, Double> transportationLinks) {
        this.facilityId = facilityId;
        this.processingCapacityPerDay = processingCapacityPerDay;
        this.dailyProcessingCost = dailyProcessingCost;
        this.inventory = new Inventory(inventory);
        this.transportationLinksWithDistance =  sortByValues((HashMap) transportationLinks);
        transportationLinksWithDays = new HashMap<>();
        this.schedule = ScheduleFactory.build("basic");
        int numberOfDays = 20;
        boolean setNumDaysStatus = schedule.setNumberOfDays(numberOfDays);
        if(!setNumDaysStatus) {
            System.out.println("Please enter number of days for Facility_ID: " + this.facilityId + " within the range 1 to 30!");
        }
        boolean setProcCapStatus = schedule.setProcessingCapacityPerDay(this.processingCapacityPerDay);
        if(!setProcCapStatus){
            System.out.println("Please enter processing capacity for Facility_ID: " + this.facilityId + " within the range 1 to 25!");
        }

    }

    private static HashMap sortByValues(HashMap map) {
        Set<Map.Entry<String, Double>> set = map.entrySet();
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Double>>() {
            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
            {
                return (o1.getValue()).compareTo(o2.getValue() );
            }
        } );

        // Copying the sorted list in HashMap using LinkedHashMap for order preservation.
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    private void populateTransportationLinksWithDays(Map<String, Double> transportationLinksWithDistanceInMiles, double drivingHoursPerDay, double milesPerHour) {
        for(String facilityLink: transportationLinksWithDistanceInMiles.keySet()) {
            transportationLinksWithDays.put(facilityLink, ShortestPathService.getShortestPathServiceInstance().getShortestPathDaysBetween(facilityId, facilityLink, drivingHoursPerDay, milesPerHour));
        }
    }

    private Map<String, Double> getDirectLinks() {
        // Sorting links by days and returning
        return sortByValues((HashMap) transportationLinksWithDays);
    }

    public boolean addInventoryItem(String itemId, int quantity) {
        return inventory.addInventoryItem(itemId, quantity);
    }

    public Map<String, Double> getTransportationLinksWithDistance() {
        // Creating a copy of transportationLinksWithDistance and returning it.
        Map<String, Double> directLinks = new HashMap<>(transportationLinksWithDistance);

        return  directLinks;
    }

    @Override
    public String generateFacilityStatusOutput() {
        double drivingHoursPerDay = 8;
        double milesPerHour = 50;
        populateTransportationLinksWithDays(transportationLinksWithDistance, drivingHoursPerDay, milesPerHour);


        StringBuilder facilityStatusOutput = new StringBuilder();
        HashMap<String, Double> directLinks = (HashMap) getDirectLinks();
        facilityStatusOutput.append(facilityId);
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append("********************");
        facilityStatusOutput.append("\n\n");
        facilityStatusOutput.append("Direct Links: ");
        int numberOfLinks = directLinks.size();
        int i = 0;
        for(String facilityLink: directLinks.keySet()) {
            facilityStatusOutput.append(facilityLink + " (" + String.format("%.2f",directLinks.get(facilityLink)) + "d)");
            i++;
            if(i < numberOfLinks) {
                facilityStatusOutput.append("; ");
            }
        }
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append("Active Inventory: ");
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append(String.format("%10s","Item_ID") + "\t\t" + "Quantity");
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append(this.inventory.generateInventoryStatusOutput());
        facilityStatusOutput.append("Depleted (Used-up) Inventory: ");
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append("Schedule: ");
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append(schedule.getSchedule());
        facilityStatusOutput.append("\n");
        return facilityStatusOutput.toString();
    }

    public String toString() {
        return "Facility_ID: " + facilityId + " ProcessingCapacityPerDay: " + processingCapacityPerDay + " DailyProcessingCost: " + dailyProcessingCost + " DirectLinks: [" + transportationLinksWithDistance + "]";
    }

}
