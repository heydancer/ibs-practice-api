package ru.ibs.practice.config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DataSourceConfig {
    private final PropertiesConfig propConfig;

    private DataSourceConfig() {
        propConfig = new PropertiesConfig();
    }

    public static DataSource getDataSource() {
        DataSourceConfig instance = new DataSourceConfig();

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(instance.propConfig.get("db.url"));
        dataSource.setUser(instance.propConfig.get("db.username"));
        dataSource.setPassword(instance.propConfig.get("db.password"));

        return dataSource;
    }
}