package com.info6205.team01.TSP.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MST {
    class MyEdge {
        int node1;
        int node2;
        double distance;

        public MyEdge(int node1, int node2, double distance) {
            this.node1 = node1;
            this.node2 = node2;
            this.distance = distance;
        }
    }

    public double minCostConnectPoints(double[][] points) {
        List<MyEdge> edges = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
//                double dist = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                double dist = Tools.distance(points[i][1], points[i][0], points[j][1], points[j][0]);
                edges.add(new MyEdge(i,j,dist));
            }
        }
        Collections.sort(edges, Comparator.comparingDouble(e -> e.distance));
        DisjointSet disjointSet = new DisjointSet(points.length);
        double minCost = 0;
        int connectCount = 0;
        for (MyEdge edge : edges) {
            if (disjointSet.connect(edge.node1, edge.node2)) {
                minCost += edge.distance;
                connectCount++;
                if (connectCount == points.length - 1) {
                    break;
                }
            }
        }
        return minCost;
    }

    public static void main(String[] args) {
        MST mst = new MST();
//        double[][] points = {{0,0},{2,2},{3,10},{5,2},{7,0}};
//        System.out.println(mst.minCostConnectPoints(points));
    }
}
