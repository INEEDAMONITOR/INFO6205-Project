package com.info6205.team01.TSP.util;

import com.info6205.team01.TSP.Graph.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LoadDataImpl extends LoadData {


    public LoadDataImpl(String path) throws Exception {
        this.path = path;
        countLines();
        metaData = new String[length][3];
        coordination = new double[length][2];
        adjacencyMatrix = new double[length][length];
        indexToID = new String[length];
        loadData();
    }

    public LoadDataImpl() throws Exception {
        this("src/main/java/com/info6205/team01/TSP/resources/crimeSample.csv");
//        this("src/main/java/com/info6205/team01/TSP/resources/info6205.spring2023.teamproject.csv");
    }

    private void countLines() throws Exception {
        int tempLength = 0;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while (reader.readLine() != null) tempLength++;
        reader.close();
        tempLength -= 1;
        length = tempLength;
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
            String id = line[0].substring(line[0].length() - 7);
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
                    double distance = Tools.distance(coordination[i][1], coordination[i][0], coordination[j][1], coordination[j][0]);
                    adjacencyMatrix[i][j] = distance;
                    adjacencyMatrix[j][i] = distance;
                }
            }
        }

        sc.close();  //closes the scanner
    }

    public static void main(String[] args) throws Exception {
        LoadDataImpl loadCSVData = new LoadDataImpl();
        System.out.println(loadCSVData.length);
    }

}
