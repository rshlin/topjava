package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.filter.AuthorizationFilter;
import ru.javawebinar.topjava.web.listener.ApplicationContextListener;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    public static final String ACTION = "action";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_CREATE = "create";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_FILTER = "filter";
    public static final String ACTION_GET_ALL = "getAll";

    public static final String ATTR_MEAL = "meal";
    public static final String ATTR_MEAL_LIST = "mealList";

    public static final String PARAM_FILTER_START_DATE = "startDate";
    public static final String PARAM_FILTER_START_TIME = "startTime";
    public static final String PARAM_FILTER_END_DATE = "endDate";
    public static final String PARAM_FILTER_END_TIME = "endTime";

    public static final String PARAM_MEAL_ID = "id";
    public static final String PARAM_MEAL_DESCRIPTION = "description";
    public static final String PARAM_MEAL_DATE_TIME = "dateTime";
    public static final String PARAM_MEAL_CALORIES = "calories";

    @Autowired
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ((ApplicationContext) config.getServletContext().getAttribute(ApplicationContextListener.CONTEXT)).getAutowireCapableBeanFactory().autowireBean(this);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String id = request.getParameter(PARAM_MEAL_ID);
        int userId = ((LoggedUser) request.getSession().getAttribute(AuthorizationFilter.USER_PROFILE)).getId();

        Meal meal = new Meal(
                userId,
                id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter(PARAM_MEAL_DATE_TIME)),
                request.getParameter(PARAM_MEAL_DESCRIPTION),
                Integer.valueOf(request.getParameter(PARAM_MEAL_CALORIES)));
        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ACTION);
        if (null == action)
            action = ACTION_GET_ALL;

        LoggedUser user = (LoggedUser) request.getSession().getAttribute(AuthorizationFilter.USER_PROFILE);
        int userId = user.getId();
        switch (action) {
            case ACTION_FILTER:
                LOG.info(ACTION_FILTER);
                LocalDateTime start = LocalDateTime.of(
                        getLocalDate(request,"min"),
                        getLocalTime(request,"min"));
                LocalDateTime end = LocalDateTime.of(
                        getLocalDate(request,"max"),
                        getLocalTime(request,"max"));
                request.setAttribute(ATTR_MEAL_LIST, controller.getInRange(user, start, end));
                request.getRequestDispatcher("/mealList.jsp").forward(request, response);
                break;
            case ACTION_DELETE:
                LOG.info(ACTION_DELETE);
                int id = getId(request);
                LOG.info("Delete {}", id);
                controller.delete(userId, id);
                response.sendRedirect("meals");
                break;
            case ACTION_CREATE:
                Meal meal = new Meal(userId, LocalDateTime.now(), "", 1000);
                request.setAttribute(ATTR_MEAL, meal);
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                break;
            case ACTION_UPDATE:
                meal = controller.get(userId, getId(request));
                request.setAttribute(ATTR_MEAL, meal);
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                break;
            case ACTION_GET_ALL:
                LOG.info(ACTION_GET_ALL);
                request.setAttribute(ATTR_MEAL_LIST, controller.getAll(user));
                request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
    }

    private LocalDate getLocalDate(HttpServletRequest request, String edge) {
        LocalDate parse = null;
        boolean min = edge.equals("min");
        try {
            parse = LocalDate.parse(request.getParameter(min?PARAM_FILTER_START_DATE:PARAM_FILTER_END_DATE));
        } catch (Exception e) {
            parse = min ?LocalDate.MIN:LocalDate.MAX;
        }
        return parse;
    }

    private LocalTime getLocalTime(HttpServletRequest request, String edge) {
        LocalTime parse = null;
        boolean min = edge.equals("min");
        try {
            parse = LocalTime.parse(request.getParameter(min?PARAM_FILTER_START_TIME:PARAM_FILTER_END_TIME));
        } catch (Exception e) {
            parse = min ?LocalTime.MIN:LocalTime.MAX;
        }
        return parse;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter(PARAM_MEAL_ID));
        return Integer.valueOf(paramId);
    }
}
