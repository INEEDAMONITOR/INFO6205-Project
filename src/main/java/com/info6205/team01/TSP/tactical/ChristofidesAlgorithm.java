package com.info6205.team01.TSP.tactical;

import java.util.*;
import java.math.*;

import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.*;
import com.info6205.team01.TSP.visualization.TestVis;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import javax.sound.midi.SysexMessage;

public class ChristofidesAlgorithm {
    public static void main(String[] args) {
//        List<Node> nodes = new ArrayList<>();
//        nodes.add(new Node("1", -0.172148, 51.479017));
//        nodes.add(new Node("2", -0.0844192, 51.5682443));
//        nodes.add(new Node("3", 0.0224653, 51.5338612));
//        nodes.add(new Node("4", -0.3050444, 51.3938231));
//        nodes.add(new Node("5", 0.05328, 51.604349));

        /* --------------------------------------------------------------------- */
        // Build original graph with directed edges
        tv = new TestVis();
        List<Node> nodes = tv.nodes.subList(0, 150);
        ChristofidesAlgorithm ca = new ChristofidesAlgorithm(nodes);

        // Build MST with Prim Algorithm;
        Map<Node, List<DirectedEdge>> mst = ca.Prim();
        // ca.vistualize(mst);

        // Construct subgraph with "odd-degree nodes" in MST
        Map<Node, List<DirectedEdge>> oddDegreeSubgraph = ca.oddDegreeSubgraph(mst);
        // ca.vistualize(oddDegreeSubgraph);

        // Minimum-Weighted Match for odd-degree subgraph with Blossom Algorithm
        Map<Node, Node> matches = ca.minimumWeightedMatch(oddDegreeSubgraph);

        // Combine MST and minimum-weighted matching to a multigraph
        Map<Node, List<DirectedEdge>> multiGraph = ca.combine(mst, matches);
        // ca.vistualize(multiGraph);

        // Find Eulerian Circuit in multiGraph
        List<Node> eulerianCircuit = ca.findEulerianCircuit(multiGraph);

        // Convert Eulerian Circuit to Hamiltonian Circuits
        List<Node> hamiltonianCircuit = ca.convertHamiltonianCircuit(eulerianCircuit);

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

        // Output graph of Hamiltonian Circuit
        if(hamiltonianCircuit != null) tv.showResult(hamiltonianCircuit.subList(0, hamiltonianCircuit.size() - 1));
    }

    public ChristofidesAlgorithm(List<Node> nodes) {
        this.N = nodes.size();
        this.nodearray = new Node[N];
        this.costMatrix = new double[N][N];
        this.nodeToIndex = new HashMap<>();
        int i = 0;
        for(Node node : nodes) {
            nodearray[i] = node;
            nodeToIndex.put(node, i++);
        }

        // Create Whole Graph with all nodes
        this.originalGraph = buildGraph();
    }

    public Map<Node, List<DirectedEdge>> buildGraph() {
        Map<Node, List<DirectedEdge>> graph = new HashMap<>();
        for(int i = 0; i < N; i++) {
            Node node1 = nodearray[i];
            for(int j = i + 1; j < N; j++) {
                Node node2 = nodearray[j];
                // node1 -> node2
                if(!graph.containsKey(node1)) graph.put(node1, new ArrayList<>());
                DirectedEdge de1 = new DirectedEdge(node1, node2);
                graph.get(node1).add(de1);
                costMatrix[i][j] = de1.getWeight();
                // node2 -> node1
                if(!graph.containsKey(node2)) graph.put(node2, new ArrayList<>());
                DirectedEdge de2 = new DirectedEdge(node2, node1);
                graph.get(node2).add(de2);
                costMatrix[j][i] = de2.getWeight();
            }
        }
        return graph;
    }

    public Map<Node, List<DirectedEdge>> Prim() {
        double[] dist = new double[N];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);

        boolean[] st = new boolean[N];
        int[] parent = new int[N];

