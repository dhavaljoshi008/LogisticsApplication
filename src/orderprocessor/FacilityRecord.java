package orderprocessor;


/**
 * FacilityRecord.java
 * LogisticsApplication
 */
public class FacilityRecord {
    private String facilityId;
    private String orderId;
    private String itemId;
    private int requiredItemQuantity;
    private int itemQuantityThatCanBeProcessed;
    private int itemQuantityProcessed;
    private String orderDestination;
    private int processingStartDay;
    private int processingEndDay;
    private double travelTime;
    private int travelStart;
    private int travelEnd;
    private int arrivalDay;
    private double facilityProcessingCost;
    private double transportationCost;
    private static final double dailyTravelCost = 500;


    public FacilityRecord(String facilityId, String orderId, String itemId, int requiredItemQuantity, int itemQuantityThatCanBeProcessed, int processingStartDay,  int processingEndDay, String orderDestination, double travelTime) {
        this.facilityId = facilityId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.requiredItemQuantity = requiredItemQuantity;
        this.itemQuantityThatCanBeProcessed = itemQuantityThatCanBeProcessed;
        this.orderDestination = orderDestination;
        this.processingStartDay = processingStartDay;
        this.processingEndDay = processingEndDay;
        // Round up travel time to a whole day.
        this.travelTime = Math.ceil(travelTime);
        this.transportationCost = this.travelTime * dailyTravelCost;
        if(travelTime != 0) {
            this.travelStart = processingEndDay + 1;
            this.travelEnd = this.travelStart + (int) this.travelTime - 1;
        }
        else {
            this.travelStart = processingEndDay;
            this.travelEnd = this.travelStart + (int) this.travelTime;
        }
        // Use rounded up travel time for computation.
        //System.out.println("FID = " + facilityId + " Destination = " + orderDestination +"Travel time =" + travelTime);
        this.arrivalDay = travelEnd;
    }

    public void setItemQuantityProcessed(int processedQuantity) {
        if(processedQuantity > 0 && processedQuantity <= itemQuantityThatCanBeProcessed) {
            this.itemQuantityProcessed = processedQuantity;
        }
    }

    public void setFacilityProcessingCost(double cost) {
        if(cost > 0) {
            this.facilityProcessingCost = cost;
        }
    }

    public double getFacilityProcessingCost() {
        return facilityProcessingCost;
    }

    public double getTransportationCost() {
        return transportationCost;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public int getItemQuantityProcessed() {
        return itemQuantityProcessed;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public int getProcessingStartDay() {
        return processingStartDay;
    }

    public int getProcessingEndDay() {
        return processingEndDay;
    }

    public int getTravelStartDay() {
        return travelStart;
    }

    public int getTravelEndDay() {
        return travelEnd;
    }

    public int getArrivalDay() {
        return arrivalDay;
    }

    public String getLogisticsDetails() {
        StringBuilder logisticsDetailsBuilder = new StringBuilder();
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("Name: " + facilityId + "(" + itemQuantityProcessed + " of " + requiredItemQuantity + ")");
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("\t Cost: $" +facilityProcessingCost);
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("\t ProcessingStart: Day " + processingStartDay);
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("\t ProcessingEnd: Day " + processingEndDay);
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("\t Travel Start: Day " + travelStart);
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("\t Travel End: Day " + travelEnd);
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("--------------------------------------------");
        logisticsDetailsBuilder.append("\n");
        logisticsDetailsBuilder.append("\t Arrival: Day " + arrivalDay);
        logisticsDetailsBuilder.append("\n");
        return logisticsDetailsBuilder.toString();
    }

    public String toString() {
        StringBuilder facilityRecordBuilder = new StringBuilder();
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Facility_ID: " + facilityId);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Order_ID: " + orderId);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Item_ID: " + itemId);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Item_Quantity: " + requiredItemQuantity);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("ProcessableItemQuantity: " + itemQuantityThatCanBeProcessed);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("ProcessedItemQuantity: " + itemQuantityProcessed);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Destination: " + orderDestination);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Processing End Day: " + processingEndDay);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Travel Time: " + travelTime);
        facilityRecordBuilder.append("\n");
        facilityRecordBuilder.append("Arrival Day: " + arrivalDay);
        facilityRecordBuilder.append("\n");
        return facilityRecordBuilder.toString();
    }

}

