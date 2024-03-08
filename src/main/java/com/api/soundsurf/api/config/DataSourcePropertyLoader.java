package com.api.soundsurf.api.config;

import com.api.soundsurf.api.exception.DbConnectionCredentialException;
import com.api.soundsurf.api.exception.UnknownException;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class DataSourcePropertyLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    final String ENV_DB_USERNAME = "DB_USERNAME";
    final String ENV_DB_PASSWORD = "DB_PASSWORD";
    final String ENV_JDBC_URL = "DB_JDBC_URL";
    final String ENV_DB_DRIVER_CLASS = "DB_DRIVER_CLASS";
    final String ENV_DB_MAX_CONNECTION_SIZE = "DB_MAX_CONNECTION_SIZE";
    final String ENV_DB_HOST = "DB_HOST";
    final String ENV_DB_PORT = "DB_PORT";
    final String ENV_DB_CATALOG = "DB_CATALOG";
    final String SQL_MODE = "STRICT_TRANS_TABLES";
    final String MYSQL_JDBC_URL_FORMAT = "jdbc:mysql://%s:%s/%s?useSSL=false&autoReconnect=true&validationQuery=select 1&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&sessionVariables=sql_mode=%s";

    @Override
    public void onApplicationEvent(final ApplicationEnvironmentPreparedEvent event) {
        System.out.println("abcaabdbdc");
        try {
            final var environment = event.getEnvironment();
            buildDataSourceProperties(environment);
        } catch (Throwable ex) {
            throw new UnknownException(ex.getMessage());
        }
    }

    private void buildDataSourceProperties(final ConfigurableEnvironment environment) {
        final String username = System.getenv(ENV_DB_USERNAME);
        final String password = System.getenv(ENV_DB_PASSWORD);
        final String dbHost = System.getenv(ENV_DB_HOST);
        final String dbPort = System.getenv(ENV_DB_PORT);
        final String dbCatalog = System.getenv(ENV_DB_CATALOG);
        final String jdbcUrl = System.getenv(ENV_JDBC_URL) == null ? String.format(MYSQL_JDBC_URL_FORMAT, dbHost, dbPort, dbCatalog, SQL_MODE) : System.getenv(ENV_JDBC_URL);

        checkDbCredentialNotNull(jdbcUrl, username, password);

        final String driverClassName = System.getenv(ENV_DB_DRIVER_CLASS) == null ? (jdbcUrl.startsWith("jdbc:mysql") ? com.mysql.cj.jdbc.Driver.class.getName() : System.getenv(ENV_DB_DRIVER_CLASS)) : System.getenv(ENV_DB_DRIVER_CLASS);
        final int maxConnectionSize = System.getenv(ENV_DB_MAX_CONNECTION_SIZE) == null ? 20 : Integer.parseInt(System.getenv(ENV_DB_MAX_CONNECTION_SIZE));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("spring.datasource.username", username);
        properties.put("spring.datasource.password", password);
        properties.put("spring.datasource.url", jdbcUrl);
        properties.put("spring.datasource.driver-class-name", driverClassName);
        properties.put("spring.datasource.initial-size", maxConnectionSize);
        properties.put("spring.datasource.max-total", maxConnectionSize);
        properties.put("spring.datasource.max-wait-millis", 3000);
        properties.put("spring.datasource.slow-query-threshold", 3000);
        final PropertySource<Map<String, Object>> propertySource = new MapPropertySource("db", properties);
        environment.getPropertySources().addLast(propertySource);

    }

    private void checkDbCredentialNotNull(final String jdbcUrl, final String username, final String password) {
        if (Objects.nonNull(jdbcUrl) && Objects.nonNull(username) && Objects.nonNull(password))
            return;

        throw new DbConnectionCredentialException();
    }
}
