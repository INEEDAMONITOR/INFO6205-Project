package com.info6205.team01.TSP.strategic;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.tactical.GreedyHeuristic;
import com.info6205.team01.TSP.util.Preprocessing;
import com.info6205.team01.TSP.visualization.AlgorithmVisualization;
import com.info6205.team01.TSP.visualization.GraphOperation;
import com.info6205.team01.TSP.visualization.TestVis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSwapping {

    public static void main(String[] args) {
        Preprocessing preprocessing = new Preprocessing();
        List<Node> nodes = preprocessing.getNodes().subList(0, 100);
        GreedyHeuristic gh = new GreedyHeuristic(nodes);

        List<Node> tour = gh.getMinNodes();
        double totalDis = 0;
        totalDis += Node.getDistance(tour.get(0), tour.get(tour.size() - 1));
        for (int i = 1; i < tour.size(); i++) {
            Node n1 = tour.get(i);
            Node n2 = tour.get(i - 1);
            totalDis += Node.getDistance(n1, n2);
        }
        System.out.println("Total Dis: " + totalDis);
        RandomSwapping rs = new RandomSwapping(tour);
        TestVis tv = new TestVis();
//        tv.showResult(tour);
//        tv.showResult(rs.run());
        List<GraphOperation> tourGos = new ArrayList<>();
        for (int i = 1; i < tour.size(); i++) {
            tourGos.add(GraphOperation.addEdge(tour.get(i - 1), tour.get(i)));
        }
        AlgorithmVisualization av = new AlgorithmVisualization(nodes, rs.getGos(), tourGos);
        av.setSleepTime(500);
        av.showResult();
//        rs.run();z
    }

    List<Node> nodes;

    public RandomSwapping(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> run() {
        return run(nodes.size() * 200000, false, null);
    }

    public List<Node> run(int repeatTime, boolean getGos, List<GraphOperation> gos) {
        List<Node> randomSwappedNodes = new ArrayList<>(this.nodes);
        int length = randomSwappedNodes.size();
        Random random = new Random();
        while (repeatTime >= 0) {
            double totalDisPrev = 0;
            totalDisPrev += Node.getDistance(randomSwappedNodes.get(0), randomSwappedNodes.get(length - 1));
            for (int i = 1; i < randomSwappedNodes.size(); i++) {
                Node n1 = randomSwappedNodes.get(i);
                Node n2 = randomSwappedNodes.get(i - 1);
                totalDisPrev += Node.getDistance(n1, n2);
            }
            repeatTime--;
            int index1 = random.nextInt(length);
            int index2 = random.nextInt(length);
            Node prev1 = randomSwappedNodes.get((index1 - 1 + length) % length);
            Node node1 = randomSwappedNodes.get(index1);
            Node next1 = randomSwappedNodes.get((index1 + 1) % length);
            Node prev2 = randomSwappedNodes.get((index2 - 1 + length) % length);
            Node node2 = randomSwappedNodes.get(index2);
            Node next2 = randomSwappedNodes.get((index2 + 1) % length);

            double prevDis = 0;
            double nextDis = 0;
            if (next1 == node2) {
                prevDis = Node.getDistance(prev1, node1) + Node.getDistance(node2, next2);
                nextDis = Node.getDistance(prev1, node2) + Node.getDistance(node1, next2);
            } else if (next2 == node1) {
                prevDis = Node.getDistance(prev2, node2) + Node.getDistance(node1, next1);
                nextDis = Node.getDistance(prev2, node1) + Node.getDistance(node2, next1);
            } else if (index1 < index2) {
                prevDis = Node.getDistance(prev1, node1) + Node.getDistance(node1, next1) + Node.getDistance(prev2, node2) + Node.getDistance(node2, prev2);
                nextDis = Node.getDistance(prev1, node2) + Node.getDistance(node2, next1) + Node.getDistance(prev2, node1) + Node.getDistance(node1, next2);
            } else {
                prevDis = Node.getDistance(prev1, node1) + Node.getDistance(node1, next1) + Node.getDistance(prev2, node2) + Node.getDistance(node2, prev2);
                nextDis = Node.getDistance(prev1, node2) + Node.getDistance(node2, next1) + Node.getDistance(prev2, node1) + Node.getDistance(node1, next2);
            }

//            if (getGos) {
//                gos.add(GraphOperation.removeEdge(prev1, node1));
//                gos.add(GraphOperation.removeEdge(next1, node1));
//                gos.add(GraphOperation.removeEdge(prev2, node2));
//                gos.add(GraphOperation.removeEdge(next2, node2));
//
//                gos.add(GraphOperation.addEdge(prev1, node2).setLayout(GraphOperation.Layout.HIGHLIGHT));
//                gos.add(GraphOperation.addEdge(next1, node2).setLayout(GraphOperation.Layout.HIGHLIGHT));
//                gos.add(GraphOperation.addEdge(prev2, node1).setLayout(GraphOperation.Layout.HIGHLIGHT));
//                gos.add(GraphOperation.addEdge(next2, node1).setLayout(GraphOperation.Layout.HIGHLIGHT));
//            }

            if (prevDis > nextDis) {
                double totalDisNext = 0;
                randomSwappedNodes.set(index1, node2);
                randomSwappedNodes.set(index2, node1);

                totalDisNext += Node.getDistance(randomSwappedNodes.get(0), randomSwappedNodes.get(length - 1));
                for (int i = 1; i < randomSwappedNodes.size(); i++) {
                    Node n1 = randomSwappedNodes.get(i);
                    Node n2 = randomSwappedNodes.get(i - 1);
                    totalDisNext += Node.getDistance(n1, n2);
                }
                if (totalDisNext >= totalDisPrev) {
                    randomSwappedNodes.set(index1, node1);
                    randomSwappedNodes.set(index2, node2);
//                if (getGos) {
//                    gos.add(GraphOperation.removeEdge(prev1, node2));
//                    gos.add(GraphOperation.removeEdge(next1, node2));
//                    gos.add(GraphOperation.removeEdge(prev2, node1));
//                    gos.add(GraphOperation.removeEdge(next2, node1));
//
//                    gos.add(GraphOperation.addEdge(prev1, node1));
//                    gos.add(GraphOperation.addEdge(next1, node1));
//                    gos.add(GraphOperation.addEdge(prev2, node2));
//                    gos.add(GraphOperation.addEdge(next2, node2));
//                }
                } else {
                    if (getGos) {
                        if (next1 != node2 && next2 != node1) {
                            gos.add(GraphOperation.removeEdge(prev1, node1));
                            gos.add(GraphOperation.removeEdge(next1, node1));
                            gos.add(GraphOperation.removeEdge(prev2, node2));
                            gos.add(GraphOperation.removeEdge(next2, node2));

                            gos.add(GraphOperation.addEdge(prev1, node2).setLayout(GraphOperation.Layout.HIGHLIGHT));
                            gos.add(GraphOperation.addEdge(node2, next1).setLayout(GraphOperation.Layout.HIGHLIGHT));
                            gos.add(GraphOperation.addEdge(prev2, node1).setLayout(GraphOperation.Layout.HIGHLIGHT));
                            gos.add(GraphOperation.addEdge(node1, next2).setLayout(GraphOperation.Layout.HIGHLIGHT));

                            gos.add(GraphOperation.unhighlightEdge(prev1, node2));
                            gos.add(GraphOperation.unhighlightEdge(node2, next1));
                            gos.add(GraphOperation.unhighlightEdge(prev2, node1));
                            gos.add(GraphOperation.unhighlightEdge(node1, next2));
                        } else {
                            if (next1 == node2) {
                                gos.add(GraphOperation.removeEdge(prev1, node1));
                                gos.add(GraphOperation.removeEdge(node1, node2));
                                gos.add(GraphOperation.removeEdge(node2, next2));

                                gos.add(GraphOperation.addEdge(prev1, node2).setLayout(GraphOperation.Layout.HIGHLIGHT));
                                gos.add(GraphOperation.addEdge(node1, node2).setLayout(GraphOperation.Layout.HIGHLIGHT));
                                gos.add(GraphOperation.addEdge(node1, next2).setLayout(GraphOperation.Layout.HIGHLIGHT));

                                gos.add(GraphOperation.unhighlightEdge(prev1, node2));
                                gos.add(GraphOperation.unhighlightEdge(node1, node2));
                                gos.add(GraphOperation.unhighlightEdge(node1, next2));
                            } else if (next2 == node1) {
                                gos.add(GraphOperation.removeEdge(prev2, node2));
                                gos.add(GraphOperation.removeEdge(node1, node2));
                                gos.add(GraphOperation.removeEdge(node1, next1));

                                gos.add(GraphOperation.addEdge(prev2, node1).setLayout(GraphOperation.Layout.HIGHLIGHT));
                                gos.add(GraphOperation.addEdge(node1, node2).setLayout(GraphOperation.Layout.HIGHLIGHT));
                                gos.add(GraphOperation.addEdge(node2, next1).setLayout(GraphOperation.Layout.HIGHLIGHT));

                                gos.add(GraphOperation.unhighlightEdge(prev2, node1));
                                gos.add(GraphOperation.unhighlightEdge(node1, node2));
                                gos.add(GraphOperation.unhighlightEdge(node2, next1));
                            }
                        }
                    }
                }
            }


        }
        double totalDis = 0;
        totalDis += Node.getDistance(randomSwappedNodes.get(0), randomSwappedNodes.get(length - 1));
        for (int i = 1; i < randomSwappedNodes.size(); i++) {
            Node n1 = randomSwappedNodes.get(i);
            Node n2 = randomSwappedNodes.get(i - 1);
            totalDis += Node.getDistance(n1, n2);
        }
        System.out.println("RS Total Dis: " + totalDis);
        return randomSwappedNodes;
    }

    public List<GraphOperation> getGos() {
        List<GraphOperation> gos = new ArrayList<>();
        this.run(nodes.size() * 20000, true, gos);
        return gos;
    }
}
