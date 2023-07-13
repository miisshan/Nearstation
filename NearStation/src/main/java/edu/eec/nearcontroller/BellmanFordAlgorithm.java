package edu.eec.nearcontroller;

import edu.eec.nearmodel.Edge;
import edu.eec.nearmodel.NearGraph;

import java.util.*;
import java.util.stream.IntStream;

public class BellmanFordAlgorithm implements Algorithm {
    @Override
    public Solution execute(NearGraph nearGraph) {
        /**
         * Initialize negative cycle.
         */
        boolean negativeCycle = false;
        /**
         * Initialize the distances from source to all other vertices
         * with the very large value.
         */
        Map<String, Double> distances = new HashMap<>();
        nearGraph.vertices().forEach(vertex -> distances.put(vertex.code(), Double.MAX_VALUE));
        /**
         * Initialize the distance to source vertex, 0.0.
         */
        distances.put(nearGraph.getRoot().code(), 0.0);

        /**
         * All edges.
         */
        Set<Edge> edges = nearGraph.edges();

        /**
         * Perform relaxing edges.
         */
        IntStream.range(0, nearGraph.countVertices()).forEach(i -> {
            for (Edge e : edges) {
                String uCode = e.getSource();
                String vCode = e.getDestination();
                Double weight = e.getWeight();

                if (distances.get(uCode) != Double.MAX_VALUE && distances.get(vCode) > distances.get(uCode) + weight) {
                    distances.put(vCode, distances.get(uCode) + weight);
                }

            }
        });

        for (Edge e : edges) {
            String uCode = e.getSource();
            String vCode = e.getDestination();
            Double weight = e.getWeight();
            if (distances.get(uCode) != Double.MAX_VALUE && distances.get(vCode) > distances.get(uCode) + weight) {
                negativeCycle = true;
            }
        }

        return Solution.from(distances, nearGraph, negativeCycle);
    }
}