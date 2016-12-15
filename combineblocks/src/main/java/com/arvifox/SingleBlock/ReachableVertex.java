package com.arvifox.SingleBlock;

import java.util.*;

/**
 * Created by Andrey on 03.12.2016.
 */
class ReachableVertex {
    Map<Integer, List<Integer>> rv;

    ReachableVertex() {
        rv = new HashMap<>();
    }

    private void addVertex(int v) {
        if (!hasVertex(v)) {
            rv.put(v, new ArrayList<>());
        }
    }

//    public boolean isEmpty() {
//        return rv.isEmpty();
//    }

    private boolean hasVertex(int v) {
        return rv.containsKey(v);
    }

    boolean hasEdge(int v1, int v2) {
        if (!hasVertex(v1)) return false;
        List<Integer> l = rv.get(v1);
        return Collections.binarySearch(l, v2) >= 0;
    }

    void addEdge(int v1, int v2) {
        if (!hasEdge(v1, v2)) {
            if (!hasVertex(v1)) addVertex(v1);
            if (!hasVertex(v2)) addVertex(v2);
            List<Integer> l1 = rv.get(v1);
            List<Integer> l2 = rv.get(v2);
            l1.add(v2);
            l2.add(v1);
            Collections.sort(l1);
            Collections.sort(l2);
        }
    }

    void addSingleEdge(int v1, int v2) {
        if (!hasEdge(v1, v2)) {
            if (!hasVertex(v1)) addVertex(v1);
            List<Integer> l1 = rv.get(v1);
            l1.add(v2);
            Collections.sort(l1);
        }
    }
}
