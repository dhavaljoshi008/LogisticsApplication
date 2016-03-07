package facility.inventory;

import item.ItemService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inventory.java
 * LogisticsApplication
 */
public class Inventory {
    private Map<String, Integer> inventory;
    private List<String> depletedItemList;

    public Inventory(Map<String, Integer> inventory) {
        if (inventory != null) {
            this.inventory = inventory;
        }
        depletedItemList = new ArrayList<>();
    }

    public boolean addInventoryItem(String itemId, int quantity) {
        // Initialize the inventory when you call this method for the first time.
        if (inventory == null) {
            inventory = new HashMap<>();
        }
        // Check if the item is a valid item and quantity > 0
        if (ItemService.getItemServiceInstance().isValidItem(itemId) && quantity > 0) {
            inventory.put(itemId, quantity);
            return true;
        } else {
            System.out.println("Error adding Item_ID: " + itemId + " to the inventory!");
            return false;
        }

    }

    public String toString() {
        return inventory.toString();
    }

    public String generateInventoryStatusOutput() {
        StringBuilder inventoryStatusOutput = new StringBuilder();
        for (String item : inventory.keySet()) {
            inventoryStatusOutput.append(String.format("%10s", item) + "\t\t" + inventory.get(item) + "\n");
        }
        inventoryStatusOutput.append("Depleted (Used-up) Inventory: " + getDepletedItems());
        return inventoryStatusOutput.toString();
    }

    public boolean isItemAvailable(String itemId) {
        if (inventory.containsKey(itemId)) {
            if (inventory.get(itemId) > 0) {
                return true;
            }
        }
        return false;
    }

    public int getAvailableQuantity(String itemId) {
        if (itemId != null && isItemAvailable(itemId)) {
            return inventory.get(itemId);
        }
        return -1;
    }

    public int processItem(String itemId, int requiredQuantity) {
        if (itemId != null && isItemAvailable(itemId) && requiredQuantity > 0 && !isDepletedItem(itemId)) {
            int processedQuantity;
            int availableQuantity = inventory.get(itemId);
            if (requiredQuantity <= availableQuantity) {
                availableQuantity = availableQuantity - requiredQuantity;
                inventory.put(itemId, availableQuantity);
                processedQuantity = requiredQuantity;
            } else {
                processedQuantity = availableQuantity;
                availableQuantity = availableQuantity - availableQuantity;
                inventory.put(itemId, availableQuantity);

            }
            if (isDepletedItem(itemId)) {
                depletedItemList.add(itemId);
            }
            return processedQuantity;
        }
        return -1;
    }

    public boolean isDepletedItem(String item) {
        if (item != null && inventory.containsKey(item)) {
            if (inventory.get(item) <= 0) {
                return true;
            }
        }
        return false;
    }

    private String getDepletedItems() {
        StringBuilder depletedItemListBuilder = new StringBuilder();
        int depletedItemListSize = depletedItemList.size();
        int i = 0;
        for (String depletedItemId : depletedItemList) {
            i++;
            depletedItemListBuilder.append(depletedItemId);
            if (i < depletedItemListSize) {
                depletedItemListBuilder.append(", ");
            }

        }
        return depletedItemListBuilder.toString();
    }

    public int getProcessableQuantity(String itemId, int requiredQuantity) {
        if (itemId != null && isItemAvailable(itemId) && requiredQuantity > 0 && !isDepletedItem(itemId)) {
            int processableQuantity;
            int availableQuantity = inventory.get(itemId);
            if (requiredQuantity <= availableQuantity) {
                availableQuantity = availableQuantity - requiredQuantity;
                processableQuantity = requiredQuantity;
            } else {
                processableQuantity = availableQuantity;
                availableQuantity = availableQuantity - availableQuantity;
            }
            //System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            //System.out.println("Processable Quantity = " + processableQuantity);
            return processableQuantity;
        }
        return -1;
    }
}
