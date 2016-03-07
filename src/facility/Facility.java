package facility;


import java.util.Map;

/**
 * Facility.java
 * LogisticsApplication
 */
public interface Facility {
    boolean addInventoryItem(String itemId, int quantity);
    String generateFacilityStatusOutput();
    Map<String, Double> getTransportationLinksWithDistance();
    boolean isItemAvailable(String itemId);
    int getAvailableQuantity(String itemId);
    int getFirstOpenDay();
    int getProcessingRate();
    int getScheduleForDay(int day);
    int processItem(int orderSubmissionDay, String itemId, int requiredQuantity);
    int getProcessingEndDay(int orderSubmissionDay, String itemId, int requiredQuantity);
    double getFacilityProcessingCost(int itemQuantity);
}
