package com.info6205.team01.TSP.strategic;

import com.info6205.team01.TSP.Graph.Node;
import com.info6205.team01.TSP.tactical.TSPNearestNeighbor;
import com.info6205.team01.TSP.util.LoadData;
import com.info6205.team01.TSP.util.LoadDataImpl;
import com.info6205.team01.TSP.visualization.AlgorithmVisualization;
import com.info6205.team01.TSP.visualization.GraphOperation;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class TwoOptTest {
    LoadData loadData;
    double[][] cityCoordinates;

    /**
     * Check node number in path
     */
    @Test
    public void pathTest1() throws Exception {
        LoadData loadData = new LoadDataImpl("src/test/java/com/info6205/team01/TSP/resources/testData5.csv");
        TSPNearestNeighbor tspNearestNeighbor = new TSPNearestNeighbor(loadData);
        tspNearestNeighbor.findShortestPath();
        TwoOpt twoOpt = new TwoOpt(tspNearestNeighbor.getTour(), loadData);
        twoOpt.optimize();
        assertTrue(twoOpt.getTour().size() == 5);
    }

    /**
     * Check all nodes are used
     */
    @Test
    public void pathTest2() throws Exception {
        LoadData loadData = new LoadDataImpl("src/test/java/com/info6205/team01/TSP/resources/testData5.csv");
        TSPNearestNeighbor tspNearestNeighbor = new TSPNearestNeighbor(loadData);
        tspNearestNeighbor.findShortestPath();
        TwoOpt twoOpt = new TwoOpt(tspNearestNeighbor.getTour(), loadData);
        twoOpt.optimize();
        for (int i = 0; i < loadData.length; i++) {
            assertTrue(twoOpt.getTour().contains(loadData.nodes.get(i)));
        }
    }

    /**
     * Check Opt path
     */
    @Test
    public void checkPath() throws Exception {
        LoadData loadData = new LoadDataImpl("src/test/java/com/info6205/team01/TSP/resources/testData5.csv");
        TSPNearestNeighbor tspNearestNeighbor = new TSPNearestNeighbor(loadData);
        tspNearestNeighbor.findShortestPath();
        TwoOpt twoOpt = new TwoOpt(tspNearestNeighbor.getTour(), loadData);
        twoOpt.optimize();
        assertTrue(twoOpt.getTour().get(0).getId().equals("54576"));
        assertTrue(twoOpt.getTour().get(1).getId().equals("c0428"));
        assertTrue(twoOpt.getTour().get(2).getId().equals("d6a9c"));
        assertTrue(twoOpt.getTour().get(3).getId().equals("c1f3d"));//c1f3d
        assertTrue(twoOpt.getTour().get(4).getId().equals("f0030"));
    }

    /**
     * Check graph
     */
    @Test
    public void checkGraph() throws Exception {
        LoadData loadData = new LoadDataImpl("src/test/java/com/info6205/team01/TSP/resources/testData5.csv");
        TSPNearestNeighbor tspNearestNeighbor = new TSPNearestNeighbor(loadData);
        tspNearestNeighbor.findShortestPath();
        TwoOpt twoOpt = new TwoOpt(tspNearestNeighbor.getTour(), loadData);
        twoOpt.optimize();
        visualization(loadData.nodes, twoOpt.getGos(), tspNearestNeighbor.getGos(), 3000);
    }

    private void visualization(List<Node> nodes, List<GraphOperation> gos, List<GraphOperation> oldGos, int interval) {
        // Build av
        AlgorithmVisualization av = new AlgorithmVisualization(nodes, gos, oldGos);
        // You can set sleep time
        // default: 500
        av.setSleepTime(interval);
        av.showResult();
    }
}
