package com.info6205.team01.TSP.util;

import com.info6205.team01.TSP.Graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class LoadData {
    public String path;

    public int length;
    public String[][] metaData;
    public ArrayList<Node> nodes = new ArrayList<>();
    public double[][] coordination;
    public double[][] adjacencyMatrix;
    public String[] indexToID;
    public Map<String, Integer> IDToIndex = new HashMap<>();
}
