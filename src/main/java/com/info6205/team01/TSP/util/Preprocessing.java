package com.info6205.team01.TSP.util;

import com.info6205.team01.TSP.Graph.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Preprocessing {

    String path = "/Users/yuxichen/Documents/NEU Obsidian/NEU/Graduate/2023 Spring/INFO6205 Program Structure & Algorithms 39568/TSP/INFO6205-Project/src/main/java/com/info6205/team01/TSP/resources/crimeSample.csv";
    ArrayList<Node> nodes = new ArrayList<>();

    public List<Node> getNodes() {
        return nodes;
    }

    private void getNodesFromFile() {
        //parsing a CSV file into Scanner class constructor
        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        sc.useDelimiter(",");   //sets the delimiter pattern
        sc.nextLine();

        while (sc.hasNext())  //returns a boolean value
        {
            String[] line = sc.nextLine().split(",");
            nodes.add(new Node(line[0].substring(line[0].length() - 5), Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }
        sc.close();  //closes the scanner


//    //parsing a CSV file into Scanner class constructor
//        Scanner sc = new Scanner("/Users/yuxichen/Documents/NEU Obsidian/NEU/Graduate/2023 Spring/INFO6205 Program Structure & Algorithms 39568/TSP/INFO6205-Project/src/main/java/com/info6205/team01/TSP/resources/crimeSample.csv");
//        sc.useDelimiter(",");   //sets the delimiter pattern
//        sc.nextLine();
//        while (sc.hasNext())  //returns a boolean value
//        {
//            String[] line = sc.nextLine().split(",");
//            System.out.println(line);
//            nodes.add(new Node(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2])));
//        }
//        sc.close();  //closes the scanner
    }

    public Preprocessing() {
        getNodesFromFile();
    }

    public void getAdjacentMatrix() {

    }

    public static void main(String[] args) {
        Preprocessing preprocessing = new Preprocessing();
        System.out.println(preprocessing.getNodes());
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}

