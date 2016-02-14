package facility;

import facility.inventory.InventoryService;

import java.util.HashMap;
import java.util.Map;

/**
 * facility.java
 * LogisticsApplication
 */
final public class FacilityService {
    private static FacilityService facilityServiceInstance;
    private FacilityNetworkLoader facilityNetworkLoaderDelegate;
    private Map<String, Facility> facilityMap;
    private Map<String, Map<String, Integer>> inventoryMap;

    private FacilityService(String type) {
        facilityNetworkLoaderDelegate = FacilityNetworkLoaderFactory.build(type);
    }

    private Map<String, Facility> loadFacilityNetwork(String source) {
        return facilityNetworkLoaderDelegate.loadFacilityNetwork(source);
    }

    private boolean isFacilityMapLoaded() {
        return facilityMap != null;
    }
    private boolean isInventoryMapLoaded() { return  inventoryMap != null;}

    public static FacilityService getFacitlityServiceInstance() {
        if(facilityServiceInstance == null) {
            // Initializing facilityServiceInstance to source as a XML file.
            facilityServiceInstance = new FacilityService("XML");
        }
        return facilityServiceInstance;
    }

    public void changeFacilityLoaderSourceType(String type) {
        facilityNetworkLoaderDelegate = FacilityNetworkLoaderFactory.build(type);
    }

    // Check if any such facility exists.
    public boolean doesFacilityExists(String facilityId) {
        if(isFacilityMapLoaded()) {
            if(facilityMap.containsKey(facilityId)) {
                return true;
            }
        }
        else {
            System.out.println("FacilityMap not initialized!");
        }
        return false;
    }

    // Publicly exposed method for loading facilitynetwork.
    public boolean loadFacilityNetworkFromSource(String source) {
        facilityMap = loadFacilityNetwork(source);
        if(isFacilityMapLoaded() && !facilityMap.isEmpty()) {
            System.out.println("FacilityNetwork loaded successfully!");
            return true;
        }
        System.out.println("Failed to load facilitynetwork!");
        return false;

    }

    public boolean loadInventoryFromSource(String source) {
        inventoryMap = InventoryService.getInventoryServiceInstance().loadInventoryFromSource(source);
        if(isFacilityMapLoaded() && !inventoryMap.isEmpty()) {
            System.out.println("Inventories loaded successfully!");
            populateInventoryForAllFacilities();
            return true;
        }
        else {
            System.out.println("Failed to load inventories!");
        }
        return false;
    }

    public void changeInventoryLoaderSourceType(String source) {
        InventoryService.getInventoryServiceInstance().changeInventoryLoaderSourceType(source);
    }

    // Populating inventories.
    private void populateInventoryForAllFacilities() {
        String facId;
        for(String facilityId: inventoryMap.keySet()) {
            facId = facilityId;
            if (doesFacilityExists(facilityId)) {
                HashMap<String, Integer> inventoryItems = (HashMap) inventoryMap.get(facilityId);
                for (String item : inventoryItems.keySet()) {
                    // Adding inventory item and quantity.
                    facilityMap.get(facilityId).addInventoryItem(item, inventoryItems.get(item));
                }
            } else {
                System.out.println("Unknown Facility_ID: " + facId + "!" + " Cannot load inventory!");
            }
        }
    }


    // Printing status of all the facilities.
    public void generateFacilityStatusOutputForAllFacilities() {
        int i = 0;
        for(String facility: facilityMap.keySet()) {
            i++;
            System.out.println(i + ". " + facilityMap.get(facility).generateFacilityStatusOutput());

        }
    }
}
