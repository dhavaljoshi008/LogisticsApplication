package facility;

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
        return null;
    }
}
