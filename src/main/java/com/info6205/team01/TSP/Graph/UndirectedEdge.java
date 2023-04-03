package com.info6205.team01.TSP.Graph;

public class UndirectedEdge {
    private Node node1;
    private Node node2;
    private double weight;

    public UndirectedEdge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = Node.getDistance(node1, node2);
    }

    public Node[] getNodes() {
        return new Node[]{node1, node2};
    }

    public double getWeight() {
        return weight;
    }

    public Node containsNodes(Node node) {
        if (node1 == node) {
            return node2;
        }
        if (node2 == node) {
            return node1;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof UndirectedEdge)) {
            return false;
        }
        UndirectedEdge edge = (UndirectedEdge) obj;
        Node[] nodes = edge.getNodes();
        if (this.node1 == nodes[0] && this.node2 == nodes[1]) {
            return true;
        }
        if (this.node1 == nodes[1] && this.node2 == nodes[0]) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "UndirectedEdge{" +
                "node1=" + node1.getId() +
                ", node2=" + node2.getId() +
                '}';
    }
}