        PriorityQueue<DirectedEdge> heap = new PriorityQueue<>((a, b) -> Double.compare(a.getWeight(), b.getWeight()));
        heap.offer(new DirectedEdge(new Node("-1", 0, 0), nodearray[0], 0));
        dist[0] = 0.0;

        while(!heap.isEmpty()) {
            DirectedEdge e = heap.poll();
            int u = nodeToIndex.get(e.getTo());

            if(st[u]) continue;

            st[u] = true;
            parent[u] = nodeToIndex.get(e.getFrom()) == null ? -1 : nodeToIndex.get(e.getFrom());

            for(int v = 0; v < N; v++) {
                if(costMatrix[u][v] != 0 && !st[v] && costMatrix[u][v] < dist[v]) {
                    dist[v] = costMatrix[u][v];
                    heap.add(new DirectedEdge(nodearray[u], nodearray[v], dist[v]));
                }
            }
        }

        Map<Node, List<DirectedEdge>> mst = new HashMap<>();
        for(Node node : nodearray) mst.put(node, new ArrayList<>());

        for(int i = 1; i < N; i++) {
            mst.get(nodearray[i]).add(
                    new DirectedEdge(nodearray[i], nodearray[parent[i]], costMatrix[i][parent[i]])
            );
            mst.get(nodearray[parent[i]]).add(
                    new DirectedEdge(nodearray[parent[i]], nodearray[i], costMatrix[parent[i]][i])
            );
        }

//        // Validate MST
//        MSTValidation valid = new MSTValidation(originalGraph, mst);
//        if(valid.validateMST()) System.out.println("MST is valid");

        return mst;
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

    // Construct Minimum Weighted Perfect Matching;
    public Map<Node, Node> minimumWeightedMatch(Map<Node, List<DirectedEdge>> graph) {
        MinimumWeightMatch mwm = new MinimumWeightMatch(graph);
        Map<Node, Node> matches = mwm.getMatches();
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

    // Form Eulerian Circuit
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
    public List<Node> convertHamiltonianCircuit(List<Node> eulerianCircuit) {
        if(eulerianCircuit == null) return null;

        HamiltonianCircuit hc = new HamiltonianCircuit(eulerianCircuit);
        List<Node> hamiltonianCircuit = hc.convertEulerianToHamiltonian();
        return hamiltonianCircuit;
    }

    // Visualize
    public void vistualize(Map<Node, List<DirectedEdge>> g) {
        List<UndirectedEdge> ue = new ArrayList<>();
        for(List<DirectedEdge> list : g.values()) {
            for(DirectedEdge de : list) {
                if(!ue.contains(de.toUndirectedEdge()))
                    ue.add(de.toUndirectedEdge());
            }
        }
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Tutorial 1");
        graph.setAttribute("ui.stylesheet", "node{\n" +
                "    size: 30px, 30px;\n" +
                "    fill-color: #f7f7f0;\n" +
                "    text-mode: normal; \n" +
                "}");
        ue.forEach(edge -> {
            Node[] nodes1 = edge.getNodes();
            Node node1 = nodes1[0];
            Node node2 = nodes1[1];
            if (graph.getNode(node1.getId()) == null) {
                graph.addNode(node1.getId()).setAttribute("ui.label", node1.getId());
                graph.getNode((node1.getId())).setAttribute("layout.frozen");
                graph.getNode((node1.getId())).setAttribute("xy", node1.getLatitude(), node1.getLongitude());

            }
            if (graph.getNode(node2.getId()) == null) {
                graph.addNode(node2.getId()).setAttribute("ui.label", node2.getId());
                graph.getNode((node2.getId())).setAttribute("layout.frozen");
                graph.getNode((node2.getId())).setAttribute("xy", node2.getLatitude(), node2.getLongitude());
            }
            graph.addEdge(node1.getId() + node2.getId(), node1.getId(), node2.getId()).setAttribute("length", edge.getWeight());
        });
        graph.display();
    }

    private Map<Node, List<DirectedEdge>> originalGraph;
    private Map<Node, Integer> nodeToIndex;
    private Node[] nodearray;
    private double[][] costMatrix;
    private int N;

    static private TestVis tv;
}
