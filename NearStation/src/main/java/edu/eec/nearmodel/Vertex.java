package edu.eec.nearmodel;

import edu.eec.nearutils.Conversion;

public class Vertex {

    /**
     * Vertex ID.
     */
    private final int id;

    /*
     * Vertex Location.
     */

    private String location;

    /**
     * Label.
     */
    private String label;

    /**
     * Latitude.
     */
    private final double lat;

    /**
     * Longitude.
     */
    private final double lon;

    /**
     * Default Constructor.
     */

    public Vertex(int id, double lat, double lon, String location) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.location = location;
        this.label = String.valueOf(this.hashCode());
    }

    public int getId() {
        return id;
    }

    public String code() {
        return this.label;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getLocation() {
        return this.location;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return Conversion.toJson(this);
    }

    public static Vertex from(int id, double lat, double lon, String location) {
        return new Vertex(id, lat, lon, location);
    }

    public static Vertex empty() {
        return new Vertex(0, 0.0, 0.0, "");
    }
}