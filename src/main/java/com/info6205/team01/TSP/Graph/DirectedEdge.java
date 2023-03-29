package com.info6205.team01.TSP.Graph;

public class DirectedEdge implements Comparable<DirectedEdge> {
    private Node from;
    private Node to;
    private double weight;

    public DirectedEdge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.weight = Node.getDistance(from, to);
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(DirectedEdge other) {
        return Double.compare(weight, other.weight);
    }

    @Override
    public String toString() {
        return "DirectedEdge{" +
                "From=" + from.getId() +
                ", To=" + to.getId() +
                '}';
    }
}
