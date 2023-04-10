package com.info6205.team01.TSP.util;

import com.info6205.team01.TSP.Graph.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class LoadCSVData {

    public static final String path = "src/main/java/com/info6205/team01/TSP/resources/crimeSample.csv";

    public int length = 0;
    public String[][] metaData;
    public ArrayList<Node> nodes = new ArrayList<>();
    public double[][] coordination;
    public double[][] adjacencyMatrix;
    public String[] indexToID;
    public Map<String, Integer> IDToIndex = new HashMap<>();

    public LoadCSVData() throws Exception {
        countLines();
        metaData = new String[length][3];
        coordination = new double[length][2];
        adjacencyMatrix = new double[length][length];
        indexToID = new String[length];
        loadData();
    }

    private void countLines() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while (reader.readLine() != null) length++;
        reader.close();
        length -= 1;
    }

    public void loadData() {
        //parsing a CSV file into Scanner class constructor
        Scanner sc;
        try {
            sc = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        sc.useDelimiter(",");   //sets the delimiter pattern
        sc.nextLine();

        // load metaData, init nodes, coordination, indexToID, IDToIndex
        int index = 0;
        while (sc.hasNext())  //returns a boolean value
        {
            String[] line = sc.nextLine().split(",");
            // metaData
            for (int i = 0; i < 3; i++) {
                metaData[index][i] = line[i];
            }
            String id = line[0].substring(line[0].length() - 5);
            double longitude = Double.parseDouble(line[1]);
            double latitude = Double.parseDouble(line[2]);
            // nodes
            nodes.add(new Node(id, longitude, latitude));
            // coordination
            coordination[index][0] = longitude;
            coordination[index][1] = latitude;
            // indexToID
            indexToID[index] = id;
            // IDToIndex
            IDToIndex.put(id, index);
            index++;
        }
        // create adjacencyMatrix
        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= i; j++) {
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    double distance = Tools.distance(coordination[i][1], coordination[i][0],coordination[j][1], coordination[j][0]);
                    adjacencyMatrix[i][j] = distance;
                    adjacencyMatrix[j][i] = distance;
                }
            }
        }

        sc.close();  //closes the scanner
    }

    public static void main(String[] args) throws Exception {
        LoadCSVData loadCSVData = new LoadCSVData();
        System.out.println(loadCSVData.length);
    }
}
