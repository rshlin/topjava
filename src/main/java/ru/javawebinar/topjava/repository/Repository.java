package ru.javawebinar.topjava.repository;

import java.util.Collection;

public interface Repository<T> {
    T save(T entity);

    void delete(long id);

    T get(long id);

    Collection<T> getAll();
}
