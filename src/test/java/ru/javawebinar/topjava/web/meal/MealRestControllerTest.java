package ru.javawebinar.topjava.web.meal;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.LoggedUser;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MealRestControllerTest {

    static private ApplicationContext ctx;
    static private LoggedUser user;

    private MealRestController controller;

    @BeforeClass
    public static void setCtx() {
        ctx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        user = new LoggedUser(0);
    }

    @Before
    public void setUp() throws Exception {
        controller = ctx.getBean(MealRestController.class);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = controller.save(new Meal(5, LocalDateTime.now(), "description", 1221));
        assertEquals(meal, controller.get(5, meal.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithInvalidUser() throws Exception {
        controller.get(15, 15);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithInvalidMeal() throws Exception {
        controller.get(0, 15);
    }

    @Test(expected = NotFoundException.class)
    public void testDelete() throws Exception {
        Meal meal = controller.save(new Meal(5, LocalDateTime.now(), "description", 1221));
        controller.delete(meal.getUserId(), meal.getId());
        controller.get(meal.getUserId(), meal.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWithInvalidUser() throws Exception {
        controller.delete(55, 1);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWithInvalidMeal() throws Exception {
        controller.delete(0, 55);
    }

    @Test
    public void testGetAll() throws Exception {
        Meal meal = controller.save(new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));

        MealWithExceed mealWithExceed = new MealWithExceed(meal, false);
        List<MealWithExceed> meals = controller.getAll(new LoggedUser(1));

        assertEquals(meals, Collections.singletonList(mealWithExceed));
    }

    @Test/*(expected = NotFoundException.class)*/
    public void testGetAllWithInvalidUser() throws Exception {
        assertTrue(controller.getAll(new LoggedUser(555)).isEmpty());
    }

    @Test/*(expected = NotFoundException.class)*/
    public void testGetInRangeWithInvalidUser() throws Exception {
        testGetAllWithInvalidUser();
    }

    @Test
    public void testGetInRange() throws Exception {
        MealWithExceed mealWithExceed1 = new MealWithExceed(controller.save(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 9, 0), "Завтрак", 500)), false);
        MealWithExceed mealWithExceed = new MealWithExceed(controller.save(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)), false);

        controller.save(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 8, 0), "Завтрак", 500));
        controller.save(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 11, 0), "Завтрак", 500));
        controller.save(new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500));

        List<MealWithExceed> expectedMeals = Arrays.asList(mealWithExceed, mealWithExceed1)
                        .stream()
                        .sorted(Comparator.comparing(MealWithExceed::getDateTime).reversed())
                        .collect(Collectors.toList());

        List<MealWithExceed> meals = controller.getInRange(
                new LoggedUser(3),
                LocalDateTime.of(2015, Month.MAY, 30, 9, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 10, 0));

        assertEquals(meals,expectedMeals);
    }
}