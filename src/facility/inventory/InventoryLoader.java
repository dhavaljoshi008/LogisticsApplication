package facility.inventory;

import java.util.Map;

/**
 * InventoryLoader.java
 * LogisticsApplication
 */
public interface InventoryLoader {
    Map<String, Map<String, Integer>> loadInventory(String source);
}
