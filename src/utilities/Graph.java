package utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph.java
 * LogisticsApplication
 */


public class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;
    private Map<String, Vertex> vertexMap;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
        generateVertexMap(this.vertexes);
    }

    // Generating vertexMap.
    private void generateVertexMap(List<Vertex> vertexList) {
        vertexMap = new HashMap<>();
        for(Vertex v: vertexList) {
            vertexMap.put(v.getName(),v);
        }
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    // Fetching desired vertex from vertexMap.
    public Vertex getVertex(String vertexName){
        return vertexMap.get(vertexName);
    }
}