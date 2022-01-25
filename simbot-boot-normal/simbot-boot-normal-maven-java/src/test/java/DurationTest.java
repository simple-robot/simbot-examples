import java.time.Duration;
import java.time.Instant;

/**
 * @author ForteScarlet
 */
public class DurationTest {
    public static void main(String[] args) throws InterruptedException {
        final Instant start = Instant.now();
        Thread.sleep(5);
        final Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }
}
