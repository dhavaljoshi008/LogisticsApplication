package orderprocessor;

import facility.Facility;
import facility.FacilityService;
import order.Order;
import order.OrderService;
import utilities.ShortestPathService;

import java.util.*;

/**
 * OrderProcessorTimePriorityImpl.java
 * LogisticsApplication
 */
public class OrderProcessorTimePriorityImpl implements OrderProcessor {
    // List of facilities which have the order item.
    private List<String> potentialFacilityList;
    private Map<String, Integer> orderItems;
    private int orderSubmissionDay;
    private String orderDestination;
    double drivingHoursPerDay = 8;
    double milesPerHour = 50;
    private double travelTime;
    private List<FacilityRecord> facilityRecordList;

    private int firstDay;
    private int lastDay;

    private Map<String, OrderItemLogisticsRecord> orderItemLogisticsRecordMap;

    private Map<String, Map<String, List<FacilityRecord>>> orderItemFacilityRecordMap;


    public OrderProcessorTimePriorityImpl() {
        facilityRecordList = new ArrayList<>();
        orderItemLogisticsRecordMap = new HashMap<>();
        orderItemFacilityRecordMap = new HashMap<>();
    }

    @Override
    public void processOrder(String orderId) {
        Order order = OrderService.getOrderServiceInstance().getOrderById(orderId);
        orderItems = order.getOrderItems();
        orderSubmissionDay = order.getOrderTime();
        //orderSubmissionDay = 100;
        orderDestination = order.getOrderDestination();

        Map<String, Integer> totalAvailableItemQuantityMap = new HashMap<>();

        int totalAvailableQuantityOfItem = 0;

        //System.out.println(orderItems);
        FacilityService facilityService = FacilityService.getFacilityServiceInstance();
        // ItemID and FacilityRecord map.
        Map<String, List<FacilityRecord>> itemFacilityRecordMap = new HashMap<>();
        //int i = 0;
        for (String itemId : orderItems.keySet()) {
            //i++;
            potentialFacilityList = facilityService.getListOfFacilitiesWithItem(itemId);
            //System.out.println("Facilities with Item_ID: " + itemId + " = " + potentialFacilityList);
            for(String facility: potentialFacilityList) {
                    totalAvailableQuantityOfItem += FacilityService.getFacilityServiceInstance().getFacilityById(facility).getAvailableQuantity(itemId);
            }
            totalAvailableItemQuantityMap.put(itemId, totalAvailableQuantityOfItem);


            for (String facilityId : potentialFacilityList) {
                travelTime = ShortestPathService.getShortestPathServiceInstance().getShortestPathDaysBetween(facilityId, orderDestination, drivingHoursPerDay, milesPerHour);
                Facility facility = facilityService.getFacilityById(facilityId);
                int firstOpenDay = facility.getFirstOpenDay();
                //int firstOpenDaySchedule = facility.getScheduleForDay(firstOpenDay);
                //int processingRate = facility.getProcessingRate();
                int availableQuantity = facility.getAvailableQuantity(itemId);
                int requiredQuantity = orderItems.get(itemId);
                int processingEndDay = facility.getProcessingEndDay(orderSubmissionDay, itemId, requiredQuantity);

                int itemQuantityThatCanBeProcessed = getItemQuantityThatCanBeProcessed(availableQuantity, requiredQuantity);
                //System.out.println("*******************************************");
                //System.out.println("Source: " + facilityId + " Destination: " + orderDestination);
                //System.out.println("FOD: " + firstOpenDay + " FODSchedule: " + firstOpenDaySchedule + " PR: " + processingRate + " AQ: " + availableQuantity + " RQ: " + requiredQuantity + " PED: " + processingEndDay);

                FacilityRecord facilityRecord = new FacilityRecord(facilityId, orderId, itemId, orderItems.get(itemId), itemQuantityThatCanBeProcessed, firstOpenDay, processingEndDay, orderDestination, travelTime);
                //System.out.println("Arrival Day: " + facilityRecord.getArrivalDay());
                facilityRecordList.add(facilityRecord);
            }

            // Sort facility records based on arrival days.
            Collections.sort(facilityRecordList, new FacilityRecordComparator());
            //System.out.println("Item No. " + i);
            int requiredQuantity = orderItems.get(itemId);
            int remainingQuantity = requiredQuantity;
            int totalProcessedQuantity = 0;
            List<FacilityRecord> logisticsFacilityRecordList = new ArrayList<>();
            for (FacilityRecord facilityRecord : facilityRecordList) {

                int currentFacilityProcessedQuantity;

                if (totalProcessedQuantity != Math.min(requiredQuantity, totalAvailableItemQuantityMap.get(itemId))) {
                    //System.out.println("#########################################################################################");
                    //System.out.println("Remaining Before --> " + remainingQuantity);
                    currentFacilityProcessedQuantity = facilityService.getFacilityById(facilityRecord.getFacilityId()).processItem(orderSubmissionDay, itemId, remainingQuantity);
                    facilityRecord.setItemQuantityProcessed(currentFacilityProcessedQuantity);
                    double facilityProcessingCost = facilityService.getFacilityById(facilityRecord.getFacilityId()).getFacilityProcessingCost(currentFacilityProcessedQuantity);
                    facilityRecord.setFacilityProcessingCost(facilityProcessingCost);

                    logisticsFacilityRecordList.add(facilityRecord);

                    totalProcessedQuantity = totalProcessedQuantity + currentFacilityProcessedQuantity;
                    remainingQuantity -= currentFacilityProcessedQuantity;
                    orderItems.put(itemId, remainingQuantity);
                    //System.out.println("FRID: " + facilityRecord.getFacilityId() + " AD: " + facilityRecord.getArrivalDay() + " RemainingQ: " + remainingQuantity + " PQ: " + currentFacilityProcessedQuantity);

                    //System.out.println("Total processed quantity: " + totalProcessedQuantity);
                    // System.out.println("#########################################################################################");


                }


            }

//            if(requiredQuantity > totalProcessedQuantity) {
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
//                System.out.println( "Item ID = " + itemId + " Quantity = " + (requiredQuantity - totalProcessedQuantity) + " Back-Ordered!");
//            }


            // printFacilityRecords();
            // Clear previous item's facility record.
            facilityRecordList.clear();

            int numberOfSources = logisticsFacilityRecordList.size();
            firstDay = logisticsFacilityRecordList.get(0).getArrivalDay();
            lastDay = logisticsFacilityRecordList.get(numberOfSources - 1).getArrivalDay();

            int totalFacilityProcessingCostForItem = 0;
            int totalFacilityTransportationCostForItem = 0;
            for(FacilityRecord facilityRecord: logisticsFacilityRecordList) {
                //System.out.println("Facility = " +facilityRecord.getFacilityId() + " Processing Cost For Item = " + facilityRecord.getFacilityProcessingCost());
                totalFacilityProcessingCostForItem += facilityRecord.getFacilityProcessingCost();

                //System.out.println("Travel Time = " +facilityRecord.getTravelTime() + " Transportation Cost For Item = " + facilityRecord.getTransportationCost());
                totalFacilityTransportationCostForItem += facilityRecord.getTransportationCost();
            }
            //System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            //System.out.println("Total Facility Processing Cost For Item ID = " +itemId + " = " + totalFacilityProcessingCostForItem);
            //System.out.println("Total Facility Transportation Cost For Item ID = " +itemId + " = " + totalFacilityTransportationCostForItem);

            // Generate OrderItemLogisticsRecord for each order item.
            OrderItemLogisticsRecord orderItemLogisticsRecord = new OrderItemLogisticsRecord(orderId, itemId, totalProcessedQuantity, numberOfSources, firstDay, lastDay, (requiredQuantity - totalProcessedQuantity), totalFacilityProcessingCostForItem, totalFacilityTransportationCostForItem);
            orderItemLogisticsRecordMap.put(itemId, orderItemLogisticsRecord);
            itemFacilityRecordMap.put(itemId, logisticsFacilityRecordList);
        }
        // Clear previous order's items.
        orderItems.clear();

        orderItemFacilityRecordMap.put(orderId, itemFacilityRecordMap);

//        for(String item: totalAvailableItemQuantityMap.keySet()) {
//            System.out.println(".....................................................");
//            System.out.println("Item ID = " + item + " Total Available Quantity = " + totalAvailableItemQuantityMap.get(item));
//        }
    }


