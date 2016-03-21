package ru.javawebinar.topjava.repository.jdbc;

import org.postgresql.util.PGTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    public static final RowMapper<UserMeal> ROW_MAPPER = (rs, rowNum) -> new UserMeal(
            rs.getInt("id"),
            rs.getTimestamp("date_time").toLocalDateTime(),
            rs.getString("description"),
            rs.getInt("calories")
    );

    private static final String GET_MEALS_BETWEEN_DATES = "SELECT * FROM meals WHERE user_id=? AND date_time >= ? AND date_time <= ? ORDER BY date_time DESC";
    private static final String GET_MEALS_ALL = "SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC";
    private static final String GET_MEAL = "SELECT * FROM meals WHERE id=? AND user_id=?";
    private static final String DELETE_MEAL = "DELETE FROM meals WHERE id=? AND user_id=?";
    private static final String UPDATE_MEAL = "UPDATE meals SET date_time= :date_time, description= :description, calories= :calories WHERE id= :id AND user_id= :user_id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        insertMeal = new SimpleJdbcInsert(dataSource).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("user_id", userId)
                .addValue("date_time", PGTimestamp.valueOf(userMeal.getDateTime()))
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());
        if (userMeal.isNew()) {
            Number key = insertMeal.executeAndReturnKey(map);
            userMeal.setId(key.intValue());
        } else {
            namedParameterJdbcTemplate.update(UPDATE_MEAL, map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update(DELETE_MEAL, id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return jdbcTemplate.queryForObject(GET_MEAL, ROW_MAPPER, id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query(GET_MEALS_ALL, ROW_MAPPER, userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                GET_MEALS_BETWEEN_DATES,
                ROW_MAPPER,
                userId,
                PGTimestamp.valueOf(startDate),
                PGTimestamp.valueOf(endDate)
        );
    }
}
