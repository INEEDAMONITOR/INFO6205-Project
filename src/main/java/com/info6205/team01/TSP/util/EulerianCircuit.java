package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.UndirectedEdge;

public class EulerianCircuit {
    private Map<Node, List<DirectedEdge>> graph;
    private Map<Node, Integer> nodeToIndex;
    private Node[] nodearray;

    public EulerianCircuit(Map<Node, List<DirectedEdge>> graph, Map<Node, Integer> nodeToIndex, Node[] nodearray) {
        this.graph = graph;
        this.nodeToIndex = nodeToIndex;
        this.nodearray = nodearray;
    }

    public List<Node> getEulerianCircuit() {
        // Check if the graph is connected and all vertices have even degree
        if (!isConnected() || !hasEulerianCircuit()) return null;

        // Find the Eulerian circuit using Hierholzer's algorithm
        List<Node> circuit = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        stack.push(nodearray[0]);
        while(!stack.isEmpty()) {
            Node current = stack.peek();
            if(!graph.get(current).isEmpty()) {
                DirectedEdge edge = graph.get(current).remove(0);
                stack.push(edge.getTo());
            }
            else
                circuit.add(stack.pop());
        }

        Collections.reverse(circuit);
        return circuit;
    }

    private boolean isConnected() {
        // Use DFS to check if the graph is connected
        boolean[] visited = new boolean[graph.size()];
        dfs(graph.keySet().iterator().next(), visited);
        for(boolean v : visited) {
            if(!v) return false;
        }
        return true;
    }

    private void dfs(Node node, boolean[] visited) {
        visited[nodeToIndex.get(node)] = true;
        for(DirectedEdge edge : graph.get(node)) {
            Node to = edge.getTo();
            if(!visited[nodeToIndex.get(to)]) {
                dfs(to, visited);
            }
        }
    }

    private boolean hasEulerianCircuit() {
        // Check if all vertices have even degree
        for (Node node : graph.keySet()) {
            if(graph.get(node).size() % 2 != 0) return false;
        }
        return true;
    }
}
