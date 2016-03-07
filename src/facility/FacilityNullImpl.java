package facility;

import java.util.HashMap;
import java.util.Map;

/**
 * FacilityNullImpl.java
 * LogisticsApplication
 */
public class FacilityNullImpl implements Facility {
    @Override
    public boolean addInventoryItem(String itemId, int quantity) {
        return false;
    }

    @Override
    public String generateFacilityStatusOutput() {
        return "";
    }

    @Override
    public Map<String, Double> getTransportationLinksWithDistance() {
        return new HashMap<>();
    }

    @Override
    public boolean isItemAvailable(String itemId) {
        return false;
    }

    @Override
    public int getAvailableQuantity(String itemId) {
        return -1;
    }

    @Override
    public int getFirstOpenDay() {
        return -1;
    }

    @Override
    public int getProcessingRate() {
        return -1;
    }

    @Override
    public int getScheduleForDay(int day) {
        return -1;
    }

    @Override
    public int processItem(int orderSubmissionDay, String itemId, int requiredQuantity) {
        return -1;
    }

    @Override
    public int getProcessingEndDay(int orderSubmissionDay, String itemId, int requiredQuantity) {
        return -1;
    }

    @Override
    public double getFacilityProcessingCost(int itemQuantity) {
        return -1;
    }
}
