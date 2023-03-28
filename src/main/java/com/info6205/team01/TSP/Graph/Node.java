package com.info6205.team01.TSP.Graph;

import com.info6205.team01.TSP.util.Tools;

public class Node {
    public String id;
    public double longitude;
    public double latitude;

    public Node(String id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public static double getDistance(Node node1, Node node2) {
        return Tools.distance(node1.latitude, node1.longitude, node2.latitude, node2.longitude);
    }
}
