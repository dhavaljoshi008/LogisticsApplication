package facility;



/**
 * Facility.java
 * LogisticsApplication
 */
public interface Facility {
    boolean addInventoryItem(String itemId, int quantity);
    String generateFacilityStatusOutput();

}
