package com.info6205.team01.TSP.visualization;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.UndirectedEdge;
import org.graphstream.graph.Edge;

public class GraphOperation {

    public enum Action {
        ADD,
        REMOVE,
        SETLAYOUT
    }

    public enum Layout {
        HIGHLIGHT,
        UNHIGHLIGHT
    }

    Action action;
    Layout layout = Layout.UNHIGHLIGHT;
    Node node1;
    Node node2;
    UndirectedEdge edge;

    public GraphOperation setLayout(Layout layout) {
        this.layout = layout;
        return this;
    }

    public UndirectedEdge getEdge() {
        return edge;
    }

    public Layout getLayout() {
        return layout;
    }

    public Action getAction() {
        return action;
    }

    private GraphOperation(Node node1, Node node2, Action act) {
        this.node1 = node1;
        this.node2 = node2;
        this.action = act;
        this.edge = new UndirectedEdge(node1, node2);
    }

    static public GraphOperation addEdge(Node node1, Node node2) {
        return new GraphOperation(node1, node2, Action.ADD);
    }

    static public GraphOperation removeEdge(Node node1, Node node2) {
        return new GraphOperation(node1, node2, Action.REMOVE);
    }

    static public GraphOperation highlightEdge(Node node1, Node node2) {
        return new GraphOperation(node1, node2, Action.SETLAYOUT).setLayout(Layout.HIGHLIGHT);
    }

    static public GraphOperation unhighlightEdge(Node node1, Node node2) {
        return new GraphOperation(node1, node2, Action.SETLAYOUT).setLayout(Layout.UNHIGHLIGHT);
    }

    static public GraphOperation addEdge(UndirectedEdge edge) {
        return GraphOperation.addEdge(edge.getNodes()[0], edge.getNodes()[1]);
    }

    static public GraphOperation removeEdge(UndirectedEdge edge) {
        return new GraphOperation(edge.getNodes()[0], edge.getNodes()[1], Action.REMOVE);
    }

    static public GraphOperation highlightEdge(UndirectedEdge edge) {
        return new GraphOperation(edge.getNodes()[0], edge.getNodes()[1], Action.SETLAYOUT).setLayout(Layout.HIGHLIGHT);
    }

    static public GraphOperation unhighlightEdge(UndirectedEdge edge) {
        return new GraphOperation(edge.getNodes()[0], edge.getNodes()[1], Action.SETLAYOUT).setLayout(Layout.UNHIGHLIGHT);
    }

    @Override
    public String toString() {
        return "GraphOperation{" +
                "action=" + action +
                ", layout=" + layout +
                ", node1=" + node1.getId() +
                ", node2=" + node2.getId() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof GraphOperation)) {
            return false;
        }
        GraphOperation go = (GraphOperation) obj;
        boolean res = true;
        if (!go.getEdge().equals(this.getEdge())) {
            return false;
        }
        if (this.getAction() != go.getAction()) {
            return false;
        }
        if (this.getLayout() != go.getLayout()) {
            return false;
        }
        return true;


    }
}
