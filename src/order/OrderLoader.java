package order;

import java.util.Map;

/**
 * OrderLoader.java
 * LogisticsApplication
 */
public interface OrderLoader {
    Map<String, Order> loadOrders(String source);
}
