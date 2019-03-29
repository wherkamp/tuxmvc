package me.kingtux.tuxmvc.core.model;

import me.kingtux.tuxorm.TOConnection;

public interface DatabaseManager {
     TOConnection getConnection();
}
