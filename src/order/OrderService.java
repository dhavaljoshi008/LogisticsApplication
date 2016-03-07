package order;

import orderprocessor.OrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * order.java
 * LogisticsApplication
 */
final public class OrderService {
    private static OrderService orderServiceInstance;
    private OrderLoader orderLoaderDelegate;
    private Map<String, Order> orderMap;
    private OrderService(String type) {
        orderLoaderDelegate = OrderLoaderFactory.build(type);
    }

    public static OrderService getOrderServiceInstance() {
        if(orderServiceInstance == null) {
            // Initializing orderServiceInstance to source as a XML file.
            orderServiceInstance = new OrderService("XML");
        }
        return  orderServiceInstance;
    }

    private Map<String, Order> loadOrders(String source) {
        return orderLoaderDelegate.loadOrders(source);
    }

    private boolean isOrderMapLoaded() {
       return orderMap != null;
    }

    public void changeOrderLoaderSourceType(String type) {
        orderLoaderDelegate = OrderLoaderFactory.build(type);
    }

    public boolean loadOrdersFromSource(String source) {
        orderMap = loadOrders(source);
        if(isOrderMapLoaded() && !orderMap.isEmpty()) {
            System.out.println("Orders loaded successfully!");
            return true;
        }
        System.out.println("Failed to load orders!");
        return false;
    }

    public List<String> getOrderList() {
        List<String> orderList = new ArrayList<>();
        for(String orderId: orderMap.keySet()) {
            orderList.add(orderId);
        }
        return orderList;
    }

    public List<String> getOrderListSortedByTime() {
        List<Order> orderObjList = new ArrayList<>();
        List<String> orderList = new ArrayList<>();
        for(String orderId: orderMap.keySet()) {
            orderObjList.add(orderMap.get(orderId));
        }
        Collections.sort(orderObjList, new OrderComparator());
        for(Order order: orderObjList) {
            orderList.add(order.getOrderId());
        }
        return orderList;
     }

    public Order getOrderById(String orderId) {
        if(isOrderMapLoaded() && orderMap.containsKey(orderId)) {
            return orderMap.get(orderId);
        }
        return null;
    }

    public String toString() {
        StringBuilder orderServiceBuilder = new StringBuilder();
        if(isOrderMapLoaded()) {
            orderServiceBuilder.append("\n");
            for (String orderId : orderMap.keySet()) {
                orderServiceBuilder.append(orderMap.get(orderId));
                orderServiceBuilder.append("\n");
            }
        }
        return orderServiceBuilder.toString();
    }

}
