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
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Autowired
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ((ApplicationContext) config.getServletContext().getAttribute(ApplicationContextListener.CONTEXT)).getAutowireCapableBeanFactory().autowireBean(this);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        int userId = ((LoggedUser) request.getSession().getAttribute(AuthorizationFilter.USER_PROFILE)).getId();

        Meal meal = new Meal(
                userId,
                id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        LoggedUser user = (LoggedUser) request.getSession().getAttribute(AuthorizationFilter.USER_PROFILE);
        if (null==user){
            user = new LoggedUser(0);
            request.getSession().setAttribute(AuthorizationFilter.USER_PROFILE, user);
        }
        int userId = user.getId();
        if (null == action) {
            LOG.info("getAll");
            request.setAttribute("mealList", controller.getAll(user));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else {
            if (action.equals("delete")) {
                int id = getId(request);
                LOG.info("Delete {}", id);
                controller.delete(userId,id);
                response.sendRedirect("meals");
            } else {
                final Meal meal = action.equals("create") ?
                        new Meal(userId, LocalDateTime.now(), "", 1000) :
                        controller.get(userId, getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
            }
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
