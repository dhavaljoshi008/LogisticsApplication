package order;

import java.util.List;
import java.util.Map;

/**
 * Order.java
 * LogisticsApplication
 */
public interface Order {
    String getOrderId();
    Map<String, Integer> getOrderItems();
    String getOrderDestination();
    int getOrderTime();
    List<String> getOrderItemList();
}
