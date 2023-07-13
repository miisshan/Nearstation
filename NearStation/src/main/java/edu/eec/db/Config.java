package edu.eec.db;

public class Config {
    /**
     * Configuration definition.
     */
    public static final String url = "jdbc:postgresql://localhost:5432/";
    public static final String dbName = "geocoordinates";
    public static final String userName = "postgres";
    public static final String password = "hello";

    public static final String tableName = "coordinates";

}


//        DbFunctions db = new DbFunctions("jdbc:postgresql://localhost:5432/","postgres","hello","geocoordinates");