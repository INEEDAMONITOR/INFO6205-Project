package com.info6205.team01.TSP.tactical;

import java.util.*;

import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.EulerianCircuit;
import com.info6205.team01.TSP.util.HamiltonianCircuit;
import com.info6205.team01.TSP.util.Preprocessing;
import com.info6205.team01.TSP.util.BlossomAlgorithm;
import com.info6205.team01.TSP.visualization.TestVis;

public class ChristofidesAlgorithm {
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("1", -0.172148, 51.479017));
        nodes.add(new Node("2", -0.0844192, 51.5682443));
        nodes.add(new Node("3", 0.0224653, 51.5338612));
        nodes.add(new Node("4", -0.3050444, 51.3938231));
        nodes.add(new Node("5", 0.05328, 51.604349));

//        Preprocessing preprocessing = new Preprocessing();
//        List<Node> nodes = preprocessing.getNodes().subList(0, 15);

        /* --------------------------------------------------------------------- */
        // Build original graph with directed edges
        ChristofidesAlgorithm ca = new ChristofidesAlgorithm(nodes);

        // Build MST with Prim Algorithm;
        Map<Node, List<DirectedEdge>> MST = ca.constructMST(nodes);

        // Construct subgraph with "odd-degree nodes" in MST
        Map<Node, List<DirectedEdge>> oddDegreeSubgraph = ca.oddDegreeSubgraph(MST);

        // Minimum-Weighted Match for odd-degree subgraph with Blossom Algorithm
        Map<Node, Node> matches = ca.minimumWeightedMatch(oddDegreeSubgraph);

        // Combine MST and minimum-weighted matching to a multigraph
        Map<Node, List<DirectedEdge>> multiGraph = ca.combine(MST, matches);

        // Find Eulerian Circuit in multiGraph
        List<Node> eulerianCircuit = ca.findEulerianCircuit(multiGraph);

        // Convert Eulerian Circuit to Hamiltonian Circuits
        List<Node> hamiltonianCircuit = ca.convertHamiltonianCircuit(eulerianCircuit, multiGraph);

        // Sum up the weight of Hamiltonian Circuit;
        double sum = 0.0;
        if(hamiltonianCircuit != null) {
            for(int i = 1; i < hamiltonianCircuit.size(); i++) {
                Node from = hamiltonianCircuit.get(i - 1), to = hamiltonianCircuit.get(i);
                DirectedEdge e = new DirectedEdge(from, to);
                sum += e.getWeight();
            }
            System.out.println("Best tour length: " + sum);
        }
        else System.out.println("Hamiltonian Circult is NULL!");

<<<<<<< HEAD
        System.out.print("Path: ");
=======
        TestVis tv = new TestVis();
        tv.showResult(hamiltonianCircuit);

