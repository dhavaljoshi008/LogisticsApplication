package utilities;

import facility.FacilityService;

import java.util.*;
/**
 * ShortestPathDijkstraAlgorithmImpl.java
 * LogisticsApplication
 */
public class ShortestPathDijkstraAlgorithmImpl implements ShortestPathAlgorithm {
    private  List<Vertex> nodes;
    private  List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Double> distance;


    private void execute(Vertex source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private double getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private double getShortestDistance(Vertex destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    private LinkedList<String> getPath(Vertex target) {
        LinkedList<String> path = new LinkedList<>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step.getName());
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step.getName());
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

    public boolean initNodesAndEdgesFromGraph(Graph graph) {
        // Initializing nodes and edges from the graph. Creating a copy of the ArrayLists.
        if(graph != null) {
            this.nodes = new ArrayList<>(graph.getVertexes());
            this.edges = new ArrayList<>(graph.getEdges());
        }
        return false;
    }


    public double getShortestDistanceBetween(String source, String destination) {
        Graph facilityNetworkGraph = FacilityService.getFacilityServiceInstance().getFacilityNetworkGraph();
        Vertex sourceVertex = facilityNetworkGraph.getVertex(source);
        Vertex destinationVertex = facilityNetworkGraph.getVertex(destination);
        execute(sourceVertex);
        return getShortestDistance(destinationVertex);
    }

    @Override
    public double getShortestPathDaysBetween(String source, String destination, double hoursPerDay, double milesPerHour) {
        double shortestDistance = getShortestDistanceBetween(source, destination);
        double shortestPathDays = shortestDistance/ (hoursPerDay * milesPerHour);
        return shortestPathDays;
    }


    // Compute the shortest path from source to destination.
    private LinkedList<String> computeShortestPathBetween(String source, String destination) {
        Graph facilityNetworkGraph = FacilityService.getFacilityServiceInstance().getFacilityNetworkGraph();
        Vertex sourceVertex = facilityNetworkGraph.getVertex(source);
        Vertex destinationVertex = facilityNetworkGraph.getVertex(destination);
        execute(sourceVertex);
        return getPath(destinationVertex);
    }

    public String getShortestPathBetween(String source, String destination) {
        LinkedList<String> shortestPath = computeShortestPathBetween(source, destination);
        StringBuilder shortestPathBuilder = new StringBuilder();
        if(shortestPath != null) {
            int size = shortestPath.size();
            int i = 0;
            for(String facilityId: shortestPath) {
                shortestPathBuilder.append(facilityId);
                i++;
                if (i < size) {
                    shortestPathBuilder.append(" --> ");
                }
            }
        }
        else shortestPathBuilder.append("No path found!");
        return  shortestPathBuilder.toString();
    }



}
