package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal);

    void delete(int userId, int id);

    Meal get(int userId, int id);

    List<Meal> getInRange(int userId, LocalDate start, LocalDate end);
}
