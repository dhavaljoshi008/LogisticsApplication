package order;

/**
 * OrderLoaderFactory.java
 * LogisticsApplication
 */
public class OrderLoaderFactory {
    public static OrderLoader build(String type) {
        switch (type) {
            case "xml":
            case "XML":
                return new OrderXmlLoaderImpl();
            case "null":
            case "NULL":
                return new OrderNullLoaderImpl();
            default:
                System.out.println("Usage for setting source as a XML file:");
                System.out.println("OrderService.getOrderServiceInstance().changeOrderLoaderSourceType(\"xml\");");
                System.out.println("or");
                System.out.println("OrderService.getOrderServiceInstance().changeOrderLoaderSourceType(\"XML\");");
                System.out.println();
                System.out.println("Default: Null Object");
                return new OrderNullLoaderImpl();
        }
    }
}
