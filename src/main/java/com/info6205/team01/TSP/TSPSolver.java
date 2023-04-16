package com.info6205.team01.TSP;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.strategic.RandomSwapping;
import com.info6205.team01.TSP.strategic.ThreeOpt;
import com.info6205.team01.TSP.strategic.TwoOpt;
import com.info6205.team01.TSP.tactical.AntColonyOptimization;
import com.info6205.team01.TSP.tactical.GreedyHeuristic;
import com.info6205.team01.TSP.tactical.TSPNearestNeighbor;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.util.LoadDataImpl;
import com.info6205.team01.TSP.util.MST;
import com.info6205.team01.TSP.visualization.AlgorithmVisualization;
import com.info6205.team01.TSP.visualization.GraphOperation;

import java.util.ArrayList;
import java.util.List;

public class TSPSolver {

    LoadDataImpl loadCSVData;
    Boolean hasVisual = false;

    public void solve() throws Exception {
        // data transform
        loadCSVData = new LoadDataImpl();
        double[][] adjacencyMatrix = loadCSVData.adjacencyMatrix;
        double[][] cityCoordinates = loadCSVData.coordination;
        // get lower boundary
        MST mst = new MST();
        double minCost = mst.minCostConnectPoints(cityCoordinates);
        System.out.println("Minimum cost: " + minCost);

        // ========== NN ============
        TSPNearestNeighbor tspNearestNeighbor = new TSPNearestNeighbor(new LoadDataImpl());
        tspNearestNeighbor.findShortestPath();
        if (hasVisual)
            visualization(loadCSVData.nodes, tspNearestNeighbor.getGos(), null, 5);
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Nearest Neighbor", tspNearestNeighbor.getMinDistance(), (tspNearestNeighbor.getMinDistance() - minCost) / minCost * 100);
//        System.out.printf("%s dis: %d, ratio: %f", );
        // NN + opt2
        TwoOpt nnTwoOpt = optimizeWithTwoOpt(tspNearestNeighbor.getTour());
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Nearest Neighbor + opt2", nnTwoOpt.getMinDistance(), (nnTwoOpt.getMinDistance() - minCost) / minCost * 100);
        if (hasVisual)
            visualization(loadCSVData.nodes, nnTwoOpt.getGos(), tspNearestNeighbor.getGos(), 5);
        // NN + opt3
        ThreeOpt nnThreeOpt = optimizeWithThreeOpt(tspNearestNeighbor.getTour());
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Nearest Neighbor + opt3", nnThreeOpt.getMinDistance(), (nnThreeOpt.getMinDistance() - minCost) / minCost * 100);
        if (hasVisual)
            visualization(loadCSVData.nodes, nnThreeOpt.getGos(), tspNearestNeighbor.getGos(), 5);
        // NN + randomSwap
        RandomSwapping nnRs = new RandomSwapping(tspNearestNeighbor.getTour());
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Nearest Neighbor + random swap", nnRs.getMinDistance(), (nnRs.getMinDistance() - minCost) / minCost * 100);
        if (hasVisual)
            visualization(loadCSVData.nodes, nnRs.getGos(), tspNearestNeighbor.getGos(), 5);

        // ============ GH ===========
        loadCSVData = new LoadDataImpl();
        GreedyHeuristic gh = new GreedyHeuristic(loadCSVData.nodes);
        List<Node> tourOfGH = gh.getTour();
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Greedy Heuristic", gh.getMinDistance(), (gh.getMinDistance() - minCost) / minCost * 100);
        if (hasVisual)
            visualization(loadCSVData.nodes, gh.getGos(), null, 5);
        // GH + opt2
        TwoOpt ghTwoOpt = optimizeWithTwoOpt(new ArrayList<>(tourOfGH));
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Greedy Heuristic + opt2", ghTwoOpt.getMinDistance(), (ghTwoOpt.getMinDistance() - minCost) / minCost * 100);
        if (hasVisual)
            visualization(loadCSVData.nodes, ghTwoOpt.getGos(), gh.getGos(), 5);
        // GH + opt3
        ThreeOpt ghThreeOpt = optimizeWithThreeOpt(new ArrayList<>(tourOfGH));
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Greedy Heuristic + opt3", nnThreeOpt.getMinDistance(), (nnThreeOpt.getMinDistance() - minCost) / minCost * 100);
        if (hasVisual)
            visualization(loadCSVData.nodes, ghThreeOpt.getGos(), gh.getGos(), 5);
        // GH + randomSwap
        RandomSwapping ghRs = new RandomSwapping(new ArrayList<>(tourOfGH));
        System.out.printf("%s dis: %.2f, ratio: %.2f%%\n", "Greedy Heuristic + random swap", nnRs.getMinDistance(), (nnRs.getMinDistance() - minCost) / minCost * 100);
        if (hasVisual)
            visualization(loadCSVData.nodes, ghRs.getGos(), gh.getGos(), 5);

        // ========== Ant ==========
        loadCSVData = new LoadDataImpl();
        AntColonyOptimization aco = new AntColonyOptimization(loadCSVData.nodes, 10, 100, 0, 1, 5);
        aco.run();


    }


    private TwoOpt optimizeWithTwoOpt(List<Node> tour) {
        TwoOpt twoOpt = new TwoOpt(tour, LoadCSVData.data);
        twoOpt.optimize();
        return twoOpt;
    }

    private ThreeOpt optimizeWithThreeOpt(List<Node> tour) {
        ThreeOpt threeOpt = new ThreeOpt(tour, LoadCSVData.data);
        threeOpt.optimize();
        return threeOpt;
    }

    private void visualization(List<Node> nodes, List<GraphOperation> gos, List<GraphOperation> oldGos, int interval) {
        AlgorithmVisualization av;
        // Build av
        if (oldGos != null)
            av = new AlgorithmVisualization(nodes, gos, oldGos);
        else
            av = new AlgorithmVisualization(nodes, gos);
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
