package order;

import java.util.HashMap;
import java.util.Map;

/**
 * OrderNullLoaderImpl.java
 * LogisticsApplication
 */
public class OrderNullLoaderImpl implements OrderLoader {
    @Override
    public Map<String, Order> loadOrders(String source) {
        //System.out.println("******** Inside OrderNullLoaderImpl loadOrders(String source) method! ********");
        return new HashMap<>();
    }
}
