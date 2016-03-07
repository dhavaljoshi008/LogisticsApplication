package order;

import exceptions.InvalidArgumentException;

import java.util.Map;

/**
 * OrderFactory.java
 * LogisticsApplication
 */
public class OrderFactory {
    public static Order build(String type, String orderId, int orderTime, String destination, Map<String, Integer> orderItems) throws InvalidArgumentException {
        switch (type) {
            case "basic":
                return new OrderBasicImpl(orderId, orderTime, destination, orderItems);
            case "null":
            case "NULL" :
                return new OrderNullImpl();
            default:
                return new OrderNullImpl();
        }
    }
}