    private int getItemQuantityThatCanBeProcessed(int availableQuantity, int requiredQuantity) {
        if (requiredQuantity <= availableQuantity) {
            return requiredQuantity;
        } else {
            return availableQuantity;
        }
    }

//    public void printFacilityRecords() {
//        for (FacilityRecord facilityRecord : facilityRecordList) {
//            System.out.println(facilityRecord);
//        }
//    }

//    public void printAllOrderItemsLogisticRecords() {
//        for(String orderId: orderItemFacilityRecordMap.keySet()) {
//            for(String itemId: orderItemFacilityRecordMap.get(orderId).keySet()) {
//                //System.out.println("Number of sources = " + orderItemFacilityRecordMap.get(orderId).get(itemId).size());
//                for(FacilityRecord facilityRecord: orderItemFacilityRecordMap.get(orderId).get(itemId)) {
//                    System.out.println(facilityRecord.getLogisticsDetails());
//                }
//            }
//        }
//    }

    public String getOrderItemProcessingSolution(String itemId) {
        OrderItemLogisticsRecord orderItemLogisticsRecord = orderItemLogisticsRecordMap.get(itemId);
        StringBuilder orderItemProcessingSolutionBuilder = new StringBuilder();
        orderItemProcessingSolutionBuilder.append("\n");
        orderItemProcessingSolutionBuilder.append(String.format("%10s",itemId)  + String.format("%10s", orderItemLogisticsRecord.getTotalProcessedQuantity())  + String.format("%10s", "$"+orderItemLogisticsRecord.getOrderItemCost()) + String.format("%15s", orderItemLogisticsRecord.getNumberOfSources()) + String.format("%10s", orderItemLogisticsRecord.getFirstDayOfDeliveryForThisItem()) + String.format("%10s", orderItemLogisticsRecord.getLastDayOfDeliveryForThisItem()));
        int backOrderQuantity = orderItemLogisticsRecord.getBackOrderQuantity();
        if(backOrderQuantity > 0) {
            orderItemProcessingSolutionBuilder.append(" *Back-Ordered Quantity:  " + backOrderQuantity);
        }
        orderItemProcessingSolutionBuilder.append("\n");
        return orderItemProcessingSolutionBuilder.toString();
    }


