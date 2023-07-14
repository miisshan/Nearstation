package edu.eec.nearapp;

import edu.eec.db.Config;
import edu.eec.db.DbFunctions;
import edu.eec.db.Route;

import java.util.List;

public class testApp {

    public static void main(String[] args) {
        DbFunctions db = new DbFunctions(Config.url, Config.userName, Config.password, Config.dbName);
        List<Route> routes = db.fetchRoutes(db.getConnection(), Config.routeTable);
        System.out.println(routes);
    }

}
