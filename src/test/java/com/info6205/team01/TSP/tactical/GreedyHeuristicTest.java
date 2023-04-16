package com.info6205.team01.TSP.tactical;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.UndirectedEdge;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.util.LoadData;
import com.info6205.team01.TSP.util.Preprocessing;
import com.info6205.team01.TSP.visualization.AlgorithmVisualization;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GreedyHeuristicTest {

    @Test
    public void pathTest1() {
        Preprocessing preprocessing = new Preprocessing();
        List<Node> nodes = preprocessing.getNodes();
        GreedyHeuristic gh = new GreedyHeuristic(nodes);
        gh.findMinRoute();
    }

    @Test
    public void pathTest2() {
        Preprocessing preprocessing = new Preprocessing();
        List<Node> nodes = preprocessing.getNodes();
        GreedyHeuristic gh = new GreedyHeuristic(nodes);
        gh.getGos();
    }

    @Test
    public void pathTest3() {
        Preprocessing preprocessing = new Preprocessing();
        List<Node> nodes = preprocessing.getNodes();
        GreedyHeuristic gh = new GreedyHeuristic(nodes);
        gh.getTour();
    }

    @Test
    public void pathTest4() {
        Preprocessing preprocessing = new Preprocessing();
        List<Node> nodes = preprocessing.getNodes();
        GreedyHeuristic gh = new GreedyHeuristic(nodes);
        gh.getTour();
        gh.getGos();
        gh.findMinRoute();
    }

}
