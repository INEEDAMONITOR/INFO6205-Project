package com.info6205.team01.TSP.Graph;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class UndirectedEdgeTest {
    @Test
    public void test1() {
        Node classroom1 = new Node("Behrakis Health Sciences Center", 42.336948, -71.091111);
        Node classroom2 = new Node("Behrakis Health Sciences Center", 42.336948, -71.091111);
        Node station1 = new Node("Ruggles", 42.337122, -71.089455);
        Node station2 = new Node("Ruggles1", 42.337122, -71.089455);

        UndirectedEdge classroomToClassroom1 = new UndirectedEdge(classroom1, classroom2);
        UndirectedEdge classroomToClassroom2 = new UndirectedEdge(classroom2, classroom1);
        UndirectedEdge classroomToRuggles1 = new UndirectedEdge(classroom1, station1);
        UndirectedEdge classroomToRuggles2 = new UndirectedEdge(classroom2, station2);
        assertTrue(classroomToClassroom1.getWeight() - 0 < 0.1);

        assertEquals(classroomToClassroom1, classroomToClassroom2);
    }

}
