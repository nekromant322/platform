import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class NotificationHelloWorldTest {

    @Test
    void passedTest() {
        Assertions.assertEquals(1, 1);
    }

    @Test
    void notPassedTest() {
        Assertions.assertEquals(1, 0);
    }

    @Test
    void logTest() {
        log.info("Log test completed!");
    }
}
