package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        LOG.info("Saving {}", meal);
        if (meal.isNew()) {
            LOG.trace("Generating id");
            meal.setId(counter.incrementAndGet());
        }
        Map<Integer, Meal> meals = repository.computeIfAbsent(meal.getUserId(), ConcurrentHashMap::new);
        meals.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        LOG.info("Deleting Meal: id[{}]", userId + "." + id);

        Map<Integer, Meal> meals = repository.get(userId);
        return
                null != meals && meals.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        LOG.info("Retrieving Meal: id[{}]", userId + "." + id);

        Map<Integer, Meal> meals = repository.get(userId);
        return
                null == meals ? null : meals.get(id);
    }

    @Override
    public List<Meal> getInRange(int userId, LocalDate start, LocalDate end) {
        LOG.info("Retrieving Meals for User[{}] in range:{}-{}", userId, start, end);

        return
                repository.getOrDefault(userId, Collections.emptyMap())
                        .values()
                        .stream()
                        .filter(meal -> {
                            LocalDate date = meal.getDateTime().toLocalDate();
                            return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
                        })
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("Retrieving Meals for User: id[{}]", userId);

        return
               getInRange(userId,LocalDate.MIN,LocalDate.MAX);
    }
}

