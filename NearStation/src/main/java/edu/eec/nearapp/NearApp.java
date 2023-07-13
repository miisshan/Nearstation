package edu.eec.nearapp;
import edu.eec.nearmodel.*;
import edu.eec.nearutils.RoutingUtils;
import edu.eec.db.Config;
import edu.eec.db.DbFunctions;
import edu.eec.db.Station;
import edu.eec.nearutils.RoutingUtils$;

import java.sql.SQLOutput;
import java.util.*;

public class NearApp {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions(Config.url, Config.userName, Config.password, Config.dbName);
        List < Station > stations = db.fetchStations(db.getConnection(), Config.tableName);
        List < Vertex > vertices = new ArrayList < > ();

        for (Station station: stations) {
            int id = station.getId();
            double latitude = station.getLatitude();
            double longitude = station.getLongitude();
            String location = station.getLocation();
            //Putting the values of station in -> vertex
            Vertex vertex = new Vertex(id, latitude, longitude, location);
            vertices.add(vertex);
        }
        //Selected Stations near Jawlakhel Area:

        String[] locationStrings = {
                "Jawlakhel",
                "Manbhawan",
                "Kumaripati",
                "Lagankhel",
                "Satdobato",
                "Pulchowk",
                "Jwagal",
                "Sanepa",
                "Mahalaxmi",
                "Thasikhel",
                "Kupondole",
                "Balkhu",
                "Satdobato",
                "Ekantakuna",
                "Dhobighat",
                "Kuleshwor",
                "Khasibazar",
                "Thapathali"
        };

        Vertex[] verticesArray = new Vertex[locationStrings.length];

        //Getting the Stations from locations
        for (int i = 0; i < locationStrings.length; i++) {
            verticesArray[i] = VertexUtils.getVertexByLocationString(vertices, locationStrings[i]);
        }

        Map < String, Set < String >> neighborsMap = new HashMap < > ();

        // Define the neighbors for each vertex
        {
            addNeighbor(neighborsMap, "Jawlakhel", "Manbhawan", "Ekantakuna", "Pulchowk");
            addNeighbor(neighborsMap, "Manbhawan", "Kumaripati");
            addNeighbor(neighborsMap, "Kumaripati", "Lagankhel", "Manbhawan");
            addNeighbor(neighborsMap, "Lagankhel", "Satdobato", "Mahalaxmi");
            addNeighbor(neighborsMap, "Satdobato", "Lagankhel", "Mahalaxmi");
            addNeighbor(neighborsMap, "Pulchowk", "Jwagal", "Jawlakhel");
            addNeighbor(neighborsMap, "Jwagal", "Kupondole", "Pulchowk");
            addNeighbor(neighborsMap, "Sanepa", "Balkhu", "Dhobighat");
            addNeighbor(neighborsMap, "Mahalaxmi", "Satdobato", "Lagankhel");
            addNeighbor(neighborsMap, "Thasikhel", "Mahalaxmi", "Ekantakuna");
            addNeighbor(neighborsMap, "Balkhu", "Sanepa", "Kuleshwor", "Khasibazar");
            addNeighbor(neighborsMap, "Kupondole", "Jwagal", "Thapathali");
        }

        // Print the vertices and their neighbors
        System.out.println("Vertex\t\tNeighbors");
        for (String vertex: locationStrings) {
            System.out.print(vertex + "\t\t");
            Set < String > neighbors = neighborsMap.get(vertex);
            if (neighbors != null && !neighbors.isEmpty()) {
                System.out.println(neighbors);
            } else {
                System.out.println("No neighbors");
            }
        }

        List < Edge > edges = new ArrayList < > ();
        int counter = 1;
        NearGraph graph = NearGraph.create();

        // Add vertices to the graph
        for (Vertex vertex: verticesArray) {
            graph.addVertex(vertex);
        }

        // Add edges to the graph
        for (Map.Entry < String, Set < String >> entry: neighborsMap.entrySet()) {
            String sourceVertex = entry.getKey();
            Set < String > neighbors = entry.getValue();

            // Find the source vertex in the vertices array
            Vertex source = VertexUtils.getVertexByLocationString(vertices, sourceVertex);
            if (source == null) {
                // Handle if source vertex is not found
                continue;
            }

            for (String neighbor: neighbors) {
                // Find the neighbor vertex in the vertices array
                Vertex destination = VertexUtils.getVertexByLocationString(vertices, neighbor);
                if (destination == null) {
                    // Handle if neighbor vertex is not found
                    continue;
                }

                // Create an edge from source to neighbor
                double distance = RoutingUtils.distanceInKilometers(
                        source.getLat(), source.getLon(),
                        destination.getLat(), destination.getLon());
                WeightCalculator weightCalculator = new WeightCalculator(distance);
                double weight = weightCalculator.getWeightByFare();
                Edge edge = Edge.from(source.code(), "edge" + counter, weight, destination.code());

                // Add the edge to the graph
                graph.addEdge(edge);
                counter++;
            }
        }

        // Set the root vertex
        graph.setRoot(VertexUtils.getVertexByLocationString(vertices, "Hattiban"));

        // Print the graph
        System.out.println(graph);

    }

    private static void addNeighbor(Map < String, Set < String >> neighborsMap, String vertex, String...neighbors) {
        Set < String > vertexNeighbors = neighborsMap.getOrDefault(vertex, new HashSet < > ());
        vertexNeighbors.addAll(Arrays.asList(neighbors));
        neighborsMap.put(vertex, vertexNeighbors);

        for (String neighbor: neighbors) {
            Set < String > neighborNeighbors = neighborsMap.getOrDefault(neighbor, new HashSet < > ());
            neighborNeighbors.add(vertex);
            neighborsMap.put(neighbor, neighborNeighbors);
        }
    }
    private static int getVertexIndex(Vertex[] vertices, String location) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].getLocation().equalsIgnoreCase(location)) {
                return i;
            }
        }
        return -1; // Handle if vertex with the given location is not found
    }

}