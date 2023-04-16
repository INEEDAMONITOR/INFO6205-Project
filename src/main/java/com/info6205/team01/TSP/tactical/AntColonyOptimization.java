package com.info6205.team01.TSP.tactical;

import java.util.*;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.Graph.DirectedEdge;
import com.info6205.team01.TSP.util.Preprocessing;
import com.info6205.team01.TSP.visualization.TestVis;

public class AntColonyOptimization {
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("1", -0.172148, 51.479017));
        nodes.add(new Node("2", -0.0844192, 51.5682443));
        nodes.add(new Node("3", 0.0224653, 51.5338612));
        nodes.add(new Node("4", -0.3050444, 51.3938231));
        nodes.add(new Node("5", 0.05328, 51.604349));

//        Preprocessing preprocessing = new Preprocessing();
//        List<Node> nodes = preprocessing.getNodes().subList(0, 7);

        // Initialize variables for ACO
        TestVis tv = new TestVis();

        AntColonyOptimization aco = new AntColonyOptimization(tv.nodes.subList(0, 100), 10, 100, 0, 1, 5);

        aco.run();

//        aco.result();

        tv.showResult((aco.resultForTestVis()));
    }

    public AntColonyOptimization(List<Node> nodes, int ants, int iterations, double evapRate, int alpha, int beta) {
        this.ants = ants;
        this.iterations = iterations;
        this.evapRate = evapRate;
        this.alpha = alpha;
        this.beta = beta;
        this.bestTourLength = Double.POSITIVE_INFINITY;

        this.N = nodes.size();
        int i = 0;
        this.nodearray = new Node[N];
        this.nodeToIndex = new HashMap<>();
        for (Node node : nodes) {
            nodearray[i] = node;
            nodeToIndex.put(node, i++);
        }

        // Cost Matrix
//        this.costMatrix = new double[N][N];
//        for(int j = 0; j < N; j++) {
//            for(int k = 0; k < N; k++) {
//                Node node1 = nodearray[j], node2 = nodearray[k];
//                DirectedEdge e = new DirectedEdge(node1, node2);
//                costMatrix[j][k] = e.getWeight();
//            }
//        }

        // Initialize Pheromone Matrix;
        this.pheromoneMatrix = new double[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                pheromoneMatrix[r][c] = 1.0 / (double) (N * N);
            }
        }

        // Create Whole Graph & costMatrix with all nodes
        this.costMatrix = new double[N][N];
        this.originalGraph = buildGraph();
    }

    public Map<Node, List<DirectedEdge>> buildGraph() {
        Map<Node, List<DirectedEdge>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) {
            Node node1 = nodearray[i];
            for (int j = i + 1; j < N; j++) {
                Node node2 = nodearray[j];
                // node1 -> node2
                if (!graph.containsKey(node1)) graph.put(node1, new ArrayList<>());
                DirectedEdge de1 = new DirectedEdge(node1, node2);
                graph.get(node1).add(de1);
                costMatrix[i][j] = de1.getWeight();
                // node2 -> node1
                if (!graph.containsKey(node2)) graph.put(node2, new ArrayList<>());
                DirectedEdge de2 = new DirectedEdge(node2, node1);
                graph.get(node2).add(de2);
                costMatrix[j][i] = de2.getWeight();
            }
        }
        return graph;
    }

    public void run() {
        for (int i = 0; i < iterations; i++) {
            List<List<Integer>> tours = new ArrayList<>();
            for (int ant = 0; ant < ants; ant++) {
                List<Integer> tour = buildTour();
                tours.add(tour);
                updataBestTour(tour);
            }
            updatePheromoneMatrix(tours);
        }
    }

    public List<Integer> buildTour() {
        List<Integer> tour = new ArrayList<>();
        Set<Integer> unvisited = new HashSet<>();

        for (int i = 0; i < N; i++) unvisited.add(i);

        int cur = new Random().nextInt(this.N);
        tour.add(cur);
        unvisited.remove(cur);

        while (!unvisited.isEmpty()) {
            int next = getNextCity(cur, unvisited);
            tour.add(next);
            unvisited.remove(next);
            cur = next;
        }
        return tour;
    }

    public int getNextCity(int cur, Set<Integer> unvisited) {
        double sum = 0.0;
        for (Integer city : unvisited) {
            sum += Math.pow(pheromoneMatrix[cur][city], alpha) * Math.pow(1.0 / costMatrix[cur][city], beta);
        }
        double rouletteWheel = new Random().nextDouble() * sum;
        double wheelPosition = 0.0;
        for (int city : unvisited) {
            wheelPosition += Math.pow(pheromoneMatrix[cur][city], alpha) * Math.pow(1.0 / costMatrix[cur][city], beta);
            if (rouletteWheel <= wheelPosition) return city;
        }
        return -1;
    }

    public void updataBestTour(List<Integer> tour) {
        double len = getTourLength(tour);
        if (len < bestTourLength) {
            bestTourLength = len;
            bestTour = new ArrayList<>(tour);
        }
    }

    public void updatePheromoneMatrix(List<List<Integer>> tours) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double totalPheromoneDeposit = 0.0;
                for (List<Integer> tour : tours)
                    totalPheromoneDeposit += getPheromoneDeposit(tour, i, j);
                pheromoneMatrix[i][j] = (1 - evapRate) * pheromoneMatrix[i][j] + totalPheromoneDeposit;
            }
        }
    }

    private double getTourLength(List<Integer> tour) {
        double len = 0.0;

        // Calculate the length of this tour
        for (int i = 0; i < N; i++)
            len += costMatrix[tour.get(i)][tour.get((i + 1) % N)];

        return len;
    }

    private double getPheromoneDeposit(List<Integer> tour, int i, int j) {
        for (int k = 0; k < N; k++) {
            if (tour.get(k) == i && tour.get((k + 1) % N) == j || tour.get(k) == j && tour.get((k + 1) % N) == i)
                return 1.0 / getTourLength(tour);
        }
        return 0.0;
    }

    public void result() {
        System.out.println("Best tour length: " + bestTourLength);

        for (Integer i : bestTour) {
            System.out.print(nodearray[i].getId() + " -> ");
        }
        System.out.print(nodearray[bestTour.get(0)].getId());
    }

    public List<Node> resultForTestVis() {
        List<Node> res = new ArrayList<>();
        for (Integer i : bestTour) {
            res.add(nodearray[i]);
        }
        return res;
    }

    public List<Node> getNodes() {
        return Arrays.asList(nodearray);
    }

    private Map<Node, List<DirectedEdge>> originalGraph;
    private Map<Node, Integer> nodeToIndex;
    private Node[] nodearray;
    private int N;

    private int ants;      // Number of ants
    private int iterations;       // Number of iterations (t_max)
    private double evapRate;         // Pheromone evaporation rate
    private int alpha, beta; // Influence of pheromone trail (alpha) and heuristic information (beta)
    double[][] pheromoneMatrix;
    private double[][] costMatrix;

    double bestTourLength;
    List<Integer> bestTour;
}
