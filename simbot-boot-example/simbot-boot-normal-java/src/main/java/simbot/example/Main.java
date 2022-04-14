package simbot.example;

import love.forte.simboot.SimbootApp;
import love.forte.simboot.SimbootApplicationException;
import love.forte.simboot.SimbootContext;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.core.SimbootApplication;
import love.forte.simbot.event.internal.BotRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * @author ForteScarlet
 */
@SimbootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger("Main");

    public static void main(String[] args) throws SimbootApplicationException, ExecutionException, InterruptedException {
        final SimbootContext context = SimbootApp.run(Main.class, args);

        // 直到 context.cancel 被调用
        context.joinBlocking();

    }


    /**
     * 此处监听"bot已经注册事件"，每当一个bot被注册便会触发此事件。
     *
     * 需要注意的是，"注册"不代表"启动"。如果想要监听
     *
     * @param botRegisteredEvent bot已经注册事件
     */
    @Listener
    public void botRegistered(BotRegisteredEvent botRegisteredEvent) {
        log.info("Bot({}) 注册成功！", botRegisteredEvent.getBot());
    }
}
