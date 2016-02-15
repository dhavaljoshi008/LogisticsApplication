package facility.inventory;

import java.util.Map;

/**
 * InventoryLoaderService.java
 * LogisticsApplication
 */
final public class InventoryLoaderService {
    private static InventoryLoaderService inventoryLoaderServiceInstance;
    private InventoryLoader inventoryLoaderDelegate;

    private InventoryLoaderService(String type) {
        inventoryLoaderDelegate = InventoryLoaderFactory.build(type);
    }

    public static InventoryLoaderService getInventoryLoaderServiceInstance() {
        if(inventoryLoaderServiceInstance == null) {
            // Initializing inventoryLoaderServiceInstance to source as a XML file.
            inventoryLoaderServiceInstance = new InventoryLoaderService("XML");
        }
        return inventoryLoaderServiceInstance;
    }

    public Map<String, Map<String, Integer>> loadInventoryFromSource(String source) {
       return inventoryLoaderDelegate.loadInventory(source);
    }

    public void changeInventoryLoaderSourceType(String source) {
        inventoryLoaderDelegate = InventoryLoaderFactory.build(source);
    }
}
