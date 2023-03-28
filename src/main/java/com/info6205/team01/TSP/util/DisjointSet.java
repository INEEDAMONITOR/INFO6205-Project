package com.info6205.team01.TSP.util;

import java.util.Arrays;

public class DisjointSet {
    int[] root;
    int[] height;

    public DisjointSet(int n) {
        root = new int[n];
        for (int i = 0; i < n; i++) {
            root[i] = i;
        }
        height = new int[n];
        Arrays.fill(height, 1);
    }

    public int rootOf(int node) {
        if (root[node] == node) {
            return node;
        }
        return root[node] = rootOf(root[node]);
    }

    public boolean connect(int node1, int node2) {
        int root1 = rootOf(node1);
        int root2 = rootOf(node2);
        if (root1 == root2) {
            return false;
        }
        if (height[root1] > height[root2]) {
            root[root2] = root1;
        } else if (height[root1] < height[root2]) {
            root[root1] = root2;
        } else {
            root[root2] = root1;
            height[root1]++;
        }

        return true;
    }
}
