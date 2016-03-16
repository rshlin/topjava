package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int userId,int id) {
        ExceptionUtil.check(repository.delete(userId, id),id);
    }

    @Override
    public Meal get(int userId, int id) {
        return ExceptionUtil.check(repository.get(userId, id),id);
    }

    @Override
    public List<Meal> getInRange(int userId, LocalDate start, LocalDate end) {

        return ExceptionUtil.check(repository.getInRange(userId, start, end),userId);
    }
}
