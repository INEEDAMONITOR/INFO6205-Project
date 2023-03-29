package com.info6205.team01.TSP.tactical;

import java.sql.Array;
import java.util.*;

import org.graphstream.graph.Graph;
import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.Preprocessing;

public class ChristofidesAlgorithm {
    public static void main(String[] args) {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node("1", -0.172148, 51.479017));
        nodes.add(new Node("2", -0.0844192, 51.5682443));
        nodes.add(new Node("3", 0.0224653, 51.5338612));
        nodes.add(new Node("4", -0.3050444, 51.3938231));
        nodes.add(new Node("5", 0.05328, 51.604349));

//        Preprocessing preprocessing = new Preprocessing();
//        List<Node> nodes = preprocessing.getNodes().subList(0, 15);

        ChristofidesAlgorithm ca = new ChristofidesAlgorithm(nodes);

        // Build MST with Prim Algorithm;
        List<UndirectedEdge> MST = ca.Prim(nodes);

        for(UndirectedEdge e : MST) System.out.println(e);

//        List<UndirectedEdge> minRoute = ca.findMinRoute();
    }

    public ChristofidesAlgorithm(List<Node> nodes) {
        // Create Whole Graph with all nodes
        this.graph = buildGraph(nodes);
        this.minCost = 0;
    }

    public Map<Node, List<UndirectedEdge>> buildGraph(List<Node> nodes) {
        int n = nodes.size();
        Map<Node, List<UndirectedEdge>> graph = new HashMap<>();
        for(int i = 0; i < n; i++) {
            Node node1 = nodes.get(i);
            for(int j = i + 1; j < n; j++) {
                Node node2 = nodes.get(j);
                if(!graph.containsKey(node1)) graph.put(node1, new ArrayList<>());
                graph.get(node1).add(new UndirectedEdge(node1, node2));
                node1.increseDegree();
                if(!graph.containsKey(node2)) graph.put(node2, new ArrayList<>());
                graph.get(node2).add(new UndirectedEdge(node2, node1));
                node2.increseDegree();
            }
        }
        return graph;
    }

    public List<UndirectedEdge> Prim(List<Node> nodes) {
        HashMap<Node, Boolean> st = new HashMap<>();
        for(Node node : nodes) st.put(node, false);
        PriorityQueue<UndirectedEdge> heap = new PriorityQueue<>((a, b) ->
                (a.getWeight() - b.getWeight() > 0 ? 1 : 0)
        );

        // Initiation
        Node starter = nodes.get(0);
        heap.addAll(graph.get(starter));
        st.put(starter, true);

        List<UndirectedEdge> MST = new ArrayList<>();
        while(!heap.isEmpty()) {
            UndirectedEdge edge = heap.poll();
            Node from = edge.getNodes()[0], to = edge.getNodes()[1];

            if(st.get(from) && st.get(to)) continue;

            MST.add(edge);
            minCost += edge.getWeight();

            if(!st.get(from)) {
                for(UndirectedEdge e : graph.get(from)) heap.add(e);
                st.put(from, true);
            }
            if(!st.get(to)) {
                for(UndirectedEdge e : graph.get(to)) heap.add(e);
                st.put(to, true);
            }
        }

        System.out.println("minCost: " + minCost);
        return MST;
    }

//    public List<UndirectedEdge> findMinRoute() {
//        List<UndirectedEdge> minRoute = transfer(this.graph);
//        return minRoute;
//    }
//
//    public List<UndirectedEdge> transfer(List<DirectedEdge> dlist) {
//
//    }

    private Map<Node, List<UndirectedEdge>> graph;
    // g[a] - all edges begin from node a
    private Map<Node, List<UndirectedEdge>> g;
    private double minCost;
}
