package item;

/**
 * ItemLoaderFactory.java
 * LogisticsApplication
 */
public class ItemLoaderFactory {
    public static ItemLoader build(String type) {
        switch (type) {
            case "xml":
            case "XML":
                return new ItemXmlLoaderImpl();
            case "null":
            case "NULL":
                return new ItemNullLoaderImpl();
            default:
                System.out.println("Usage for setting source as a XML file:");
                System.out.println("itemService.changeSourceType(\"xml\");");
                System.out.println("or");
                System.out.println("itemService.changeSourceType(\"XML\");");
                System.out.println();
                System.out.println("Default: Null Object");
                return new ItemNullLoaderImpl();
        }
    }
}
