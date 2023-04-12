package com.info6205.team01.TSP.strategic;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.visualization.GraphOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ThreeOpt {

    List<Node> tour;
    int length;
    List<GraphOperation> gos = new ArrayList<>();
    double minDistance = 0;

    public ThreeOpt(List<Node> nodes) {
        tour = new ArrayList<>(nodes);
        length = tour.size();
        minDistance = calculateDistance(tour);

    }
    public void optimize() {
        List<Node> newTour;
        int n = tour.size();
        boolean improvement = true;

        while (improvement) {
            improvement = false;

            for (int i = 1; i < n - 2; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    for (int k = j + 1; k < n; k++) {
                        // evaluate all possible 3-opt swaps
                        newTour = threeOptSwap(tour, i, j, k, n);
                        // calculate new distance
                        double newDist = calculateDistance(newTour);

                        if (newDist < minDistance) {
                            // new tour is better, keep it
                            tour = newTour;
                            improvement = true;
                            minDistance = newDist;
                        }
                    }
                }
            }
        }
    }

    // helper method for performing a 3-opt swap
    private List<Node> threeOptSwap(List<Node> tour, int i, int j, int k, int n) {
        Node[] newTour = new Node[length];
        int index = 0;
        // take all the cities from beginning to i-1
        for (int x = 0; x < i; x++) {
            newTour[index++] = tour.get(x);
        }
        // take cities from i to j in the same order
        for (int x = i; x <= j; x++) {
            newTour[index++] = tour.get(x);
        }
        // take cities from k to n-1 in the same order
        for (int x = k; x < n; x++) {
            newTour[index++] = tour.get(x);
        }
        // take cities from j+1 to k-1 in reverse order
        for (int x = j + 1; x < k; x++) {
            newTour[index++] = tour.get(k + j - x);
        }
        List<Node> nList = new ArrayList<>(Arrays.asList(newTour));
        return nList;
    }

    // helper method for calculating the distance of a tour
    private double calculateDistance(List<Node> tour) {
        Map<String, Integer> IDToIndex = LoadCSVData.data.IDToIndex;
        double[][] distances = LoadCSVData.data.adjacencyMatrix;

        double distance = 0;
        for (int i = 0; i < length - 1; i++) {
            int index1 = IDToIndex.get(tour.get(i).getId());
            int index2 = IDToIndex.get(tour.get(i+1).getId());
            distance += distances[index1][index2];
        }
        int lastIndex = IDToIndex.get(tour.get(length-1).getId());
        int firstIndex = IDToIndex.get(tour.get(0).getId());;
        distance += distances[lastIndex][firstIndex];

        return distance;
    }
}
