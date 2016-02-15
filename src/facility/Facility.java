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
}
