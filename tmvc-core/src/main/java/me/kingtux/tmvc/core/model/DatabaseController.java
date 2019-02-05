package me.kingtux.tmvc.core.model;

public interface DatabaseController {
    <T extends Model> ModelService<T> registerModel(T model);
}
