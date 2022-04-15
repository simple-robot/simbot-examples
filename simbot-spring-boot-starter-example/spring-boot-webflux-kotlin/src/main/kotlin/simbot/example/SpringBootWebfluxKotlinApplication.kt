package simbot.example

import love.forte.simboot.autoconfigure.EnableSimbot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

/**
 * 如果想要开启simbot，使用 [EnableSimbot].
 *
 */
@EnableSimbot
@EnableR2dbcRepositories
@SpringBootApplication
class SpringBootWebfluxKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringBootWebfluxKotlinApplication>(*args)
}
