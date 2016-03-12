package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends InjectServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);


    public static final String USERMEAL = "meal";
    public static final String USERMEAL_VIEW_COLLECTION = "meals";

    public static final String USERMEAL_ID = "id";
    public static final String USERMEAL_DESCRIPTION = "descr";
    public static final String USERMEAL_CALORIES = "cal";
    public static final String USERMEAL_DATETIME = "date";

    public static final String CREATE = "create";
    public static final String READ = "all";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    UserMealRepository userMealRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long id;
        String action = req.getParameter("action");
        switch (null == action ? READ : action) {
            case CREATE:
                LOG.info("create action");
                req.setAttribute(USERMEAL, new UserMeal(TimeUtil.now(), "", 0));
                req.getRequestDispatcher("WEB-INF/jsp/mealEdit.jsp").forward(req, resp);
                break;
            case UPDATE:
                id = Long.parseLong(req.getParameter(USERMEAL_ID));
                req.setAttribute(USERMEAL, userMealRepository.get(id));
                LOG.info("update action: id[{}]", id);
                req.getRequestDispatcher("WEB-INF/jsp/mealEdit.jsp").forward(req, resp);
                break;
            case DELETE:
                id = Long.parseLong(req.getParameter(USERMEAL_ID));
                LOG.info("delete action: id[{}]", id);
                userMealRepository.delete(id);
                resp.sendRedirect("meals");
                break;
            case READ:
                req.setAttribute(USERMEAL_VIEW_COLLECTION, UserMealsUtil.getExceeded(userMealRepository.getAll()));
                req.getRequestDispatcher("WEB-INF/jsp/mealList.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserMeal entity = new UserMeal(
                Long.parseLong(req.getParameter(USERMEAL_ID)),
                LocalDateTime.parse(req.getParameter(USERMEAL_DATETIME)),
                req.getParameter(USERMEAL_DESCRIPTION),
                Integer.parseInt(req.getParameter(USERMEAL_CALORIES))
        );
        LOG.info(entity.getId() == -1 ? "Created {}" : "Updated {}", entity);
        userMealRepository.save(entity);
        resp.sendRedirect("meals");
    }
}
