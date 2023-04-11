package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;

public class BlossomAlgorithm {
    private Map<Node, List<DirectedEdge>> graph;
    private Map<Node, Integer> nodeToIndex;
    private Node[] nodearray;
    private double[][] costMatrix;
    private int N;

    public BlossomAlgorithm(Map<Node, List<DirectedEdge>> graph) {
        this.graph = graph;

        this.N = this.graph.keySet().size();

        nodearray = new Node[N];
        int idx = 0;
        nodeToIndex = new HashMap<>();
        for(Node node : this.graph.keySet()) {
            nodearray[idx] = node;
            nodeToIndex.put(node, idx++);
        }

        costMatrix = new double[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(i == j) costMatrix[i][j] = 0.0;
                else costMatrix[i][j] = new DirectedEdge(nodearray[i], nodearray[j]).getWeight();
            }
        }
    }

    public Map<Node, Node> run() {
        int[] matches = new int[N];
        Arrays.fill(matches, -1);
        for (int i = 0; i < N; i++) {
            if (matches[i] == -1) {
                int[] parent = new int[N];
                Arrays.fill(parent, -1);
                Queue<Integer> q = new LinkedList<>();
                q.offer(i);
                while (!q.isEmpty()) {
                    int u = q.poll();
                    for (int v = 0; v < N; v++) {
                        if (u == v || costMatrix[u][v] == 0.0) continue;
                        if (matches[u] == v) continue;

                        if (parent[v] == -1) {
                            parent[v] = u;
                            if (matches[v] == -1) {
                                augment(matches, parent, u, v);
                                break;
                            }
                            q.offer(matches[v]);
                        } else {
                            int w = findCommonAncestor(u, v, parent, matches);
                            if (w != -1) blossom(matches, parent, u, v);
                        }
                    }
                }
            }
        }

        Map<Node, Node> matching = new HashMap<>();
        for (int i = 0; i < N; i++) {
            if (matches[i] != -1) matching.put(nodearray[i], nodearray[matches[i]]);
        }

        return matching;
    }

    private void augment(int[] matches, int[] parent, int u, int v) {
        while (u != -1) {
            int temp = matches[u];
            matches[u] = v;
            matches[v] = u;
            v = temp;
            u = parent[v];
        }
    }

    private int findCommonAncestor(int u, int v, int[] parent, int[] matches) {
        boolean[] visited = new boolean[parent.length];
        visited[u] = true;

        while (true) {
            v = parent[matches[v]];
            if (v == -1) return -1;
            if (visited[v]) return v;
            visited[v] = true;

            u = parent[matches[u]];
            if (u == -1) return -1;
            if (visited[u]) return u;
            visited[u] = true;
        }
    }

    private void blossom(int[] matches, int[] parent, int u, int v) {
        int p = findPath(u, v, parent, matches);
        int q = findPath(v, u, parent, matches);
        int[] base = new int[matches.length];
        Arrays.fill(base, -1);

        for (int i = p; i != -1; i = q) {
            base[i] = u;
            u = parent[i];
        }

        for (int i = q; i != -1; i = p) {
            base[i] = u;
            u = parent[i];
        }

        for (int i = 0; i < matches.length; i++) {
            if (base[i] != -1) {
                matches[i] = base[i];
                if (matches[base[i]] == -1) {
                    matches[base[i]] = i;
                }
            }
        }
    }

    private int findPath(int u, int v, int[] parent, int[] match) {
        boolean[] visited = new boolean[parent.length];
        visited[u] = true;
        while (true) {
            u = parent[match[u]];

            if (u == -1) return -1;

            if (visited[u]) return u;

            visited[u] = true;
            u = parent[u];

            if (u == -1) return -1;

            if (visited[u]) return u;

            visited[u] = true;
        }
    }

}
