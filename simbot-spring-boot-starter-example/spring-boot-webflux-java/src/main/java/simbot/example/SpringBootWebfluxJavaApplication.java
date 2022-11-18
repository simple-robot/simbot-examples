package simbot.example;

import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Simbot 整合 Spring Boot. 如果想要开启 simbot, 需要标记 {@link EnableSimbot}.
 *
 * @author forte
 */
@EnableSimbot
@SpringBootApplication
@EnableScheduling // 启动定时任务
public class SpringBootWebfluxJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxJavaApplication.class, args);
    }

}
