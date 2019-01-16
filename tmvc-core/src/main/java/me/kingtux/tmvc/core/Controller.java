package me.kingtux.tmvc.core;

import java.util.List;

public interface Controller {


    default List<SingleController> getSingleControllers() {
        return null;
    }
}
