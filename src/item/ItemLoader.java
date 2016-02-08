package item;

import java.util.Map;

/**
 * ItemLoader.java
 * LogisticsApplication
 */
public interface ItemLoader {
     Map<String, Item> loadItems(String source);
}
