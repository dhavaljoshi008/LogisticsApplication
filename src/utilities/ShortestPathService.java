package utilities;
/**
 * ShortestPathService.java
 * LogisticsApplication
 */
final public class ShortestPathService implements ShortestPathAlgorithm{
    private static ShortestPathService shortestPathServiceInstance;
    private ShortestPathAlgorithm shortestPathAlgorithmDelegate;

    private ShortestPathService(String algorithm) {
        shortestPathAlgorithmDelegate = ShortestPathAlgorithmFactory.build(algorithm);
    }

    public static ShortestPathService getShortestPathServiceInstance() {
        if(shortestPathServiceInstance == null) {
            shortestPathServiceInstance = new ShortestPathService("Dijkstra");
        }
        return shortestPathServiceInstance;
    }

    public void changeShortestPathAlgorithm(String algorithm) {
        shortestPathAlgorithmDelegate = ShortestPathAlgorithmFactory.build(algorithm);
    }

    @Override
    public String getShortestPathBetween(String source, String destination) {
        return shortestPathAlgorithmDelegate.getShortestPathBetween(source, destination);
    }

    @Override
    public double getShortestDistanceBetween(String source, String destination) {
        return shortestPathAlgorithmDelegate.getShortestDistanceBetween(source, destination);
    }

    @Override
    public double getShortestPathDaysBetween(String source, String destination, double hoursPerDay, double milesPerHour) {
        return shortestPathAlgorithmDelegate.getShortestPathDaysBetween(source, destination, hoursPerDay, milesPerHour);
    }

    @Override
    public boolean initNodesAndEdgesFromGraph(Graph graph) {
       return shortestPathAlgorithmDelegate.initNodesAndEdgesFromGraph(graph);
    }
}
