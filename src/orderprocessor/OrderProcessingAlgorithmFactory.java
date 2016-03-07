package orderprocessor;

/**
 * OrderProcessingAlgorithmFactory.java
 * LogisticsApplication
 */
public class OrderProcessingAlgorithmFactory {
    public static OrderProcessor build(String type) {
        switch(type) {
            case "time":
                return new OrderProcessorTimePriorityImpl();
            case "null":
            case "NULL":
                return new OrderProcessorNullImpl();
            default:
                return new OrderProcessorNullImpl();
        }
    }
}
