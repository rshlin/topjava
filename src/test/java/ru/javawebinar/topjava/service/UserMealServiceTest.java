package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertNotSame;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {



    @Autowired
    UserMealService service;

    @Autowired
    DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal meal = service.get(MEAL_ID, USER_ID);
        MATCHER.assertEquals(MEAL, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetInvalidUserId() throws Exception {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNonExistingUserId() throws Exception {
        service.get(MEAL_ID, NON_EXISTING_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        MATCHER.assertCollectionEquals(service.getAll(USER_ID), MEALS_AFTER_DELETION);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNonExistingUserId() throws Exception {
        service.delete(MEAL_ID, NON_EXISTING_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNonExistingMealId() throws Exception {
        service.delete(NON_EXISTING_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteInvalidUserId() throws Exception {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<UserMeal> betweenDateTimes = service.getBetweenDateTimes(
                BETWEEN_DATETIMES_START_DATE, BETWEEN_DATETIMES_END_DATE, USER_ID);
        MATCHER.assertCollectionEquals(MEALS_BETWEEN_DATETIME, betweenDateTimes);
    }


    @Test
    public void testGetBetweenDescendingDateTimes() throws Exception {
        Collection<UserMeal> betweenDateTimes = service.getBetweenDateTimes(
                BETWEEN_DATETIMES_END_DATE, BETWEEN_DATETIMES_START_DATE, USER_ID);
        MATCHER.assertCollectionEquals(Collections.emptyList(), betweenDateTimes);
    }

    @Test
    public void testGetBetweenDateTimesInvalidUserId() throws Exception {
        Collection<UserMeal> betweenDateTimes = service.getBetweenDateTimes(
                BETWEEN_DATETIMES_START_DATE, BETWEEN_DATETIMES_END_DATE, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.emptyList(), betweenDateTimes);
    }


    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void testGetAllNonExistingUserId() throws Exception {
        MATCHER.assertCollectionEquals(service.getAll(NON_EXISTING_ID), Collections.emptyList());
    }
    @Test
    public void testGetAllInvalidUserId() throws Exception {
        MATCHER.assertCollectionEquals(service.getAll(ADMIN_ID), Collections.emptyList());
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updatedMeal = getUpdatedMeal();
        MATCHER.assertEquals(service.update(updatedMeal,USER_ID),updatedMeal);
        assertNotSame(MEAL, updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateInvalidUserId() throws Exception {
        UserMeal updatedMeal = getUpdatedMeal();
        service.update(updatedMeal, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNonExistingUserId() throws Exception {
        UserMeal updatedMeal = getUpdatedMeal();
        service.update(updatedMeal, NON_EXISTING_ID);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal meal = new UserMeal(null, LocalDateTime.now(), "Description", 2121);
        UserMeal savedMeal = service.save(meal, USER_ID);
        meal.setId(savedMeal.getId());
        MATCHER.assertEquals(meal, savedMeal);
    }
}