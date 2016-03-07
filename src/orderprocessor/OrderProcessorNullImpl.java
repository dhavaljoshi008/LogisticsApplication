package orderprocessor;

/**
 * OrderProcessorNullImpl.java
 * LogisticsApplication
 */
public class OrderProcessorNullImpl implements OrderProcessor {
    @Override
    public void processOrder(String orderId) {

    }

    @Override
    public String getProcessingSolutionForOrder(String orderId) {
        return null;
    }
}
