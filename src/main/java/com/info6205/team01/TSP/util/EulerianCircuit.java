package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.UndirectedEdge;

public class EulerianCircuit {
    private Map<Node, List<DirectedEdge>> graph;
    private Map<Node, Integer> nodeToIndex;
    private Node[] nodearray;
    private int N;
    private List<List<Integer>> adjList;

    public EulerianCircuit(Map<Node, List<DirectedEdge>> graph, Map<Node, Integer> nodeToIndex, Node[] nodearray) {
        this.graph = graph;
        this.nodeToIndex = nodeToIndex;
        this.nodearray = nodearray;
        N = nodearray.length;

        this.adjList = new ArrayList<>();
        for(int i = 0; i < N; i++) adjList.add(new ArrayList<>());
        for(Node node : graph.keySet()) {
            for(DirectedEdge e : graph.get(node)) {
                int u = nodeToIndex.get(e.getFrom()), v = nodeToIndex.get(e.getTo());
                addEdge(u, v);
            }
        }
    }

    public List<Node> getEulerianCircuit() {
        // Check if the graph is connected and all vertices have even degree
        if (!isConnected() || !hasEulerianCircuit()) return null;

        // Find the Eulerian circuit using Hierholzer's algorithm
        List<Integer> ec = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        int curVertex = 0;
        stack.push(curVertex);

        while(!stack.isEmpty()) {
            if(!adjList.get(curVertex).isEmpty()) {
                stack.push(curVertex);
                int nextVertex = adjList.get(curVertex).get(0);
                removeEdge(curVertex, nextVertex);
                curVertex = nextVertex;
            } else {
                ec.add(curVertex);
                curVertex = stack.pop();
            }
        }

        List<Node> circuit = new ArrayList<>();
        for(Integer i : ec) circuit.add(nodearray[i]);
        return circuit;
    }

    private void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    private void removeEdge(int u, int v) {
        adjList.get(u).remove((Integer) v);
        adjList.get(v).remove((Integer) u);
    }

    private boolean isConnected() {
        // Use DFS to check if the graph is connected
        boolean[] visited = new boolean[N];
        dfs(0, visited);
        for(boolean v : visited) {
            if(!v) return false;
        }
        return true;
    }

    private void dfs(int cur, boolean[] visited) {
        visited[cur] = true;
        for(Integer next : adjList.get(cur)) {
            if(!visited[next]) {
                dfs(next, visited);
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
