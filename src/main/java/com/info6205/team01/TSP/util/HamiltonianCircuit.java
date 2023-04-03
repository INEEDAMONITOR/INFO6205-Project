package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;

public class HamiltonianCircuit {
    private List<Node> eulerianCircuit;
    private Map<Node, List<DirectedEdge>> graph;
    private Node[] nodearray;

    public HamiltonianCircuit(List<Node> eulerianCircuit, Map<Node, List<DirectedEdge>> graph) {
        this.eulerianCircuit = eulerianCircuit;
        int n = eulerianCircuit.size(), i = 0;
        nodearray = new Node[n];
        this.graph = graph;
    }

    public List<Node> EulerianToHamiltonian() {
        Set<Node> visited = new HashSet<>();
        List<Node> hamiltonianCircuit = new ArrayList<>();
        int n = eulerianCircuit.size();
        for (int i = 0; i < n; i++) {
            Node node = eulerianCircuit.get(i);
            if(!visited.contains(node)) {
                visited.add(node);
                hamiltonianCircuit.add(node);
            }
            else {
                Node start = hamiltonianCircuit.get(0), end = node;
                List<Node> path = getPath(start, end, eulerianCircuit, graph);

                if(path == null) return null;

                hamiltonianCircuit.remove(hamiltonianCircuit.size() - 1);
                hamiltonianCircuit.addAll(path.subList(1, path.size() - 1));
            }
        }
        return hamiltonianCircuit;
    }

    // Returns the path between the "start" and "end" in the Eulerian circuit.
    // Returns null if no such path exists.
    public List<Node> getPath(Node start, Node end, List<Node> eulerianCircuit, Map<Node, List<DirectedEdge>> graph) {
        Map<Node, Node> prev = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();

        queue.offer(start);
        prev.put(start, null);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr.equals(end))
                break;
            for (DirectedEdge edge : graph.get(curr)) {
                Node to = edge.getTo();
                if (prev.get(to) == null) {
                    prev.put(to, curr);
                    queue.offer(to);
                }
            }
        }
        if (prev.get(end) == null)
            return null;
        List<Node> path = new ArrayList<>();
        for (Node curr = end; curr != null; curr = prev.get(curr)) {
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
