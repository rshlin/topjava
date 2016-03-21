package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int USER_ID = UserTestData.USER_ID;
    public static final int ADMIN_ID = UserTestData.ADMIN_ID;
    public static final int NON_EXISTING_ID = 55;


    private static int startSeq = BaseEntity.START_SEQ + 1;

    public static final UserMeal MEAL = new UserMeal(++startSeq, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final int MEAL_ID = MEAL.getId();

    public static final List<UserMeal> MEALS = sort(Arrays.asList(
            new UserMeal(++startSeq, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(++startSeq, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(++startSeq, LocalDateTime.of(2015, Month.MAY, 31, 9, 0), "Завтрак", 1000),
            new UserMeal(++startSeq, LocalDateTime.of(2015, Month.MAY, 31, 14, 0), "Обед", 500),
            new UserMeal(++startSeq, LocalDateTime.of(2015, Month.MAY, 31, 21, 0), "Ужин", 510),
            MEAL
    ));
    public static final List<UserMeal> MEALS_AFTER_DELETION = sort(MEALS.subList(0, MEALS.size() - 1));

    public static final LocalDateTime BETWEEN_DATETIMES_START_DATE = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
    public static final LocalDateTime BETWEEN_DATETIMES_END_DATE = LocalDateTime.of(2015, Month.MAY, 30, 13, 0);
    public static final List<UserMeal> MEALS_BETWEEN_DATETIME = sort(
            MEALS.stream().filter(meal -> TimeUtil.isBetween(
                    meal.getDateTime(),
                    BETWEEN_DATETIMES_START_DATE,
                    BETWEEN_DATETIMES_END_DATE)));

    public static UserMeal getUpdatedMeal() {
        return new UserMeal(
                MEAL.getId(),
                MEAL.getDateTime(),
                "Updated Description",
                MEAL.getCalories()
        );
    }

    public static List<UserMeal> sort(List<UserMeal> meals) {
        return sort(meals.stream());
    }

    public static List<UserMeal> sort(Stream<UserMeal> meals) {
        return meals.sorted(Comparator.comparing(UserMeal::getDateTime).reversed()).collect(Collectors.toList());
    }
}
