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
    public int[] getSchedule() {return new int[20];}
}
