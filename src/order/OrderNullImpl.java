package order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderNullImpl.java
 * LogisticsApplication
 */
public class OrderNullImpl implements Order {

    @Override
    public String getOrderId() {
        return null;
    }

    @Override
    public Map<String, Integer> getOrderItems() {
        return new HashMap<>();
    }

    @Override
    public String getOrderDestination() {
        return null;
    }

    @Override
    public int getOrderTime() {
        return -1;
    }

    @Override
    public List<String> getOrderItemList() {
        return new ArrayList<>();
    }
}
