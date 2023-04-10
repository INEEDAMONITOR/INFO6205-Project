package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.UndirectedEdge;

public class BlossomAlgorithm {
    private Map<Node, List<DirectedEdge>> graph;
    private Map<Node, Integer> nodes;
    private Integer idx;
    private Map<Node, Node> matches;

    public BlossomAlgorithm(Map<Node, List<DirectedEdge>> graph) {
        this.graph = graph;
        this.nodes = new HashMap<>();
        idx = 1;
        for(Node node : graph.keySet()) {
            nodes.put(node, idx++);
        }
        matches = new HashMap<>();
        for(Node node : graph.keySet()) {
            matches.put(node, null);
        }
    }

    public Integer getNum() { return nodes.size(); }

    public Map<Node, Node> execute() {
        while(true) {
            Map<Node, Node> parent = new HashMap<>();
            for(Node node : graph.keySet()) {
                parent.put(node, null);
            }

            Node start = null;
            for(Node node : graph.keySet()) {
                if(matches.get(node) == null) {
                    start = node;
                    break;
                }
            }
            if(start == null) break;

            Queue<Node> q = new LinkedList<>();
            q.add(start);
            parent.put(start, start);

            while(!q.isEmpty()) {
                Node from = q.remove();
                for (DirectedEdge e : graph.get(from)) {
                    Node to = e.getTo();
                    if (parent.get(to) == null) {
                        parent.put(to, from);
                        if (matches.get(to) == null) {
                            while (!to.equals(start)) {
                                Node from1 = parent.get(to);
                                matches.put(to, from1);
                                matches.put(from1, to);
                                to = from1;
                            }
                            break;
                        } else {
                            q.add(matches.get(to));
                            parent.put(matches.get(to), to);
                        }
                    }
                }
                if (matches.get(start) != null) break;
            }
        }
        return matches;
    }
}
