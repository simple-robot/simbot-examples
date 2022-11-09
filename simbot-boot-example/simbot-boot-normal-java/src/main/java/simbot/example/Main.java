package simbot.example;

import love.forte.simboot.annotation.Listener;
import love.forte.simboot.core.SimbootApp;
import love.forte.simboot.core.SimbootApplication;
import love.forte.simbot.event.internal.BotRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ForteScarlet
 */
@SimbootApplication
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final var context = SimbootApp.run(Main.class, args);
        // 直到 context.cancel 被调用之前，阻塞当前线程。
        context.launchBlocking();
    }


    /**
     * 此处监听"bot已经注册事件"，每当一个bot被注册便会触发此事件。
     * <p>
     * 需要注意的是，"注册"不代表"启动"。如果想要监听
     *
     * @param botRegisteredEvent bot已经注册事件
     */
    @Listener
    public void botRegistered(BotRegisteredEvent botRegisteredEvent) {
        LOGGER.info("Bot({}) 注册成功！", botRegisteredEvent.getBot());
    }
}
