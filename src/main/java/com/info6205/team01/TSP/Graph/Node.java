package com.info6205.team01.TSP.Graph;

import com.info6205.team01.TSP.util.Tools;

public class Node {
    private String id;
    private double longitude;
    private double latitude;
    private int degree = 0;

    public int getDegree() {
        return degree;
    }

    public void increseDegree() {
        this.degree++;
    }

    public String getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return this.id + " " + this.longitude + " " + this.latitude;
    }

    public Node(String id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static double getDistance(Node node1, Node node2) {
        return Tools.distance(node1.latitude, node1.longitude, node2.getLatitude(), node2.getLongitude());
    }
}
