package com.info6205.team01.TSP.tactical;

import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.Preprocessing;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class GreedyHeuristic {
    public static void main(String[] args) {
        ArrayList<Node> list = new ArrayList<>();
        list.add(new Node("1", -0.172148, 51.479017));
        list.add(new Node("2", -0.0844192, 51.5682443));
        list.add(new Node("3", 0.0224653, 51.5338612));
        list.add(new Node("4", -0.3050444, 51.3938231));
        list.add(new Node("5", 0.05328, 51.604349));
        Preprocessing preprocessing = new Preprocessing();

        GreedyHeuristic gh = new GreedyHeuristic(preprocessing.getNodes().subList(0, 15));
        List<UndirectedEdge> edges = gh.findMinRoute();

        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Tutorial 1");
        graph.setAttribute("ui.stylesheet", "node{\n" +
                "    size: 30px, 30px;\n" +
                "    fill-color: #f7f7f0;\n" +
                "    text-mode: normal; \n" +
                "}");
        edges.forEach(edge -> {
            Node[] nodes = edge.getNodes();
            Node node1 = nodes[0];
            Node node2 = nodes[1];
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
//        graph.addNode("A");
//        graph.addNode("B");
//        graph.addNode("C");
//        graph.addEdge("AB", "A", "B");
//        graph.addEdge("BC", "B", "C");
//        graph.addEdge("CA", "C", "A");

        graph.display();
        Node node1 = new Node("1", 1, 1);
        Node node2 = new Node("2", 1, 1);
        Node node3 = new Node("3", 1, 1);
        List<UndirectedEdge> e = new ArrayList<>();
        e.add(new UndirectedEdge(node1, node2));
//        e.add(new UndirectedEdge(node2, node3));
    }

    List<Node> nodes;
    //    double[][] adjacentMatrix;
    private final PriorityQueue<UndirectedEdge> edgesWeightMinHeap;

    public GreedyHeuristic(List<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
        edgesWeightMinHeap = new PriorityQueue<>(new Comparator<UndirectedEdge>() {
            @Override
            public int compare(UndirectedEdge o1, UndirectedEdge o2) {
                return (int) (o1.getWeight() - o2.getWeight());
            }
        });
    }

    public List<UndirectedEdge> findMinRoute() {
        // Add nodes into min-heap
        int counter = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                Node node1 = nodes.get(i);
                Node node2 = nodes.get(j);
                edgesWeightMinHeap.offer(new UndirectedEdge(node1, node2));
            }
        }
        List<UndirectedEdge> resEdges = new ArrayList<>();
        UndirectedEdge initEdge = edgesWeightMinHeap.poll();
        initEdge.getNodes()[0].increseDegree();
        initEdge.getNodes()[1].increseDegree();
        resEdges.add(initEdge);
        counter = 2;
        System.out.println(resEdges);
        while (!edgesWeightMinHeap.isEmpty()) {
            System.out.println(resEdges);
            UndirectedEdge edge = edgesWeightMinHeap.poll();
            System.out.println(edge);
            Node[] nodes = edge.getNodes();
            Node node1 = nodes[0];
            Node node2 = nodes[1];
            if (node1.getDegree() >= 2 || node2.getDegree() >= 2) {
                continue;
            }

            if (isNode1ReachableNode2(resEdges, node1, node2)) {
                continue;
            }
            if (node1.getDegree() == 0 && node2.getDegree() == 0) {
                counter += 2;
            } else {
                counter += 1;
            }
            node1.increseDegree();
            node2.increseDegree();
            resEdges.add(edge);
            if (counter == nodes.length) {
                break;
            }
        }
        Node start = null;
        Node end = null;
        for (Node node : nodes) {
            if (node.getDegree() == 1) {
                if (start == null) {
                    start = node;
                    node.increseDegree();
                } else {
                    end = node;
                    break;
                }
            }
        }
        resEdges.add(new UndirectedEdge(start, end));
        return resEdges;
    }

    private boolean hasCycle(List<UndirectedEdge> edges, UndirectedEdge edge) {
        Node[] nodes = edge.getNodes();
        Node node1 = nodes[0];
        Node node2 = nodes[1];
        return true;
    }

    private boolean isNode1ReachableNode2(List<UndirectedEdge> edges, Node node1, Node node2) {
        Deque<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        for (UndirectedEdge edge : edges) {
            Node nextNode = edge.containsNodes(node1);
            if (nextNode != null) {
                queue.offerLast(nextNode);
                visited.add(node1);
                visited.add(nextNode);
                break;
            }
        }
        while (!queue.isEmpty()) {
            Node node = queue.pollFirst();
            if (node == node2) {
                return true;
            }
            for (UndirectedEdge edge : edges) {
                Node nextNode = edge.containsNodes(node);
                if (nextNode != null && !visited.contains(nextNode)) {
                    visited.add(nextNode);
                    queue.offerLast(nextNode);
                }
            }
        }
        return false;
    }
}
