package me.kingtux.tuxmvc.simple.db;

import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxorm.TOConnection;

public class SimpleDatabaseManager implements DatabaseManager {
    private TOConnection connection;

    public SimpleDatabaseManager(TOConnection connection) {
        this.connection = connection;
    }

    @Override
    public TOConnection getConnection() {
        return connection;
    }
}
