package utilities;
/**
 * ShortestPathAlgorithmFactory.java
 * LogisticsApplication
 */
public class ShortestPathAlgorithmFactory {
    public static ShortestPathAlgorithm build(String algorithm) {
        switch (algorithm) {
            case "dijkstra":
            case "Dijkstra":
                return new ShortestPathDijkstraAlgorithmImpl();
            case "null":
            case "NULL":
                return new ShortestPathNullAlgorithmImpl();
            default:
                System.out.println("Usage for setting shortest path algorithm as Dijkstra's Shortest Path algorithm:");
                System.out.println("ShortestPathService.getShortestPathServiceInstance().changeShortestPathAlgorithm(\"dijkstra\");");
                System.out.println("or");
                System.out.println("ShortestPathService.getShortestPathServiceInstance().changeShortestPathAlgorithm(\"Dijkstra\");");
                System.out.println();
                System.out.println("Default: Null Object");
                return new ShortestPathNullAlgorithmImpl();
        }
    }
}
