package com.info6205.team01.TSP.Graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void nodeTest1() {
        Node classroom = new Node("Behrakis Health Sciences Center", 42.336948, -71.091111);
        Node station = new Node("Ruggles", 42.337122, -71.089455);
        Node station2 = new Node("Ruggles1", 42.337122, -71.089455);
        // Get Id
        assertSame("Behrakis Health Sciences Center", classroom.getId());
        assertSame("Ruggles", station.getId());

        // Get long and lag
        assertEquals(42.336948, classroom.getLongitude(), 0.0);
        assertEquals(-71.091111, classroom.getLatitude(), 0.0);

        // Node.getDistance
        // The distance between the Ruggles Station and classroom
        assertTrue(Node.getDistance(classroom, station) - 184.25 < 1);
        assertEquals(0, Node.getDistance(station, station2), 0.0);

        // degree
        classroom.increaseDegree();
        classroom.increaseDegree();
        classroom.increaseDegree();
        classroom.increaseDegree();
        assertEquals(4, classroom.getDegree());
        classroom.decreaseDegree();
        classroom.decreaseDegree();
        assertEquals(2, classroom.getDegree());
    }

}


