package facility;

/**
 * FacilityNetworkLoaderFactory.java
 * LogisticsApplication
 */
public class FacilityNetworkLoaderFactory {
    public static FacilityNetworkLoader build(String type) {
        switch (type) {
            case "xml":
            case "XML":
                return new FacilityNetworkXmlLoaderImpl();
            case "null":
            case "NULL":
                return new FacilityNetworkXmlLoaderImpl();
            default:
                System.out.println("Usage for setting source as a XML file:");
                System.out.println("FacilityService.getFacilityServiceInstance().changeFacilityLoaderSourceType(\"xml\");");
                System.out.println("or");
                System.out.println("FacilityService.getFacilityServiceInstance().changeFacilityLoaderSourceType(\"XML\");");
                System.out.println();
                System.out.println("Default: Null Object");
                return new FacilityNetworkNullLoaderImpl();
        }
    }
}
