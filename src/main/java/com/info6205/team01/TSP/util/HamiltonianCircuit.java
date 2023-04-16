package com.info6205.team01.TSP.util;

import java.util.*;

import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;

public class HamiltonianCircuit {
    private List<Node> eulerianCircuit;

    public HamiltonianCircuit(List<Node> eulerianCircuit) {
        this.eulerianCircuit = eulerianCircuit;
    }

    public List<Node> convertEulerianToHamiltonian() {
        // Create a set of visited nodes and an empty Hamiltonian circuit
        Set<Node> visited = new HashSet<>();
        List<Node> hamiltonianCircuit = new ArrayList<>();

        // Traverse the Eulerian circuit and add each node to the Hamiltonian circuit
        for (Node node : eulerianCircuit) {
            if (!visited.contains(node)) {
                hamiltonianCircuit.add(node);
                visited.add(node);
            }
        }

        // Check if the Hamiltonian circuit is valid
        if(!hamiltonianCircuit.isEmpty()) hamiltonianCircuit.add(hamiltonianCircuit.get(0));

        // Return the Hamiltonian circuit
        return hamiltonianCircuit;
    }
}
