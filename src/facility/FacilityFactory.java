package facility;

import exceptions.InvalidArgumentException;

import java.util.Map;

/**
 * FacilityFactory.java
 * LogisticsApplication
 */
public class FacilityFactory {
    public static Facility build(String type, String facilityId, int processingCapacityPerDay, double dailyProcessingCost, Map<String, Integer> inventory, Map<String, Double> transportationLinks) throws InvalidArgumentException {
        switch(type) {
            case "basic":
                return new FacilityBasicImpl(facilityId, processingCapacityPerDay, dailyProcessingCost, inventory, transportationLinks);
            case "null":
            case "NULL":
                return new FacilityNullImpl();
            default:
                return new FacilityNullImpl();
        }
    }
}
