package simbot.example

import love.forte.simboot.spring.autoconfigure.EnableSimbot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * 如果想要开启simbot，使用 [EnableSimbot].
 *
 */
@EnableSimbot
@EnableR2dbcRepositories
@SpringBootApplication
@EnableScheduling // 启动定时任务
class SpringBootWebfluxKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringBootWebfluxKotlinApplication>(*args)
}
