package item;

import java.util.Map;

/**
 * item.java
 * LogisticsApplication
 */
public class ItemService implements ItemLoader {
    private ItemLoader delegate;

    public ItemService(String type) {
        delegate = ItemLoaderFactory.build(type);
    }

    @Override
    public Map<String, Item> loadItems(String source) {
        return delegate.loadItems(source);
    }
}
