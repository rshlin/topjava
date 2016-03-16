package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.Set;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    private final int id;
    private Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    private int caloriesPerDay = MealsUtil.DEFAULT_CALORIES_PER_DAY;
    private boolean enabled = true;

    public LoggedUser(User user) {
        this.id = user.getId();
        this.caloriesPerDay = user.getCaloriesPerDay();
    }

    public LoggedUser(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {

        this.caloriesPerDay = caloriesPerDay;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public static int id() {
        return 1;
    }
}
