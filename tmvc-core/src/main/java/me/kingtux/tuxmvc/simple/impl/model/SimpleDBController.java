package me.kingtux.tuxmvc.simple.impl.model;

import me.kingtux.tuxmvc.core.model.DatabaseController;
import me.kingtux.tuxmvc.core.model.Model;
import me.kingtux.tuxmvc.core.model.ModelService;

public class SimpleDBController implements DatabaseController {

    @Override
    public <T extends Model> ModelService<T> registerModel(T t) {
        return new SimpleModelService<T>();
    }
}
