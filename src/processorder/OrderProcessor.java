package processorder;


import item.Item;
import item.ItemService;

import java.util.HashMap;

/**
 * processorder.java
 * LogisticsApplication
 */
public class OrderProcessor {
    public static void main(String[] args) {
       ItemService itemService = new ItemService("XML");
       String source = "items.xml";
       HashMap<String, Item> itemHashMap = (HashMap) itemService.loadItems(source);
       for(String itemId: itemHashMap.keySet()) {
           System.out.println(itemHashMap.get(itemId));
       }
    }
}
