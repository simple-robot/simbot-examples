package simbot.example.task

import kotlinx.coroutines.launch
import love.forte.simbot.ID
import love.forte.simbot.application.Application
import love.forte.simbot.component.mirai.bot.MiraiBotManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit


/**
 *
 * @author ForteScarlet
 */
@Component
class MessageSendTask(
    /**
     * simbot启动后得到的 [Application].
     */
    val application: Application,
) {
    
    /**
     * 定时任务。
     */
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    fun task() {
        // 寻找到所需的botManager。例如 MiraiBotManager
        val miraiBotManager = application.botManagers.firstOrNull { it is MiraiBotManager } as? MiraiBotManager ?: return
        // 得到你想要的bot，例如列表中第一个bot
        val bot = miraiBotManager.all().firstOrNull() ?: return
        
        // 例如利用bot启用异步并向某个好友发送消息
        bot.launch {
            bot.friend(123.ID)?.send("现在时间: ${LocalDateTime.now()}")
        }
    }
    
}