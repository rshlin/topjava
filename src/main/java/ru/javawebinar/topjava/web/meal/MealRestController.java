package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.LoggedUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        LOG.info("Save {}", meal);
        return service.save(meal);
    }

    public Meal get(int userId, int id) {
        return service.get(userId, id);
    }

    public void delete(int userId, int id) {
        LOG.info("Delete {}", userId + "." + id);
        service.delete(userId, id);
    }

    public List<MealWithExceed> getAll(LoggedUser user) {
        return
                getInRange(user, LocalDateTime.MIN, LocalDateTime.MAX);
    }


    public List<MealWithExceed> getInRange(LoggedUser user, LocalDateTime start, LocalDateTime end) {
        List<Meal> meals = service.getInRange(user.getId(), start.toLocalDate(), end.toLocalDate());

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), start.toLocalTime(), end.toLocalTime()))
                .map(um -> new MealWithExceed(
                        um,
                        caloriesSumByDate.get(um.getDateTime().toLocalDate()) > user.getCaloriesPerDay()))
                .collect(Collectors.toList());
    }
}
