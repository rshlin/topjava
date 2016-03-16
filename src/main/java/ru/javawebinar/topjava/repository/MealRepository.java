package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int userId, int id);

    Meal get(int userId, int id);

    List<Meal> getInRange(int userId, LocalDate start, LocalDate end);

    List<Meal> getAll(int userId);
}
