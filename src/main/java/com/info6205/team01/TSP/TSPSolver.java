package com.info6205.team01.TSP;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.strategic.ThreeOpt;
import com.info6205.team01.TSP.strategic.TwoOpt;
import com.info6205.team01.TSP.tactical.TSPNearestNeighbor;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.util.LoadData;
import com.info6205.team01.TSP.util.MST;
import com.info6205.team01.TSP.visualization.AlgorithmVisualization;
import com.info6205.team01.TSP.visualization.GraphOperation;

import java.util.ArrayList;
import java.util.List;

public class TSPSolver {

    LoadData loadCSVData;

    public void solve() throws Exception {
        // data transform
        loadCSVData = LoadCSVData.data;
        double[][] adjacencyMatrix = loadCSVData.adjacencyMatrix;
        double[][] cityCoordinates = loadCSVData.coordination;
        // get lower boundary
        MST mst = new MST();
        double minCost = mst.minCostConnectPoints(cityCoordinates);
        System.out.println("Minimum cost: " + minCost);
        // use Nearest Neighbor to solve problem
        TSPNearestNeighbor tspNearestNeighbor = new TSPNearestNeighbor(loadCSVData);
        tspNearestNeighbor.findShortestPath();
        visualization(loadCSVData.nodes, tspNearestNeighbor.getGos(), null, 5);
        // optimize it using 2-opt
        TwoOpt twoOpt = optimizeWithTwoOpt(tspNearestNeighbor.getTour());
        visualization(loadCSVData.nodes, twoOpt.getGos(), tspNearestNeighbor.getGos(), 5);
        // optimize it using 3-opt
        ThreeOpt threeOpt = optimizeWithThreeOpt(tspNearestNeighbor.getTour());
        visualization(loadCSVData.nodes, threeOpt.getGos(), tspNearestNeighbor.getGos(), 5);
        System.out.println("hahahah");
    }

    /*private double solveWithNearestNeighbor(List<Integer> tourNN, double[][] adjacencyMatrix) {
        TSPNearestNeighbor tsp = new TSPNearestNeighbor(adjacencyMatrix);
        double distanceNN = tsp.findShortestPath(tourNN);
        System.out.println("NearestNeighbor - Tour: " + tourNN);
        System.out.println("NearestNeighbor - Shortest Distance: " + distanceNN);
        return distanceNN;
    }*/

    private TwoOpt optimizeWithTwoOpt(List<Node> tour) {
        TwoOpt twoOpt = new TwoOpt(tour);
        twoOpt.optimize();
        return twoOpt;
    }

    private ThreeOpt optimizeWithThreeOpt(List<Node> tour) {
        ThreeOpt threeOpt = new ThreeOpt(tour);
        threeOpt.optimize();
        return threeOpt;
    }

    private void visualization(List<Node> nodes, List<GraphOperation> gos, List<GraphOperation> oldGos, int interval) {
        // Build av
        AlgorithmVisualization av = new AlgorithmVisualization(nodes, gos, oldGos);
        // You can set sleep time
        // default: 500
        av.setSleepTime(interval);
        av.showResult();
    }

    public static void main(String[] args) throws Exception {
        TSPSolver tspSolver = new TSPSolver();
        tspSolver.solve();
    }

}
