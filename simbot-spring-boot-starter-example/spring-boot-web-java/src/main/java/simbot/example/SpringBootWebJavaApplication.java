package simbot.example;

import love.forte.simboot.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring的启动类。当你需要使用Simbot的时候，需要标记 {@link EnableSimbot}
 *
 * @author forte
 */
@EnableSimbot
@SpringBootApplication
public class SpringBootWebJavaApplication {

    /**
     * 正常流程启动Spring.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebJavaApplication.class, args);
    }

}
