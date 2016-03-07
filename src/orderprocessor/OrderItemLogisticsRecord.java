package orderprocessor;

import item.ItemService;
import order.OrderService;

/**
 * OrderItemLogisticsRecord.java
 * LogisticsApplication
 */
public class OrderItemLogisticsRecord {
    private String orderId;
    private String itemId;
    private int totalProcessedQuantity;
    private int numberOfSources;
    private int firstDayOfDeliveryForThisItem;
    private int lastDayOfDeliveryForThisItem;
    private double orderItemCost;
    private int backOrderQuantity;
    private double totalFacilityProcessingCostForItem;
    private double totalTransportationCostForItem;


    public OrderItemLogisticsRecord(String orderId, String itemId, int totalProcessedQuantity, int numberOfSources, int firstDayOfDeliveryForThisItem, int lastDayOfDeliveryForThisItem, int backOrderQuantity, double totalFacilityProcessingCostForItem, double totalTransportationCostForItem) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.totalProcessedQuantity = totalProcessedQuantity;
        this.numberOfSources = numberOfSources;
        this.firstDayOfDeliveryForThisItem = firstDayOfDeliveryForThisItem;
        this.lastDayOfDeliveryForThisItem = lastDayOfDeliveryForThisItem;
        this.orderItemCost = totalProcessedQuantity * ItemService.getItemServiceInstance().getItem(this.itemId).getItemPrice();
        this.backOrderQuantity = backOrderQuantity;
        this.totalFacilityProcessingCostForItem = totalFacilityProcessingCostForItem;
        this.totalTransportationCostForItem = totalTransportationCostForItem;
    }


    public int getTotalProcessedQuantity() {
        return totalProcessedQuantity;
    }

    public int getNumberOfSources() {
        return numberOfSources;
    }

    public int getFirstDayOfDeliveryForThisItem() {
        return firstDayOfDeliveryForThisItem;
    }

    public int getLastDayOfDeliveryForThisItem() {
        return lastDayOfDeliveryForThisItem;
    }

    public double getOrderItemCost() {
        return orderItemCost;
    }

    public int getBackOrderQuantity() {
        return backOrderQuantity;
    }

    public double getTotalFacilityProcessingCostForItem() {
        return totalFacilityProcessingCostForItem;
    }

    public double getTotalTransportationCostForItem() {
        return totalTransportationCostForItem;
    }
    public String toString() {
        StringBuilder logisticsRecordBuilder = new StringBuilder();
        logisticsRecordBuilder.append(OrderService.getOrderServiceInstance().getOrderById(orderId));
        return logisticsRecordBuilder.toString();
    }
}
