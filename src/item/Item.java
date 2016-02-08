package item;

/**
 * Item.java
 * LogisticsApplication
 */
public class Item {
    private String id;
    private double price;

    public Item(String id, double price) {
        this.id = id;
        this.price = price;
    }

    public String getitemId() {
        return id;
    }

    public double getItemPrice() {
        return price;
    }

    public String toString() {
        return "Item_ID: " + id + ", Item_Price: $" + price;
    }
}
