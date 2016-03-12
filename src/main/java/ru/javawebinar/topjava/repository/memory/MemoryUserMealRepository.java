package ru.javawebinar.topjava.repository.memory;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.javawebinar.topjava.util.IdGenerator.getUserMealId;

public class MemoryUserMealRepository implements UserMealRepository {

    private final Map<Long, UserMeal> repository = new ConcurrentHashMap<Long, UserMeal>() {{
        put(1L, new UserMeal(getUserMealId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        put(2L, new UserMeal(getUserMealId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        put(3L, new UserMeal(getUserMealId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        put(4L, new UserMeal(getUserMealId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        put(5L, new UserMeal(getUserMealId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        put(6L, new UserMeal(getUserMealId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 511));
    }};


    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.getId() == -1)
            userMeal.setId(getUserMealId());

        repository.put(userMeal.getId(), userMeal);

        return userMeal;
    }

    @Override
    public void delete(long id) {
        repository.remove(id);
    }

    @Override
    public UserMeal get(long id) {
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        return repository.values();
    }
}
