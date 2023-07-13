package edu.eec.nearcontroller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.eec.nearmodel.Edge;
import edu.eec.nearmodel.Vertex;
import edu.eec.nearutils.Conversion;

public class Result {

    /**
     * Source vertex.
     */
    private final Vertex source;

    private final Edge edge;

    private final Vertex destination;

    /**
     * Default constructor.
     */
    public Result(Vertex source, Edge edge, Vertex destination) {
        this.source = source;
        this.edge = edge;
        this.destination = destination;
    }

    /**
     * To Json representation.
     */
    @Override
    public String toString() {
        return Conversion.toJson(this);
    }

    /**
     * Empty Result.
     */
    public static Result empty() {
        return new Result(Vertex.empty(), Edge.empty(), Vertex.empty());
    }
}