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
                return new ItemXmlLoadeImpl();
            default:
                System.out.println("Usage for XML file:");
                System.out.println("ItemService itemService = new ItemService(\"xml\");");
                System.out.println("or");
                System.out.println("ItemService itemService = new ItemService(\"XML\");");
                System.out.println();
                System.out.println("Default: Null Object");
                return new ItemNullLoaderImpl();
        }
    }
}
