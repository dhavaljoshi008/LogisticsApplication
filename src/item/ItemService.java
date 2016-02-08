package item;

import java.util.Map;

/**
 * item.java
 * LogisticsApplication
 */
public class ItemService {
    private ItemLoader delegate;
    private Map<String, Item> itemMap;
    public ItemService(String type) {
        delegate = ItemLoaderFactory.build(type);
    }

    private Map<String, Item> loadItems(String source) {
        return delegate.loadItems(source);
    }

    // Publicly exposed method for loading items.
    public boolean loadItemsFromSource(String source) {
        itemMap = loadItems(source);
        if(itemMap != null && !itemMap.isEmpty()) {
            System.out.println("Items loaded successfully!");
            return true;
        }
        return false;
    }

    public boolean isItemAvailable(String itemId) {
        if(itemMap != null && itemMap.containsKey(itemId)) {
            System.out.println("Item_ID: " + itemId + " available!");
            return true;
        }
        System.out.println("Item_ID: " + itemId + " not available!");
        return false;
    }

    // Get Item object by Item_ID
    public Item getItem(String itemId) {
        isItemAvailable(itemId);
        return itemMap.get(itemId);
    }
}
