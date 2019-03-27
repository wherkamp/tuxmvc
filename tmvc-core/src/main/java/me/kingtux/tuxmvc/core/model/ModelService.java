package me.kingtux.tuxmvc.core.model;

/**
 * A ModelService is a object is to have the ability to make quereies to the orm simply. For more advance queries you will need to use the ORM of choice
 * @param <T>
 */
public interface ModelService<T extends Model> {
    T getById(String id);
}
