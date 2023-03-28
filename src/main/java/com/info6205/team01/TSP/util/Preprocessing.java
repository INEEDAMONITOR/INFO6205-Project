package com.info6205.team01.TSP.util;

import com.info6205.team01.TSP.Graph.Node;

import java.util.ArrayList;
import java.util.Scanner;

public class Preprocessing {
    String path = "./src/main/java/com/info6205/team01/TSP/resources/crimeSample.csv";
    ArrayList<Node> nodes = new ArrayList<>();

    private void getNodes() {
        //parsing a CSV file into Scanner class constructor
        Scanner sc = new Scanner(path);
        sc.useDelimiter(",");   //sets the delimiter pattern
        sc.nextLine();
        while (sc.hasNext())  //returns a boolean value
        {
            String[] line = sc.nextLine().split(",");
            nodes.add(new Node(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }
        sc.close();  //closes the scanner
    }
    public Preprocessing() {
        getNodes();
    }
    public void getAdjacentMatrix(){

    }
    public static void main(String[] args) {

    }
    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}

