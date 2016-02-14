package facility.inventory;

import java.util.Map;

/**
 * InventoryService.java
 * LogisticsApplication
 */
final public class InventoryService {
    private static InventoryService inventoryServiceInstance;
    private InventoryLoader inventoryLoaderDelegate;

    private InventoryService(String type) {
        inventoryLoaderDelegate = InventoryLoaderFactory.build(type);
    }

    public static InventoryService getInventoryServiceInstance() {
        if(inventoryServiceInstance == null) {
            // Initializing inventoryServiceInstance to source as a XML file.
            inventoryServiceInstance = new InventoryService("XML");
        }
        return  inventoryServiceInstance;
    }

    public Map<String, Map<String, Integer>> loadInventoryFromSource(String source) {
       return inventoryLoaderDelegate.loadInventory(source);
    }

    public void changeInventoryLoaderSourceType(String source) {
        inventoryLoaderDelegate = InventoryLoaderFactory.build(source);
    }
}
