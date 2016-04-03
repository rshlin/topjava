package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void testGetUserWithMeals() {
        User userWithMeals = service.getUserWithMeals(USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.USER_MEALS, userWithMeals.getMeals());
        MATCHER.assertEquals(USER, userWithMeals);
    }

    @Test(expected = NotFoundException.class)
    public void testGetUserWithMealsNotFound() {
        service.getUserWithMeals(1);
    }
}
