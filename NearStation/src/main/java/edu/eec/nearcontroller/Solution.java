package edu.eec.nearcontroller;

import edu.eec.nearmodel.Edge;
import edu.eec.nearmodel.NearGraph;
import edu.eec.nearmodel.Vertex;
import edu.eec.nearutils.Conversion;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Solution {

    private boolean negativeCycle;

    private Map < String, Double > distances;

    private NearGraph graph;

    public Solution(Map < String, Double > distances, NearGraph graph, boolean negativeCycle) {
        this.distances = distances;
        this.graph = graph;
        this.negativeCycle = negativeCycle;
    }

    /**
     * Check if the solution has negative cycle.
     */
    public boolean hasNegativeCycle() {
        return this.negativeCycle;
    }

    public Map < String, Double > getDistances() {
        return this.distances;
    }

    /**
     * Returns the minimum distance.
     */
    public Result minimumDistance() {
        /**
         * Remote the root itself from the solution.
         */
        this.distances.remove(graph.getRoot().code());
        String label = Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey();
        Optional < Edge > edge = graph.edges().stream().filter(e -> e.getDestination().equals(label)).findAny();
        Optional < Vertex > target = graph.vertexByLabel(label);

        boolean isSolutionAvailable = edge.isPresent() && target.isPresent() && !hasNegativeCycle();
        if (isSolutionAvailable)
            return new Result(graph.getRoot(), edge.get(), target.get());
        else
            return Result.empty();
    }

    /**
     * Factory.
     */
    public static Solution from(Map < String, Double > distances, NearGraph graph, boolean negativeCycle) {
        return new Solution(distances, graph, negativeCycle);
    }

    /**
     * Its an empty instance of the solution.
     *
     * @return emptySolution
     */
    public static Solution empty() {
        return new Solution(new HashMap < > (), NearGraph.empty(), false);
    }

    @Override
    public String toString() {
        return Conversion.toJson(this);
    }
}