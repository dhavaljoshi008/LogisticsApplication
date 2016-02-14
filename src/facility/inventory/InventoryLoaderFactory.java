package facility.inventory;

/**
 * InventoryLoaderFactory.java
 * LogisticsApplication
 */
public class InventoryLoaderFactory {
    public static InventoryLoader build(String type) {
        switch (type) {
            case "xml":
            case "XML":
                return new InventoryXmlLoaderImpl();
            case "null":
            case "NULL":
                return new InventoryNullLoaderImpl();
            default:
                System.out.println("Usage for setting source as a XML file:");
                System.out.println("FacilityService.getFacilityServiceInstance().changeInventoryLoaderSourceType(\"xml\");");
                System.out.println("or");
                System.out.println("FacilityService.getFacilityServiceInstance().changeInventoryLoaderSourceType(\"XML\");");
                System.out.println();
                System.out.println("Default: Null Object");
                return new InventoryNullLoaderImpl();
        }
    }
}
