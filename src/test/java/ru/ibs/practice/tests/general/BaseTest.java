package ru.ibs.practice.tests.general;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.ibs.practice.config.DataSourceConfig;
import ru.ibs.practice.service.DBService;
import ru.ibs.practice.service.impl.DBServiceImpl;
import ru.ibs.practice.service.APIService;
import ru.ibs.practice.service.impl.APIServiceImpl;

public class BaseTest {
    protected static DBService dbService;
    protected static APIService apiService;

    @BeforeEach
    public void checkTableInDb(){
       Assertions.assertTrue(dbService.checkTable("FOOD"),
               "Таблица не найдена в БД");
    }

    @BeforeAll
    public static void setUp() {
        dbService = new DBServiceImpl(DataSourceConfig.getDataSource());
        apiService = new APIServiceImpl();
    }
}