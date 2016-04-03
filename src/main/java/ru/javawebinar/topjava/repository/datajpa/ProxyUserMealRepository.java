package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    UserMeal findByIdAndUserId(int id, int userId);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    List<UserMeal> findByUserIdOrderByDateTimeDesc(int userId);

    List<UserMeal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT DISTINCT um FROM UserMeal um LEFT JOIN FETCH um.user WHERE um.id=?1 AND um.user.id=?2")
    UserMeal getWithUser(int id, int userId);
}
