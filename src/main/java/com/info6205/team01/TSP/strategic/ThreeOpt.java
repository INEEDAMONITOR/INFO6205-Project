package com.info6205.team01.TSP.strategic;

public class ThreeOpt {
    public double optimize(int[] tour, double[][] distances) {
        int n = tour.length;
        boolean improvement = true;
        double minDistance = calculateDistance(tour, distances);

        while (improvement) {
            improvement = false;

            for (int i = 1; i < n - 2; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    for (int k = j + 1; k < n; k++) {
                        // evaluate all possible 3-opt swaps
                        int[] newTour = threeOptSwap(tour, i, j, k, n);
                        // calculate new distance
                        double newDist = calculateDistance(newTour, distances);

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

        return minDistance;
    }

    // helper method for performing a 3-opt swap
    private int[] threeOptSwap(int[] tour, int i, int j, int k, int n) {
        int[] newTour = new int[n];
        int index = 0;
        // take all the cities from beginning to i-1
        for (int x = 0; x < i; x++) {
            newTour[index++] = tour[x];
        }
        // take cities from i to j in the same order
        for (int x = i; x <= j; x++) {
            newTour[index++] = tour[x];
        }
        // take cities from k to n-1 in the same order
        for (int x = k; x < n; x++) {
            newTour[index++] = tour[x];
        }
        // take cities from j+1 to k-1 in reverse order
        for (int x = j + 1; x < k; x++) {
            newTour[index++] = tour[k + j - x];
        }
        return newTour;
    }

    // helper method for calculating the distance of a tour
    private double calculateDistance(int[] tour, double[][] distances) {
        double distance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            distance += distances[tour[i]][tour[i+1]];
        }
        distance += distances[tour[tour.length-1]][tour[0]];
        return distance;
    }
}
