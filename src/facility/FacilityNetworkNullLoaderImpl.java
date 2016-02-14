package facility;

import java.util.HashMap;
import java.util.Map;

/**
 * FacilityNetworkNullLoaderImpl.java
 * LogisticsApplication
 */
public class FacilityNetworkNullLoaderImpl implements FacilityNetworkLoader {
    @Override
    public Map<String, Facility> loadFacilityNetwork(String source) {
        //System.out.println("******** Inside FacilityNetworkNullLoaderImpl loadFacilityNetwork(String source) method! ********");
        return new HashMap<>();
    }
}
