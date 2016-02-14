package facility;


import facility.inventory.Inventory;

import java.util.Map;

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
    private Map<String, Double> transportationLinks;
    FacilityBasicImpl(String facilityId, int processingCapacityPerDay, double dailyProcessingCost, Map<String, Integer> inventory, Map<String, Double> transportationLinks) {
        this.facilityId = facilityId;
        this.processingCapacityPerDay = processingCapacityPerDay;
        this.dailyProcessingCost = dailyProcessingCost;
        this.inventory = new Inventory(inventory);
        this.transportationLinks = transportationLinks;
    }

    public boolean addInventoryItem(String itemId, int quantity) {
        return inventory.addInventoryItem(itemId, quantity);
    }

    @Override
    public String generateFacilityStatusOutput() {
        StringBuilder facilityStatusOutput = new StringBuilder();
        facilityStatusOutput.append(facilityId);
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append("********************");
        facilityStatusOutput.append("\n\n");
        facilityStatusOutput.append("Direct Links: " +  transportationLinks);
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append("Active Inventory: ");
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append("Item_ID" + "\t\t" + "Quantity");
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append(this.inventory.generateInventoryStatusOutput());
        facilityStatusOutput.append("Depleted (Used-up) Inventory: ");
        facilityStatusOutput.append("\n");
        facilityStatusOutput.append("Schedule: ");
        facilityStatusOutput.append("\n");
        return facilityStatusOutput.toString();
    }

    public String toString() {
        return "Facility_ID: " + facilityId + " ProcessingCapacityPerDay: " + processingCapacityPerDay + " DailyProcessingCost: " + dailyProcessingCost + " DirectLinks: [" +transportationLinks + "]";
    }

}
