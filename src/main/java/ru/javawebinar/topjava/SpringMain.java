package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.LoggedUser;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            System.out.println(adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN)));

            ProfileRestController profileRestController = appCtx.getBean(ProfileRestController.class);
            User user = profileRestController.get(0);
            LoggedUser loggedUser = new LoggedUser(user);

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll(loggedUser).stream().forEach(System.out::println);
            System.out.println();
            System.out.println();
            System.out.println();
            try {
                System.out.println(mealRestController.get(1, 1));
            } catch (NotFoundException e) {
                System.out.println("\nnotFoundException:get non existing meal\n");
            }
            try {
                System.out.println(mealRestController.get(5, 1));
            } catch (NotFoundException e) {
                System.out.println("\nnotFoundException:get non existing user\n");
            }
            mealRestController.save(new Meal(1, LocalDateTime.now(), "descr", 1221));
            mealRestController.save(new Meal(1, LocalDateTime.now(), "descr2", 122));
            try {
                mealRestController.delete(1, 3);
            } catch (NotFoundException e) {
                System.out.println("\nnotFoundException:delete\n");
            }
            System.out.println("\nget in range\n");
            mealRestController.getInRange(
                    loggedUser,
                    LocalDateTime.of(2015, Month.MAY, 30, 0, 0),
                    LocalDateTime.of(2015, Month.MAY, 30, 23, 0))
                    .stream()
                    .forEach(System.out::println);
        }
    }
}
