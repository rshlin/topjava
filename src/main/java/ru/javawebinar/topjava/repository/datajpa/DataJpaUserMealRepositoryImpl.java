package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    ProxyUserMealRepository proxy;

    @Autowired
    ProxyUserRepository userProxy;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        if (!userMeal.isNew() && null == get(userMeal.getId(), userId)) {
            return null;
        }

        userMeal.setUser(userProxy.findOne(userId));
        return proxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxy.findByIdAndUserId(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(userId, startDate, endDate);
    }

    public UserMeal getWithUser(int id, int userId) {
        return proxy.getWithUser(id, userId);
    }


}
