package ru.javawebinar.topjava.service.meal;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserMealServiceTest extends UserMealServiceTest {

    @Test
    public void testGetWithUser() throws Exception {
        UserMeal meal = service.getWithUser(MEAL1_ID, MEAL1.getUser().getId());
        MATCHER.assertEquals(meal, MEAL1);
        UserTestData.MATCHER.assertEquals(MEAL1.getUser(), meal.getUser());
    }

    @Test
    public void testGetWithUserNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getWithUser(1, 5);
    }
}
