package facility;

import facility.inventory.InventoryLoaderService;
import utilities.Edge;
import utilities.Graph;
import utilities.ShortestPathService;
import utilities.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * facility.java
 * LogisticsApplication
 */
final public class FacilityService {
    private static FacilityService facilityServiceInstance;
    private FacilityNetworkLoader facilityNetworkLoaderDelegate;
    private Map<String, Facility> facilityMap;
    private Map<String, Map<String, Integer>> inventoryMap;
    private Graph facilityNetworkGraph;
    private Map<String, Vertex> facilityVertexMap;

    private FacilityService(String type) {
        facilityNetworkLoaderDelegate = FacilityNetworkLoaderFactory.build(type);
    }

    private Map<String, Facility> loadFacilityNetwork(String source) {
        return facilityNetworkLoaderDelegate.loadFacilityNetwork(source);
    }

    public static FacilityService getFacilityServiceInstance() {
        if(facilityServiceInstance == null) {
            // Initializing facilityServiceInstance to source as a XML file.
            facilityServiceInstance = new FacilityService("XML");
        }
        return facilityServiceInstance;
    }


    private boolean isFacilityMapLoaded() {
        return facilityMap != null;
    }

    private boolean isInventoryMapLoaded() { return  inventoryMap != null;}

    // Check if FacilityNetworkGraph has been generated.
    private boolean isFacilityNetworkGraphInitialized() {
        return facilityNetworkGraph != null;
    }

    private Facility getFacilityById(String facilityId) {
        if(doesFacilityExists(facilityId)) {
            return facilityMap.get(facilityId);
        }
        else {
            System.out.println("Facility_ID: " + facilityId + " does not exists!");
        }
        return null;
    }

    private void generateFacilityNetworkGraph(Map<String, Facility> facilityMap) {
        List<Vertex> facilityVertexList = new ArrayList<>();
        List<Edge> facilityEdgeList = new ArrayList<>();
        Map<String, Vertex> vertexMap = new HashMap<>();
        int vertexId = 0;
        int edgeId = 0;
        for(String facility: facilityMap.keySet()) {
            vertexId++;
            Vertex v = new Vertex("" + vertexId, facility);
            facilityVertexList.add(v);
            vertexMap.put(facility, v);
        }
        for(String facilityVertex: vertexMap.keySet()) {
            Map<String, Double> transportationLinks = getFacilityById(facilityVertex).getTransportationLinksWithDistance();
            for(String facilityLink: transportationLinks.keySet()) {
                edgeId++;
                Edge v1V2 = new Edge("" + edgeId, vertexMap.get(facilityVertex), vertexMap.get(facilityLink), transportationLinks.get(facilityLink));
                Edge v2V1 = new Edge("" + edgeId, vertexMap.get(facilityLink), vertexMap.get(facilityVertex),  transportationLinks.get(facilityLink));
                facilityEdgeList.add(v1V2);
                facilityEdgeList.add(v2V1);
            }
        }
        facilityNetworkGraph = new Graph(facilityVertexList, facilityEdgeList);
        ShortestPathService.getShortestPathServiceInstance().initNodesAndEdgesFromGraph(facilityNetworkGraph);

    }

    // Populating inventories.
    private void populateInventoryForAllFacilities() {
        String facId;
        for(String facilityId: inventoryMap.keySet()) {
            facId = facilityId;
            if (doesFacilityExists(facilityId)) {
                HashMap<String, Integer> inventoryItems = (HashMap) inventoryMap.get(facilityId);
                for (String item : inventoryItems.keySet()) {
                    // Adding inventory item and quantity.
                    facilityMap.get(facilityId).addInventoryItem(item, inventoryItems.get(item));
                }
            } else {
                System.out.println("Unknown Facility_ID: " + facId + "!" + " Cannot load inventory!");
            }
        }
    }


    public void changeFacilityLoaderSourceType(String type) {
        facilityNetworkLoaderDelegate = FacilityNetworkLoaderFactory.build(type);
    }

    // Check if any such facility exists.
    public boolean doesFacilityExists(String facilityId) {
        if(isFacilityMapLoaded()) {
            if(facilityMap.containsKey(facilityId)) {
                return true;
            }
        }
        else {
            System.out.println("FacilityMap not initialized!");
        }
        return false;
    }

    // Publicly exposed method for loading facilitynetwork.
    public boolean loadFacilityNetworkFromSource(String source) {
        facilityMap = loadFacilityNetwork(source);
        if(isFacilityMapLoaded() && !facilityMap.isEmpty()) {
            System.out.println("FacilityNetwork loaded successfully!");
            // Generating FacilityNetworkGraph.
            generateFacilityNetworkGraph(facilityMap);
            return true;
        }
        System.out.println("Failed to load facilitynetwork!");
        return false;

    }

    public boolean loadInventoryFromSource(String source) {
        inventoryMap = InventoryLoaderService.getInventoryLoaderServiceInstance().loadInventoryFromSource(source);
        if(isInventoryMapLoaded() && !inventoryMap.isEmpty()) {
            System.out.println("Inventories loaded successfully!");
            populateInventoryForAllFacilities();
            return true;
        }
        else {
            System.out.println("Failed to load inventories!");
        }
        return false;
    }

    public void changeInventoryLoaderSourceType(String source) {
        InventoryLoaderService.getInventoryLoaderServiceInstance().changeInventoryLoaderSourceType(source);
    }

    // Printing status of all the facilities.
    public void generateFacilityStatusOutputForAllFacilities() {
        int i = 0;
        for(String facility: facilityMap.keySet()) {
            i++;
            System.out.println(i + ". " + facilityMap.get(facility).generateFacilityStatusOutput());

        }
    }

    public Graph getFacilityNetworkGraph() {
        if(isFacilityNetworkGraphInitialized()) {
            return facilityNetworkGraph;
        }
        else {
            System.out.println("FacilityNetworkGraph not initialized!");
        }
        return null;
    }
}
