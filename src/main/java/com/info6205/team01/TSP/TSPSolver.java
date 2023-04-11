package com.info6205.team01.TSP;

import com.info6205.team01.TSP.strategic.ThreeOpt;
import com.info6205.team01.TSP.strategic.TwoOpt;
import com.info6205.team01.TSP.tactical.TSPNearestNeighbor;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.util.MST;

import java.util.ArrayList;
import java.util.List;

public class TSPSolver {

    LoadCSVData loadCSVData;

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
        List<Integer> tourNN = new ArrayList<>();
        double distanceNN = solveWithNearestNeighbor(tourNN, adjacencyMatrix);
        // optimize it using 2-opt
        optimizeWithTwoOpt(tourNN, adjacencyMatrix);
        // optimize it using 3-opt
        optimizeWithThreeOpt(tourNN, adjacencyMatrix);
    }

    private double solveWithNearestNeighbor(List<Integer> tourNN, double[][] adjacencyMatrix) {
        TSPNearestNeighbor tsp = new TSPNearestNeighbor(adjacencyMatrix);
        double distanceNN = tsp.findShortestPath(tourNN);
        System.out.println("NearestNeighbor - Tour: " + tourNN);
        System.out.println("NearestNeighbor - Shortest Distance: " + distanceNN);
        return distanceNN;
    }

    private void optimizeWithTwoOpt(List<Integer> tour, double[][] adjacencyMatrix) {
        int[] arr1 = tour.stream().mapToInt(i -> i).toArray();
        TwoOpt twoOpt = new TwoOpt();
        double newDistance = twoOpt.optimize(arr1, adjacencyMatrix);
        System.out.print("Two Opt - Tour: ");
        for (int e : arr1) {
            System.out.print(e + ", ");
        }
        System.out.println();
        System.out.println("Two Opt - Shortest Distance: " + newDistance);

    }

    private void optimizeWithThreeOpt(List<Integer> tour, double[][] adjacencyMatrix) {
        int[] arr1 = tour.stream().mapToInt(i -> i).toArray();
        ThreeOpt threeOpt = new ThreeOpt();
        double newDistance = threeOpt.optimize(arr1, adjacencyMatrix);
        System.out.print("Three Opt - Tour: ");
        for (int e : arr1) {
            System.out.print(e + ", ");
        }
        System.out.println();
        System.out.println("Three Opt - Shortest Distance: " + newDistance);

    }

    public static void main(String[] args) throws Exception {
        TSPSolver tspSolver = new TSPSolver();
        tspSolver.solve();
    }

}
