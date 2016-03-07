package order;

import exceptions.InvalidArgumentException;
import facility.Facility;
import facility.FacilityService;
import item.ItemService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderBasicImpl.java
 * LogisticsApplication
 */
public class OrderBasicImpl implements Order {
    private String orderId;
    private int orderTime;
    private String destination;
    // Map of item id and quantity
    private Map<String, Integer> orderItems;

    public OrderBasicImpl(String orderId, int orderTime, String destination, Map<String, Integer> orderItems) throws InvalidArgumentException {
//        this.orderId = orderId;
//        this.orderTime = orderTime;
//        this.destination = destination;
//        this.orderItems = new HashMap<>(orderItems);
        setOrderId(orderId);
        setOrderTime(orderTime);
        setDestination(destination);
        setOrderItems(orderItems);
    }

    public void setOrderId(String orderId) throws InvalidArgumentException {
        if(orderId == null || orderId.isEmpty())
            throw new InvalidArgumentException("OrderId " + orderId + " is null or empty");
        this.orderId = orderId;
    }

    public void setOrderTime(int orderTime) throws InvalidArgumentException {
        if(orderTime < 1)
            throw new InvalidArgumentException("OrderTime " + orderTime + " should be greater than 0");
        this.orderTime = orderTime;
    }

    public void setDestination(String destination) throws InvalidArgumentException {
        if(destination == null || destination.isEmpty())
            throw new InvalidArgumentException("Destination " + destination + " is null or empty");
        if(!FacilityService.getFacilityServiceInstance().doesFacilityExists(destination))
            throw new InvalidArgumentException("Destination " + destination + " is not a valid Facility");
        this.destination = destination;
    }

    public void setOrderItems(Map<String, Integer> orderItems) throws InvalidArgumentException {
        if(orderItems == null || orderItems.isEmpty())
            throw new InvalidArgumentException("Map " + orderItems + " in setOrderItems(Map) is either null or empty" );
        for(String itemId: orderItems.keySet()) {
            if(itemId == null || !ItemService.getItemServiceInstance().isValidItem(itemId) || itemId.isEmpty() )
                throw new InvalidArgumentException("Item " + itemId + " is not a valid item");
            if(orderItems.get(itemId) < 1)
                throw new InvalidArgumentException("Quantity " + orderItems.get(itemId) + " is invalid for item " + itemId);
        }
        this.orderItems = orderItems;
    }

    public String toString() {
        StringBuilder orderBuilder = new StringBuilder();
        orderBuilder.append("Order Id: " + orderId);
        orderBuilder.append("\n");
        orderBuilder.append("Order Time: Day " + orderTime);
        orderBuilder.append("\n");
        orderBuilder.append("Destination: " + destination);
        orderBuilder.append("\n");
        orderBuilder.append("List of Order Items:");
        orderBuilder.append("\n");
        for (String itemId : orderItems.keySet()) {
            orderBuilder.append("<> Item ID: " + itemId + "," + " Quantity: " + orderItems.get(itemId));
            orderBuilder.append("\n");
        }
        return orderBuilder.toString();
    }


    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public Map<String, Integer> getOrderItems() {
        // Return a copy of orderItems.
        return new HashMap<>(orderItems);
    }


    @Override
    public String getOrderDestination() {
        return destination;
    }

    @Override
    public int getOrderTime() {
        return orderTime;
    }

    @Override
    public List<String> getOrderItemList() {
        List<String> orderItemList = new ArrayList<>();
        for(String orderItemId: orderItems.keySet()) {
            orderItemList.add(orderItemId);
        }
        return orderItemList;
    }
}
