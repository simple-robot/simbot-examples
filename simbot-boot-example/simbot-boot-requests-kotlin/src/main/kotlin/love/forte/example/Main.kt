package love.forte.example

import love.forte.simboot.SimbootApp
import love.forte.simboot.core.SimbootApplication

/**
 *
 * @author ForteScarlet
 */
@SimbootApplication class Main

suspend fun main(args: Array<String>) {
    SimbootApp.run(Main::class, *args).join()
}