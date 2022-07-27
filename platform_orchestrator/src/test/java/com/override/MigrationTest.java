package com.override;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.override.service.LessonStructureService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Тест для корректного применения миграций.
 * <p>
 * Разворачивается in-memory postgres база данных, которой задается порт,
 * после чего запускается оркестратор в test конфигурации с ddl-auto:validate
 * и проверяет что схема бд в миграции совпадает с моделями в коде.
 */
@ActiveProfiles("test")
@SpringBootTest(classes = PlatformOrchestratorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureMockMvc
@Slf4j
public class MigrationTest {
    private static int pgPort = 8123;
    private static EmbeddedPostgres pg = null;
    @MockBean
    private LessonStructureService lessonStructureService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void initPostgresDatabase() {
        try {
            pg = EmbeddedPostgres.builder()
                    .setPort(pgPort)
                    .start();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Test
    @DisplayName("Схема бд в миграции совпадает с моделями в коде")
    void checkLiquibase() {
    }
}
