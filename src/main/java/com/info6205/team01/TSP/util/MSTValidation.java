package com.info6205.team01.TSP.util;

import java.util.*;
import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.Graph.Node;

public class MSTValidation {
    private Map<Node, List<DirectedEdge>> graph, MST;
    private int N;

    public MSTValidation(Map<Node, List<DirectedEdge>> g, Map<Node, List<DirectedEdge>> MST) {
        this.graph = g;
        this.MST = MST;
        this.N = MST.keySet().size();
    }

    public boolean validateMST() {
        // Check connectivity
        if (!isConnected()) return false;

        // Check minimality
        List<DirectedEdge> mst_edges = transfer(MST);
        double mstWeight = mst_edges.stream().mapToDouble(DirectedEdge::getWeight).sum();

        List<DirectedEdge> sortedEdges = new ArrayList<>(transfer(graph));
        sortedEdges.sort((e1, e2) -> Double.compare(e1.getWeight(), e2.getWeight()));

        double totalWeight = 0.0;
        Set<Node> visited = new HashSet<>();
        for (DirectedEdge edge : sortedEdges) {
            if (visited.add(edge.getFrom()) || visited.add(edge.getTo())) {
                totalWeight += edge.getWeight();
            }
            if (visited.size() == N) break;
        }

        return mstWeight <= totalWeight;
    }

    private boolean isConnected() {
        Set<Node> visited = new HashSet<>();

        for (List<DirectedEdge> es : this.MST.values()) {
            for(DirectedEdge e : es) {
                visited.add(e.getFrom());
                visited.add(e.getTo());
            }
        }

        return visited.size() == N;
    }

    private List<DirectedEdge> transfer(Map<Node, List<DirectedEdge>> g) {
        List<DirectedEdge> edges = new ArrayList<>();
        Set<DirectedEdge> visited = new HashSet<>();
        for(Node node : g.keySet()) {
            for(DirectedEdge e : g.get(node)) {
                if(visited.contains(e)) continue;

                visited.add(e);
                for(DirectedEdge e2 : g.get(e.getTo())) {
                    if(e2.getTo() == e.getFrom())
                        visited.add(e2);
                }

                edges.add(e);
            }
        }
        return edges;
    }
}
