package facility.inventory;

import item.ItemService;

import java.util.HashMap;
import java.util.Map;

/**
 * Inventory.java
 * LogisticsApplication
 */
public class Inventory {
    private Map<String, Integer> inventory;

    public Inventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public boolean addInventoryItem(String itemId, int quantity) {
        // Initialize the inventory when you call this method for the first time.
        if(inventory == null) {
            inventory = new HashMap<>();
        }
        // Check if the item is a valid item and quantity > 0
        if(ItemService.getItemServiceInstance().isValidItem(itemId) && quantity > 0) {
            inventory.put(itemId, quantity);
            return true;
        }
        else {
            System.out.println("Error adding Item_ID: " + itemId + " to the inventory!");
            return false;
        }

    }

    public String toString() {
        return inventory.toString();
    }
    public String generateInventoryStatusOutput() {
        StringBuilder inventoryStatusOutput = new StringBuilder();
        for(String item: inventory.keySet()) {
            inventoryStatusOutput.append(String.format("%10s",item) + "\t\t" + inventory.get(item) + "\n");
        }
        return inventoryStatusOutput.toString();
    }
}
