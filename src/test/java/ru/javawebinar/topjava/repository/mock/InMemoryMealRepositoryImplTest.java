package ru.javawebinar.topjava.repository.mock;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InMemoryMealRepositoryImplTest {

    private InMemoryMealRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository = new InMemoryMealRepositoryImpl();
    }

    @Test
    public void testSave() throws Exception {
        Meal meal = new Meal(2, LocalDateTime.now(), "description", 1200);
        repository.save(meal);
        assertEquals(repository.getAll(2).get(0), meal);
    }

    @Test
    public void testDelete() throws Exception {
        Meal meal = repository.get(0, 1);
        assertFalse(repository.delete(55, 15));
        assertTrue(repository.delete(meal.getUserId(), meal.getId()));
        assertNull(repository.get(meal.getUserId(), meal.getId()));
    }

    @Test
    public void testDeleteInvalidMeal() throws Exception {
        assertFalse(repository.delete(55, 15));
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = repository.save(new Meal(5, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        assertEquals(meal, repository.get(meal.getUserId(), meal.getId()));
    }

    @Test()
    public void testGetInvalidMeal() throws Exception {
        assertNull(repository.get(55, 15));
    }

    @Test
    public void testGetInRange() throws Exception {
        List<Meal> meals = repository.getInRange(
                0,
                LocalDateTime.of(2015, Month.MAY, 30, 0, 0).toLocalDate(),
                LocalDateTime.of(2015, Month.MAY, 30, 23, 0).toLocalDate());
        List<Meal> expectedMeals = Arrays.asList(
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)
        );

        assertEquals(meals, expectedMeals);
    }

    @Test
    public void testGetInRangeWithInvalidUser() throws Exception {
        List<Meal> meals = repository.getInRange(
                55,
                LocalDateTime.of(2015, Month.MAY, 30, 0, 0).toLocalDate(),
                LocalDateTime.of(2015, Month.MAY, 30, 23, 0).toLocalDate());
        assertTrue(meals.isEmpty());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Meal> meals = repository.getAll(0);
        List<Meal> expectedMeals = Arrays.asList(
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)
        );
        assertEquals(meals, expectedMeals);
    }

    @Test
    public void testGetAllWithInvalidUser() throws Exception {
        List<Meal> meals = repository.getAll(55);
        assertTrue(meals.isEmpty());
    }
}