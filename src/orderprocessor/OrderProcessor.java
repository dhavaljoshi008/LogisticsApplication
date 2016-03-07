package orderprocessor;

/**
 * OrderProcessor.java
 * LogisticsApplication
 */
public interface OrderProcessor {
    void processOrder(String orderId);
    public String getProcessingSolutionForOrder(String orderId);
}
