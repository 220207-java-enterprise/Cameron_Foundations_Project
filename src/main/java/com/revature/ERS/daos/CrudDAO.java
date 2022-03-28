package com.revature.ERS.daos;

import java.util.List;

public interface CrudDAO<T> {

    void save(T newObject);
    T getById(String id);
    List<T> getAll();
    void update(T updatedObject);
    void deleteById(T objectToBeDeleted);


    static void staticInterfaceMethod() {

    }


    // Provides a base functionality that all implementations get, but it can be overridden
    default void defaultInterfaceMethod() {

    }


}