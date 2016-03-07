package facility;


import exceptions.InvalidArgumentException;
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

    FacilityBasicImpl(String facilityId, int processingCapacityPerDay, double dailyProcessingCost, Map<String, Integer> inventory, Map<String, Double> transportationLinks) throws InvalidArgumentException {
//        this.facilityId = facilityId;
//        this.processingCapacityPerDay = processingCapacityPerDay;
//        this.dailyProcessingCost = dailyProcessingCost;
//        this.inventory = new Inventory(inventory);
//        this.transportationLinksWithDistance =  sortByValues((HashMap) transportationLinks);
//        transportationLinksWithDays = new HashMap<>();

        //this.facilityId = facilityId;
        setFacilityId(facilityId);
        //this.processingCapacityPerDay = processingCapacityPerDay;
        setProcessingCapacityPerDay(processingCapacityPerDay);
        //this.dailyProcessingCost = dailyProcessingCost;
        setDailyProcessingCost(dailyProcessingCost);
        //this.inventory = new Inventory(inventory);
        setInventory(inventory);
        //this.transportationLinksWithDistance =  sortByValues((HashMap) transportationLinks);
        setTransportationLinksWithDistance(transportationLinks);
        transportationLinksWithDays = new HashMap<>();
        //this.schedule = ScheduleFactory.build("basic");
        setSchedule("basic");
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

    private void setFacilityId(String facId) throws InvalidArgumentException {
        if(facId == null && facId.isEmpty()) {
            throw new InvalidArgumentException("Facility " + facId + " is null or empty");
        }
        this.facilityId = facId;
    }

    private void setProcessingCapacityPerDay(int procCapPerDay) throws InvalidArgumentException {
        if(procCapPerDay < 0 || procCapPerDay > 25){
            throw new InvalidArgumentException("Invalid Processing Capacity Per Day: " + procCapPerDay + " for facility " + facilityId);
        }
        this.processingCapacityPerDay = procCapPerDay;
    }

    private void setDailyProcessingCost(double dailyProcCost) throws InvalidArgumentException {
        if(dailyProcCost < 0.0){
            throw new InvalidArgumentException("Invalid Processing Cost Per Day: " + dailyProcCost + " for facility " + facilityId);
        }
        this.dailyProcessingCost = dailyProcCost;
    }

    private void setInventory(Map invent) throws InvalidArgumentException {
        if(invent == null) {
            throw new InvalidArgumentException("Null passed to 'setInventory(Map)'");
        }
        this.inventory = new Inventory(invent);
    }

    private void setTransportationLinksWithDistance(Map transportLinksWithDistance) throws InvalidArgumentException {
        if(transportLinksWithDistance == null) {
            throw new InvalidArgumentException("Null passed to 'setTransportationLinksWithDistance(Map)'");
        }
        this.transportationLinksWithDistance = sortByValues((HashMap) transportLinksWithDistance);
    }

    private void setSchedule(String type) throws InvalidArgumentException {
        if(type == "basic" || type == "null" || type == "NULL") {
            this.schedule = ScheduleFactory.build(type);
        } else {
            throw new InvalidArgumentException("Incorrect type passed to 'setSchedule(String)'");
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
    public boolean isItemAvailable(String itemId) {
        return inventory.isItemAvailable(itemId);
    }

    @Override
    public int getAvailableQuantity(String itemId) {
        return inventory.getAvailableQuantity(itemId);
    }

    @Override
    public int getFirstOpenDay() {
       return schedule.getFirstOpenDay();
    }

    @Override
    public int getProcessingRate() {
        return processingCapacityPerDay;
    }

    @Override
    public int getScheduleForDay(int day) {
        return schedule.getScheduleForDay(day);
    }

    @Override
    public int processItem(int orderSubmissionDay, String itemId, int requiredQuantity) {
        int processedQuantity = inventory.processItem(itemId, requiredQuantity);
        schedule.bookSchedule(orderSubmissionDay, processedQuantity);
        return processedQuantity;
    }

    @Override
    public int getProcessingEndDay(int orderSubmissionDay, String itemId, int requiredQuantity) {
       int processableQuantity = inventory.getProcessableQuantity(itemId, requiredQuantity);
       return schedule.getProcessingEndDay(orderSubmissionDay,processableQuantity);
    }

    @Override
    public double getFacilityProcessingCost(int itemQuantity) {
        double totalProcessingDaysRequired = itemQuantity/(double) processingCapacityPerDay;
        return dailyProcessingCost * totalProcessingDaysRequired;
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
       // facilityStatusOutput.append("Depleted (Used-up) Inventory: ");
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
