package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;

public class HamiltonianCircuit {
    private List<Node> eulerianCircuit;
    private Map<Node, List<DirectedEdge>> graph;

    public HamiltonianCircuit(List<Node> eulerianCircuit, Map<Node, List<DirectedEdge>> graph) {
        this.eulerianCircuit = eulerianCircuit;
        this.graph = graph;
    }

    public List<Node> convertEulerianToHamiltonian() {
        // Create a set of visited nodes and an empty Hamiltonian circuit
        Set<Node> visited = new HashSet<>();
        List<Node> hamiltonianCircuit = new ArrayList<>();

        // Traverse the Eulerian circuit and add each node to the Hamiltonian circuit
        for (Node node : eulerianCircuit) {
            if (!visited.contains(node)) {
                visited.add(node);
                hamiltonianCircuit.add(node);
            }
        }

        // Check if the Hamiltonian circuit is valid
        if (hamiltonianCircuit.size() != graph.size()) {
            return null;
        }

        // Return the Hamiltonian circuit
        return hamiltonianCircuit;
    }
}
