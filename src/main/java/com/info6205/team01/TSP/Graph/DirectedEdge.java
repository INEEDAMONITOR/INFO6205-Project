package com.info6205.team01.TSP.Graph;

public class DirectedEdge {
    private Node from;
    private Node to;
    private double weight;

    public DirectedEdge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.weight = Node.getDistance(from, to);
    }

    public DirectedEdge(Node from, Node to, double w) {
        this.from = from;
        this.to = to;
        this.weight = w;
    }

    public DirectedEdge(DirectedEdge rhs) {
        this(rhs.getFrom(), rhs.getTo());
    }

    public Node getFrom() { return from;}

    public Node getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DirectedEdge)) {
            return false;
        }

        DirectedEdge edge = (DirectedEdge) obj;
        Node from = edge.getFrom(), to = edge.getTo();
        return this.from == from && this.to == to;
    }

    @Override
    public String toString() {
        return "DirectedEdge{" +
                "From=" + from.getId() +
                ", To=" + to.getId() +
                '}';
    }

    public UndirectedEdge toUndirectedEdger() {
        return new UndirectedEdge(this.from, this.to);
    }
}
