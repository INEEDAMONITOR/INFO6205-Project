package com.info6205.team01.TSP.tactical;

import java.util.ArrayList;
import java.util.List;

public class TSPNearestNeighbor {
    private int numberOfNodes;
    private boolean[] visited;
    private double[][] adjacencyMatrix;

    public TSPNearestNeighbor(double[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        numberOfNodes = adjacencyMatrix[0].length;
        visited = new boolean[numberOfNodes];
    }

    public double findShortestPath(List<Integer> tour) {
        visited[0] = true;
        tour.add(0);
        int currentPos = 0;
        int nearestNeighbor = 0;
        double shortestDistance = 0;
        boolean minFlag = false;

        for (int i = 1; i < numberOfNodes; i++) {
            double currentMin = Double.MAX_VALUE;
            for (int j = 1; j < numberOfNodes; j++) {
                if (adjacencyMatrix[currentPos][j] != 0 && visited[j] == false) {
                    if (adjacencyMatrix[currentPos][j] < currentMin) {
                        currentMin = adjacencyMatrix[currentPos][j];
                        nearestNeighbor = j;
                        minFlag = true;
                    }
                }
            }

            if (minFlag == true) {
                visited[nearestNeighbor] = true;
                tour.add(nearestNeighbor);
                minFlag = false;
                currentPos = nearestNeighbor;
            }
            shortestDistance += currentMin;
        }

        shortestDistance += adjacencyMatrix[currentPos][0];

        System.out.println("Nearest Neighbor Heuristic Algorithm Result: ");
        System.out.println("Tour: " + tour);
        System.out.println("Shortest Distance: " + shortestDistance);
        return shortestDistance;
    }

    public static void main(String[] args) {
        double[][] adjacencyMatrix = {{0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}};
        TSPNearestNeighbor tsp = new TSPNearestNeighbor(adjacencyMatrix);
        List<Integer> tour = new ArrayList<>();
        double distance = tsp.findShortestPath(tour);

        System.out.println("Tour: " + tour);
        System.out.println("Shortest Distance: " + distance);
    }
}
