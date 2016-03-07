package orderprocessor;

import order.Order;

import java.util.Comparator;

/**
 * OrderComparator.java
 * LogisticsApplication
 */
public class OrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        int orderTime1 = o1.getOrderTime();
        int orderTime2 = o2.getOrderTime();

        if (orderTime1 > orderTime2) {
            return +1;
        } else if (orderTime1 < orderTime2) {
            return -1;
        } else {
            return 0;
        }
    }
}
