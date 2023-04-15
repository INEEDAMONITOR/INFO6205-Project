package com.info6205.team01.TSP.strategic;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.util.LoadCSVData;
import com.info6205.team01.TSP.util.LoadData;
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
    LoadData loadData;

    public ThreeOpt(List<Node> nodes, LoadData loadData) {
        this.loadData = loadData;
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

            for (int i = 1; i < n; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    for (int k = j + 1; k < n; k++) {
                        // evaluate all possible 3-opt swaps
                        newTour = threeOptSwap(tour, i, j, k, n);
                        // calculate new distance
                        double newDist = calculateDistance(newTour);

                        if (newDist < minDistance) {
                            // new tour is better, keep it
                            gos.add(GraphOperation.removeEdge(tour.get(i-1), tour.get(i)));
                            gos.add(GraphOperation.removeEdge(tour.get(j-1), tour.get(j)));
                            gos.add(GraphOperation.removeEdge(tour.get(k-1), tour.get(k)));
                            gos.add(GraphOperation.removeEdge(tour.get(n-1), tour.get(0)));
                            gos.add(GraphOperation.addEdge(tour.get(i-1), tour.get(k)));
                            gos.add(GraphOperation.addEdge(tour.get(n-1), tour.get(j)));
                            gos.add(GraphOperation.addEdge(tour.get(k-1), tour.get(i)));
                            gos.add(GraphOperation.addEdge(tour.get(j-1), tour.get(0)));
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
        List<Node> newTour = new ArrayList<>(tour.subList(0, i));
        newTour.addAll(tour.subList(k, length));
        newTour.addAll(tour.subList(j, k));
        newTour.addAll(tour.subList(i, j));
        return newTour;
    }

    // helper method for calculating the distance of a tour
    private double calculateDistance(List<Node> tour) {
        Map<String, Integer> IDToIndex = loadData.IDToIndex;
        double[][] distances = loadData.adjacencyMatrix;

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

    public List<GraphOperation> getGos() {
        return gos;
    }

    public List<Node> getTour() {
        return tour;
    }
}
