package com.info6205.team01.TSP.strategic;

public class TwoOpt {
    public double optimize(int[] tour, double[][] distances) {
        int n = tour.length;
        int[] newTour = null;
        double minDistance = calculateDistance(tour, distances);
        boolean improvement = true;

        while (improvement) {
            improvement = false;

            for (int i = 1; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    // 2-opt swap
                    newTour = twoOptSwap(tour, i, j);
                    // check new distance
                    double newDist = calculateDistance(newTour, distances);

                    if (newDist < minDistance) {
                        // new tour is better, keep it
                        tour = newTour;
                        minDistance = newDist;
                        improvement = true;
                    }
                }
            }
        }

        return minDistance;
    }

    // helper method for performing a 2-opt swap
    private int[] twoOptSwap(int[] tour, int i, int j) {
        int[] newTour = new int[tour.length];
        int index = 0;
        // take all the cities from beginning to i-1
        for (int k = 0; k < i; k++) {
            newTour[index++] = tour[k];
        }
        // take cities from i to j in reverse order
        for (int k = j; k >= i; k--) {
            newTour[index++] = tour[k];
        }
        // take cities from j+1 to end
        for (int k = j + 1; k < tour.length; k++) {
            newTour[index++] = tour[k];
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
