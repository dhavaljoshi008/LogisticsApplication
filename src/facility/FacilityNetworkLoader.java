package facility;

import exceptions.InvalidArgumentException;

import java.util.Map;

/**
 * FacilityNetworkLoader.java
 * LogisticsApplication
 */
public interface FacilityNetworkLoader {
    // Loads facility and its transportation links.
    Map<String, Facility> loadFacilityNetwork(String source) throws InvalidArgumentException;
}
