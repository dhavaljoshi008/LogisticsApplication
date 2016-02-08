package orderprocessor;


import item.Item;
import item.ItemService;

import java.util.HashMap;

/**
 * orderprocessor.java
 * LogisticsApplication
 */
public class OrderProcessor {
    public static void main(String[] args) {
       ItemService itemService = new ItemService("XML");
       String source = "items.xml";
       itemService.loadItemsFromSource(source);
       Item item1 = itemService.getItem("CT1928");
       System.out.println(item1);
       Item item2 = itemService.getItem("XYZ144");
       System.out.println(item2);
    }
}
