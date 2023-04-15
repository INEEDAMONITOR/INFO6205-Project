package com.info6205.team01.TSP.util;

public class Tools {

    public static final int earthRadius = 6378137;


    public static double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double latitude = Math.toRadians(latitude2 - latitude1);
        double longitude = Math.toRadians(longitude2 - longitude1);
        double a = Math.pow(Math.sin(latitude / 2), 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.pow(Math.sin(longitude / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    public static void main(String[] args) {
        // Boston to New York: 300km
        System.out.println(distance(42.361145, -71.057083, 40.730610, -73.935242));
        System.out.println(distance(51.515192, -0.016542, 51.495871, -0.184411));
        System.out.println(distance(51.515192, -0.016542, 51.415897, -0.098618));
    }

}
