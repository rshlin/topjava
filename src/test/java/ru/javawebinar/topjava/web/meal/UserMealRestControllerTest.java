package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.util.json.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserMealRestController.MEALS + "/";

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(EXCEEDED_MATCHER.contentListMatcher(EXCEEDED_MEALS));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testGetBetween() throws Exception {
        List<UserMealWithExceed> withExceeded = FILTERED_EXCEEDED_MEALS;
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDateTime", FILTER_START.toString())
                .param("endDateTime", FILTER_END.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(EXCEEDED_MATCHER.contentListMatcher(withExceeded));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk());

        MATCHER.assertCollectionEquals(USER_MEALS.subList(0, 5), mealService.getAll(UserTestData.USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updatedMeal = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedMeal)))
                .andExpect(status().isOk())
                .andDo(print());

        MATCHER.assertEquals(updatedMeal, mealService.get(MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    public void testCreate() throws Exception {
        UserMeal created = getCreated();

        ResultActions actions = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        UserMeal received = MATCHER.fromJsonAction(actions);
        created.setId(received.getId());

        ArrayList<UserMeal> meals = new ArrayList<UserMeal>(USER_MEALS) {{
            add(0, created);
        }};

        MATCHER.assertEquals(created, received);
        MATCHER.assertCollectionEquals(mealService.getAll(UserTestData.USER_ID), meals);
    }
}
