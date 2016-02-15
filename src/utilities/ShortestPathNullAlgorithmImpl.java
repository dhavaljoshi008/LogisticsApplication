package utilities;
/**
 * ShortestPathNullAlgorithmImpl.java
 * LogisticsApplication
 */
public class ShortestPathNullAlgorithmImpl implements ShortestPathAlgorithm {
    @Override
    public String getShortestPathBetween(String source, String destination) {
        return "";
    }

    @Override
    public double getShortestDistanceBetween(String source, String destination) {
        return -1;
    }

    @Override
    public double getShortestPathDaysBetween(String source, String destination, double hoursPerDay, double milesPerHour) {
        return -1;
    }

    @Override
    public boolean initNodesAndEdgesFromGraph(Graph graph) {
        return false;
    }
}
