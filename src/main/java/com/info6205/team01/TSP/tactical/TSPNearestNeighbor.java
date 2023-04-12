package com.info6205.team01.TSP.tactical;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.visualization.GraphOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TSPNearestNeighbor {
    private int length;
    private Set<String> visited;
    private double[][] adjacencyMatrix;

    List<Node> tour;
    List<Node> nodes;
    private double minDistance = 0;
    List<GraphOperation> gos = new ArrayList<>();

    public TSPNearestNeighbor() {
        tour = new ArrayList<>();
        this.adjacencyMatrix = LoadCSVData.data.adjacencyMatrix;
        length = LoadCSVData.data.length;
        visited = new HashSet<>();
        nodes = LoadCSVData.data.nodes;
    }

    public void findShortestPath() {
        visited.add(nodes.get(0).getId());
        tour.add(nodes.get(0));
        int currentPos = 0;
        int nearestNeighbor = 0;
        boolean minFlag = false;

        for (int i = 1; i < length; i++) {
            double currentMin = Double.MAX_VALUE;
            for (int j = 1; j < length; j++) {
                if (adjacencyMatrix[currentPos][j] != 0 && !visited.contains(nodes.get(j).getId())) {
                    if (adjacencyMatrix[currentPos][j] < currentMin) {
                        currentMin = adjacencyMatrix[currentPos][j];
                        nearestNeighbor = j;
                        minFlag = true;
                    }
                }
            }

            if (minFlag == true) {
                visited.add(nodes.get(nearestNeighbor).getId());
                tour.add(nodes.get(nearestNeighbor));
                minFlag = false;
                currentPos = nearestNeighbor;
            }
            minDistance += currentMin;
        }

        minDistance += adjacencyMatrix[currentPos][0];

        System.out.println("Nearest Neighbor Heuristic Algorithm Result: ");
//        System.out.println("Tour: " + tour);
        System.out.println("Shortest Distance: " + minDistance);
    }

    public List<Node> getTour() {
        return tour;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public List<GraphOperation> getGos() {
        return gos;
    }

    public static void main(String[] args) {
//        double[][] adjacencyMatrix = {{0, 10, 15, 20},
//                {10, 0, 35, 25},
//                {15, 35, 0, 30},
//                {20, 25, 30, 0}};
//        TSPNearestNeighbor tsp = new TSPNearestNeighbor();
//        List<Integer> tour = new ArrayList<>();
//        double distance = tsp.findShortestPath(tour);
//
//        System.out.println("Tour: " + tour);
//        System.out.println("Shortest Distance: " + distance);
    }
}
