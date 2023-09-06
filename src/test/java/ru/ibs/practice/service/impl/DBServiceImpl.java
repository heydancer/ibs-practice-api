package ru.ibs.practice.service.impl;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.ibs.practice.model.Food;
import ru.ibs.practice.model.FoodType;
import ru.ibs.practice.service.DBService;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DBServiceImpl implements DBService {
    private static final String SQL_SHOW_TABLES = "SHOW TABLES;";
    private static final String SQL_INSERT_FOOD = "INSERT INTO food(food_name, food_type, food_exotic) VALUES (?, ?, ?);";
    private static final String SQL_SELECT_ALL_FOOD = "SELECT * FROM food;";
    private static final String SQL_SELECT_FOOD_NAME = "SELECT * FROM food WHERE food_name = ?;";
    private static final String SQL_DELETE_FOOD = "DELETE FROM food WHERE food_name = ?;";
    private final JdbcTemplate jdbcTemplate;

    public DBServiceImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Step("Добавлена новая запись в таблицу {productName}, {type}, {exoticStatus}")
    @Override
    public void addProduct(String productName, FoodType type, boolean exoticStatus) {
        log.info("Вставка новой записи в таблицу productName: {}, type: {}, exoticStatus: {}",
                productName, type, exoticStatus);

        jdbcTemplate.update(SQL_INSERT_FOOD, productName, type, exoticStatus);
    }

    @Step("Выполнен SQL-запроса для получения товара по названию {name}")
    @Override
    public Optional<Food> findByName(String name) {
        log.info("Выполнение SQL-запроса для получения товара по названию {}", name);

        try {
            Food food = jdbcTemplate.queryForObject(SQL_SELECT_FOOD_NAME, (rs, rowNum) -> Food.builder()
                    .id(rs.getInt("food_id"))
                    .name(rs.getString("food_name"))
                    .type(FoodType.valueOf(rs.getString("food_type")))
                    .exotic(rs.getBoolean("food_exotic"))
                    .build(), name);

            return Optional.ofNullable(food);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Step("Получен список товаров через SQL-запрос")
    @Override
    public List<Food> findAll() {
        log.info("Выполнение SQL-запроса для получения списка всех товаров таблицы 'FOOD'");

        return jdbcTemplate.query(SQL_SELECT_ALL_FOOD,
                (rs, rowNum) -> Food.builder()
                        .id(rs.getInt("food_id"))
                        .name(rs.getString("food_name"))
                        .type(FoodType.valueOf(rs.getString("food_type")))
                        .exotic(rs.getBoolean("food_exotic"))
                        .build());
    }

    @Step("Удален товара: {name} через SQL-запрос")
    @Override
    public void removeProductByName(String name) {
        log.info("Выполнение SQL-запроса для удаления записи с именем {}", name);

        jdbcTemplate.update(SQL_DELETE_FOOD, name);
    }

    @Step("Выполнена проверка наличия таблицы: {tableName} в схеме БД")
    @Override
    public boolean checkTable(String tableName) {
        log.info("Проверка наличия таблицы {} в схеме БД", tableName);

        List<String> tables = jdbcTemplate.query(SQL_SHOW_TABLES,
                (rs, rowNum) -> rs.getString(1));

        boolean tableExists = false;
        for (String table : tables) {
            if (table.equalsIgnoreCase(table)) {
                tableExists = true;
                break;
            }
        }

        return tableExists;
    }
}