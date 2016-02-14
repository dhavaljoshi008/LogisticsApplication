package orderprocessor;


        import facility.FacilityService;
        import item.ItemService;


/**
 * orderprocessor.java
 * LogisticsApplication
 */
public class OrderProcessor {
    public static void main(String[] args) {
        ItemService itemService = ItemService.getItemServiceInstance();
        String itemSource = "items.xml";
        //ItemService.getItemServiceInstance().changeItemLoaderSourceType("");
        itemService.loadItemsFromSource(itemSource);
        FacilityService facilityService = FacilityService.getFacitlityServiceInstance();
        //facilityService.changeFacilityLoaderSourceType("");
        String facilitySource = "facilitynetwork.xml";
        String inventorySource = "inventories.xml";
        facilityService.loadFacilityNetworkFromSource(facilitySource);
        //facilityService.changeInventoryLoaderSourceType("");
        facilityService.loadInventoryFromSource(inventorySource);
        facilityService.generateFacilityStatusOutputForAllFacilities();
    }
}
