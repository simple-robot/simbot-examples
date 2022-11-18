package simbot.example.task;

import lombok.RequiredArgsConstructor;
import love.forte.simbot.Identifies;
import love.forte.simbot.application.Application;
import love.forte.simbot.component.mirai.bot.MiraiBotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author ForteScarlet
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessageSendTask {

    /**
     * simbot启动后得到的 {@link Application}.
     *
     */
    private final Application application;


    /**
     * 定时任务
     */
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void task() {
        for (var botManager : application.getBotManagers()) {
            // 寻找到所需的botManager。例如 MiraiBotManager
            if (botManager instanceof MiraiBotManager miraiBotManager) {
                // 得到你想要的bot，例如列表中第一个bot
                final var miraiBot = miraiBotManager.all().get(0);

                // 例如向一个指定好友发送消息
                final var friend = miraiBot.getFriend(Identifies.ID(123));
                if (friend != null) {
                    friend.sendBlocking("现在时间: " + LocalDateTime.now());
                }

                break;
            }
        }
    }
}
