package com.info6205.team01.TSP.visualization;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.tactical.AntColonyOptimization;
import com.info6205.team01.TSP.tactical.GreedyHeuristic;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.util.Preprocessing;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;

public class TestVis {
    public static void main(String[] args) {
        TestVis tv = new TestVis();
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("1", -0.172148, 51.479017));
        nodes.add(new Node("2", -0.0844192, 51.5682443));
        nodes.add(new Node("3", 0.0224653, 51.5338612));
        nodes.add(new Node("4", -0.3050444, 51.3938231));
        nodes.add(new Node("5", 0.05328, 51.604349));
        Preprocessing preprocessing = new Preprocessing();
        GreedyHeuristic gh = new GreedyHeuristic(preprocessing.getNodes().subList(0, 15));
        tv.showResult(gh.getMinNodes());
    }
    public List<Node> nodes;
    public TestVis() {
        try {
            LoadCSVData loader = new LoadCSVData();
            nodes = loader.nodes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showResult(List<Node> nodes) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Tutorial 1");
        graph.setAttribute("ui.stylesheet", "node{\n" +
                "    size: 30px, 30px;\n" +
                "    fill-color: #f7f7f0;\n" +
                "    text-mode: normal; \n" +
                "}");
        graph.addNode(nodes.get(0).getId()).setAttribute("ui.label", nodes.get(0).getId());
        graph.getNode((nodes.get(0).getId())).setAttribute("layout.frozen");
        graph.getNode((nodes.get(0).getId())).setAttribute("xy", nodes.get(0).getLatitude(), nodes.get(0).getLongitude());;
        for (int i = 1; i < nodes.size(); i++) {
            Node cur = nodes.get(i);
            Node prev = nodes.get(i - 1);
            graph.addNode(cur.getId()).setAttribute("ui.label", cur.getId());;
            graph.getNode((cur.getId())).setAttribute("layout.frozen");
            graph.getNode((cur.getId())).setAttribute("xy", cur.getLatitude(), cur.getLongitude());;
            graph.addEdge(cur.getId()+prev.getId(), cur.getId(), prev.getId()).setAttribute("length", Node.getDistance(cur, prev));
        }
        Node cur = nodes.get(0);
        Node prev = nodes.get(nodes.size() - 1);
        graph.addEdge(cur.getId()+prev.getId(), cur.getId(), prev.getId()).setAttribute("length", Node.getDistance(cur, prev));
        graph.display();

    }
}
