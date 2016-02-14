package facility.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * InventoryNullLoaderImpl.java
 * LogisticsApplication
 */
public class InventoryNullLoaderImpl implements InventoryLoader {
    @Override
    public Map<String, Map<String, Integer>> loadInventory(String source) {
        // System.out.println("******** Inside InventoryNullLoaderImpl loadInventory(String source) method! ********");
        return new HashMap<>();
    }
}
