package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.DirectedEdge;

public class MinimumWeightMatch {
    private Map<Node, List<DirectedEdge>> graph;
    private Map<Node, Integer> nodeToIndex;
    private Node[] nodearray;
    private double[][] costMatrix;
    private int N;
    private List<Integer> oddDegreeNode;

    public MinimumWeightMatch(Map<Node, List<DirectedEdge>> graph) {
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

        this.oddDegreeNode = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            oddDegreeNode.add(i);
        }
    }

    // Find matches with nearest neighbour node
    public Map<Node, Node> getMatches() {
        double distance = 0.0, minv = Double.MAX_VALUE;
        int nextCityIndex = 0, indexForRemove = 0;
        List<DirectedEdge> edges = new ArrayList<>();

        DirectedEdge tempEdge = null;

        int temp1 = -1, temp2 = -1;
        for(int i = 0; i < N; i = nextCityIndex) {
            temp1 = oddDegreeNode.get(i);
            oddDegreeNode.remove(i);
            for(int k = 0; k < oddDegreeNode.size(); k++) {
                temp2 = oddDegreeNode.get(k);

                distance = new DirectedEdge(nodearray[temp1], nodearray[temp2]).getWeight();

                if(distance < minv) {
                    minv = distance;
                    nextCityIndex = 0;
                    indexForRemove = k;
                }
            }

            temp2 = oddDegreeNode.get(indexForRemove);
            tempEdge = new DirectedEdge(nodearray[temp1], nodearray[temp2], minv);
            edges.add(tempEdge);

            minv = Double.MAX_VALUE;
            oddDegreeNode.remove(indexForRemove);

            if(oddDegreeNode.size() == 2) {
                int from = oddDegreeNode.get(0), to = oddDegreeNode.get(1);
                tempEdge = new DirectedEdge(nodearray[from], nodearray[to]);
                edges.add(tempEdge);
                break;
            }
        }

        Map<Node, Node> matches = new HashMap<>();
        for(DirectedEdge e : edges) {
            matches.put(e.getFrom(), e.getTo());
            matches.put(e.getTo(), e.getFrom());
        }
        return matches;
    }


}