    public String getProcessingSolutionForOrder(String orderId) {
        List<Integer> firstDeliveryDays = new ArrayList<>();
        List<Integer> lastDeliveryDays = new ArrayList<>();
        List<String> orderItems = OrderService.getOrderServiceInstance().getOrderById(orderId).getOrderItemList();
        double totalCost = 0;
        for(String itemId: orderItems){
            OrderItemLogisticsRecord orderItemLogisticsRecord = orderItemLogisticsRecordMap.get(itemId);
            firstDeliveryDays.add(orderItemLogisticsRecord.getFirstDayOfDeliveryForThisItem());
            lastDeliveryDays.add(orderItemLogisticsRecord.getLastDayOfDeliveryForThisItem());
            totalCost += orderItemLogisticsRecord.getOrderItemCost();
            totalCost += orderItemLogisticsRecord.getTotalFacilityProcessingCostForItem();
            totalCost += orderItemLogisticsRecord.getTotalTransportationCostForItem();
        }

        Collections.sort(firstDeliveryDays);
        Collections.sort(lastDeliveryDays);
        int firstDayOfDeliveryForThisOrder = firstDeliveryDays.get(0);
        int lastDayOfDeliveryForThisOrder = lastDeliveryDays.get(lastDeliveryDays.size() - 1);
        StringBuilder processingSolutionBuilder =  new StringBuilder();
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append(OrderService.getOrderServiceInstance().getOrderById(orderId));
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append("Processing Solution:");
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append("Order Id: " + orderId);
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append("<> Destination: " + OrderService.getOrderServiceInstance().getOrderById(orderId).getOrderDestination());
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append("<> Total Cost: $" + totalCost);
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append("<> 1st Delivery Day: " + firstDayOfDeliveryForThisOrder);
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append("<> Last Delivery Day: " + lastDayOfDeliveryForThisOrder);
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append("Order Items:");
        processingSolutionBuilder.append("\n");
        processingSolutionBuilder.append(String.format("%10s","Item ID") + String.format("%10s","Quantity") + String.format("%10s","Cost") + String.format("%15s","Num. Sources") + String.format("%10s","First Day") + String.format("%10s","Last Day"));
        processingSolutionBuilder.append("\n");
        for(String itemId: orderItems) {
            processingSolutionBuilder.append(getOrderItemProcessingSolution(itemId));
        }


        return processingSolutionBuilder.toString();
    }
}
