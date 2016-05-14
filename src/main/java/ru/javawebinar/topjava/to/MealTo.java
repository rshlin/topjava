package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.model.UserMeal;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MealTo {

    private Integer id;

    @NotNull(message = " must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @NotEmpty(message = " must not be empty")
    private String description;

    @NotNull(message = " must not be null")
    @Range(min = 10, max = 5000, message = " must be between 10 and 5000!")
    private int calories;

    public MealTo() {
    }

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public static MealTo asTo(UserMeal meal) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    public UserMeal asUserMeal() {
        return new UserMeal(id, dateTime, description, calories);
    }

    public UserMeal updateMeal(UserMeal meal) {
        meal.setCalories(calories);
        meal.setDateTime(dateTime);
        meal.setDescription(description);
        return meal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
