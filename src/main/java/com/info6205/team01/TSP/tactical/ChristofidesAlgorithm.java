package com.info6205.team01.TSP.tactical;

import java.util.*;

import org.graphstream.graph.Graph;
import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.Preprocessing;

public class ChristofidesAlgorithm {
    public static void main(String[] args) {
//        ArrayList<Node> nodes = new ArrayList<>();
//        nodes.add(new Node("1", -0.172148, 51.479017));
//        nodes.add(new Node("2", -0.0844192, 51.5682443));
//        nodes.add(new Node("3", 0.0224653, 51.5338612));
//        nodes.add(new Node("4", -0.3050444, 51.3938231));
//        nodes.add(new Node("5", 0.05328, 51.604349));

        Preprocessing preprocessing = new Preprocessing();
        List<Node> nodes = preprocessing.getNodes().subList(0, 15);
        ChristofidesAlgorithm ca = new ChristofidesAlgorithm(nodes);

        // ChristofidesAlgorithm ca = new ChristofidesAlgorithm(nodes);

        // Build MST with Prim Algorithm;
        List<DirectedEdge> MST = ca.Prim(nodes);

        for(DirectedEdge e : MST) {
            System.out.println(e);
        }

        List<UndirectedEdge> minRoute = ca.findMinRoute();
    }

    public ChristofidesAlgorithm(List<Node> nodes) {
        // Create Whole Graph with all nodes
        this.graph = buildGraph(nodes);
        this.g = buildGraph2(nodes);
    }

    public List<DirectedEdge> buildGraph(List<Node> nodes) {
        int n = nodes.size();
        List<DirectedEdge> graph = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            Node node1 = nodes.get(i);
            for(int j = 0; j < n; j++) {
                if(i != j) {
                    Node node2 = nodes.get(j);
                    graph.add(new DirectedEdge(node1, node2));
                }
            }
        }
        return graph;
    }

    public Map<Node, List<DirectedEdge>> buildGraph2(List<Node> nodes) {
        int n = nodes.size();
        Map<Node, List<DirectedEdge>> hash = new HashMap<>();

        for(int i = 0; i < n; i++) {
            Node node1 = nodes.get(i);
            hash.put(node1, new ArrayList<>());
            for(int j = 0; j < n; j++) {
                if(i != j) {
                    Node node2 = nodes.get(j);
                    hash.get(node1).add(new DirectedEdge(node1, node2));
                }
            }
        }

        return hash;
    }

    public List<DirectedEdge> Prim(List<Node> nodes) {
        HashMap<Node, Boolean> st = new HashMap<>();
        for(Node node : nodes) st.put(node, false);

        PriorityQueue<DirectedEdge> heap = new PriorityQueue<>((a, b) ->
                (a.getWeight() - b.getWeight() > 0 ? 1 : 0)
        );

        Node node = nodes.get(0);
        st.put(node, true);
        for(DirectedEdge e : g.get(node)) {
            heap.offer(e);
        }
        int minCost = 0;

        List<DirectedEdge> MST = new ArrayList<>();
        while(!heap.isEmpty()) {
            DirectedEdge edge = heap.poll();
            Node to = edge.getTo();

            if(!st.get(to)) {
                // build MST
                st.put(to, true);
                MST.add(edge);
                minCost += edge.getWeight();
            }

            for(DirectedEdge e : g.get(to)) {
                if(!st.get(e.getTo())) heap.offer(e);
            }
        }

        System.out.println("minCost: " + minCost);
        return MST;
    }

    public List<UndirectedEdge> findMinRoute() {
        List<UndirectedEdge> minRoute = transfer(this.graph);
        return minRoute;
    }

    public List<UndirectedEdge> transfer(List<DirectedEdge> dlist) {

    }

    private List<DirectedEdge> graph;
    // g[a] - all edges begin from node a
    Map<Node, List<DirectedEdge>> g;
}
