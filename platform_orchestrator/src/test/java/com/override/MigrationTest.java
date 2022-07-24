package com.override;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

@TestConfiguration
public class MigrationTest extends DatabaseAwareTestBase {

    @Test
    @DisplayName("Check Liquibase by PostgreSQL version")
    void checkLiquibaseByPostgresVersion() throws SQLException {
        try (Connection connection = EMBEDDED_POSTGRES.getTestDatabase().getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select version();")) {
                resultSet.next();
                final String pgVersion = resultSet.getString(1);
                assertThat(pgVersion).startsWith("PostgreSQL 13.7");
            }
        }
    }

}