>>>>>>> main
        for(Node node : hamiltonianCircuit) {
            System.out.print(node.getId() + " -> ");
        }
        System.out.println(hamiltonianCircuit.get(0).getId());
    }

    public ChristofidesAlgorithm(List<Node> nodes) {
        int n = nodes.size(), i = 0;
        this.nodearray = new Node[n];
        this.nodeToIndex = new HashMap<>();
        for(Node node : nodes) {
            nodearray[i] = node;
            nodeToIndex.put(node, i++);
        }

        // Create Whole Graph with all nodes
        this.originalGraph = buildGraph();
    }

    public Map<Node, List<DirectedEdge>> buildGraph() {
        Map<Node, List<DirectedEdge>> graph = new HashMap<>();
        for(int i = 0; i < nodearray.length; i++) {
            Node node1 = nodearray[i];
            for(int j = i + 1; j < nodearray.length; j++) {
                Node node2 = nodearray[j];
                if(!graph.containsKey(node1)) graph.put(node1, new ArrayList<>());
                graph.get(node1).add(new DirectedEdge(node1, node2));
                if(!graph.containsKey(node2)) graph.put(node2, new ArrayList<>());
                graph.get(node2).add(new DirectedEdge(node2, node1));
            }
        }
        return graph;
    }

    // Construct MST with Prim / Kruskal Algorithm
    public Map<Node, List<DirectedEdge>> constructMST(List<Node> nodes) {
        int n = nodes.size();
        boolean[] visited = new boolean[n];
        Map<Node, DirectedEdge> parent = new HashMap<>();
        // dist[i] - distance from node i to the set
        double[] dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> Double.compare(dist[nodeToIndex.get(a)], dist[nodeToIndex.get(b)]));
        for(Node node : originalGraph.keySet()) {
            heap.offer(node);
        }

        Node start = heap.peek();
        dist[nodeToIndex.get(start)] = 0;
        while(!heap.isEmpty()) {
            Node node = heap.poll();
            int idx = nodeToIndex.get(node);

            if(visited[idx]) continue;

            visited[idx] = true;
            for(DirectedEdge edge : originalGraph.get(node)) {
                Node to = edge.getTo();
                int to_idx = nodeToIndex.get(to);
                if(!visited[to_idx] && edge.getWeight() < dist[to_idx]) {
                    dist[to_idx] = edge.getWeight();
                    parent.put(to, edge);
                    heap.offer(to);
                }
            }
        }

        Map<Node, List<DirectedEdge>> MST = new HashMap<>();
        for (Node node : originalGraph.keySet())
            MST.put(node, new ArrayList<>());

        for (DirectedEdge edge : parent.values()) {
            Node from = edge.getFrom(), to = edge.getTo();
            MST.get(from).add(new DirectedEdge(from, to));
            MST.get(to).add(new DirectedEdge(to, from));
        }

        return MST;
    }

    // Construct Subgraph with "odd-degrees nodes" in MST
    public Map<Node, List<DirectedEdge>> oddDegreeSubgraph(Map<Node, List<DirectedEdge>> MST) {
        // find all "odd-degree nodes" in MST
        Set<Node> oddNodes = new HashSet<>();
        for(Node node : MST.keySet()) {
            if(MST.get(node).size() % 2 == 1) oddNodes.add(node);
        }

        // Construct subgraph with original graph
        Map<Node, List<DirectedEdge>> subgraph = new HashMap<>();
        for(Node node : oddNodes)
            subgraph.put(node, new ArrayList<>());

        for(Node from : oddNodes) {
            for(DirectedEdge e : originalGraph.get(from)) {
                Node to = e.getTo();
                if(oddNodes.contains(to)) subgraph.get(from).add(e);
            }
        }

        return subgraph;
    }

    // Construct Minimum Weighted Match;
    public Map<Node, Node> minimumWeightedMatch(Map<Node, List<DirectedEdge>> graph) {
        BlossomAlgorithm ba = new BlossomAlgorithm(graph);
        Map<Node, Node> matches = ba.execute();
        return matches;
    }

    // Combine matches and MST to build a multigraph
    private Map<Node, List<DirectedEdge>> combine(Map<Node, List<DirectedEdge>> MST, Map<Node, Node> matches) {
        Map<Node, List<DirectedEdge>> multiGraph = new HashMap<>();

        // Must use deep copy
        for(Node node : MST.keySet()) {
            multiGraph.put(node, new ArrayList<>());
            for(DirectedEdge e : MST.get(node)) {
                multiGraph.get(node).add(new DirectedEdge(e));
            }
        }

        matches.forEach((k, v) -> {
            DirectedEdge edge = new DirectedEdge(k, v);
            multiGraph.get(k).add(edge);
        });

        return multiGraph;
    }

    // Find Eulerian Circuit
    public List<Node> findEulerianCircuit(Map<Node, List<DirectedEdge>> multiGraph) {
        Map<Node, List<DirectedEdge>> graph = new HashMap<>();

        // Must use deep copy
        for(Node node : multiGraph.keySet()) {
            graph.put(node, new ArrayList<>());
            for(DirectedEdge e : multiGraph.get(node)) {
                graph.get(node).add(new DirectedEdge(e));
            }
        }

        EulerianCircuit ec = new EulerianCircuit(graph, nodeToIndex, nodearray);
        List<Node> circuit = ec.getEulerianCircuit();
        return circuit;
    }

    // Convert Eulerian Circuit to Hamiltonian Circuit
    public List<Node> convertHamiltonianCircuit(List<Node> eulerianCircuit, Map<Node, List<DirectedEdge>> multiGraph) {
        HamiltonianCircuit hc = new HamiltonianCircuit(eulerianCircuit, multiGraph);
        List<Node> hamiltonianCircuit = hc.convertEulerianToHamiltonian();
        return hamiltonianCircuit;
    }

    private Map<Node, List<DirectedEdge>> originalGraph;
    private Map<Node, Integer> nodeToIndex;
    private Node[] nodearray;
}
