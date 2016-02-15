package utilities;
/**
 * ShortestPathAlgorithm.java
 * LogisticsApplication
 */
public interface ShortestPathAlgorithm {
    String getShortestPathBetween(String source, String destination);
    double getShortestDistanceBetween(String source, String destination);
    double getShortestPathDaysBetween(String source, String destination, double hoursPerDay, double milesPerHour);
    boolean initNodesAndEdgesFromGraph(Graph graph);
}
