package ru.javawebinar.topjava.web.meal;

import org.hamcrest.Matchers;
import org.junit.Test;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserMealControllerTest extends AbstractControllerTest {

    public static final String MEALS = "/meals";

    @Test
    public void testMealList() throws Exception{
        mockMvc.perform(get(MEALS))
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", Matchers.hasSize(6)))
                .andExpect(model().attribute("mealList", Matchers.is(MealTestData.EXCEEDED_MEALS)));
    }
}
