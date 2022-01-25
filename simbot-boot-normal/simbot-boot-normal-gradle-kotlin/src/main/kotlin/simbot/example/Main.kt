package simbot.example

import love.forte.simboot.SimbootApp
import love.forte.simboot.core.SimbootApplication

@SimbootApplication
class Main

suspend fun main(args: Array<String>) {
    SimbootApp.run(Main::class, *args).join()
}