package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/meals")
public class MealController extends AbstractUserMealController {

    @RequestMapping(method = RequestMethod.GET)
    public String getAllMeals(Model model) {
        model.addAttribute("mealList", getAll());
        return "mealList";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteMeal(int id) {
        delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createMeal(Model model) {
        model.addAttribute("meal", new UserMeal(LocalDateTime.now(), "", 1000));
        return "mealEdit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateMeal(Model model, int id) {
        model.addAttribute("meal", get(id));
        return "mealEdit";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filterMeals(HttpServletRequest request) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        request.setAttribute("mealList", getBetween(startDate, startTime, endDate, endTime));
        return "mealList";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitMeal(
            @RequestParam(value = "id", required = false) Integer id,
            String dateTime, String description, int calories) {
        final UserMeal meal = new UserMeal(LocalDateTime.parse(dateTime), description, calories);
        if (id == null) {
            create(meal);
        } else {
            update(meal, id);
        }
        return "redirect:/meals";
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
