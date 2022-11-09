package simbot.example;

import love.forte.simbot.core.event.SimpleListenerManagerConfiguration;
import love.forte.simbot.core.event.SimpleListeners;
import love.forte.simbot.event.EventListenerRegistrar;
import love.forte.simbot.event.FriendMessageEvent;
import org.slf4j.LoggerFactory;

/**
 * @author ForteScarlet
 */
public class MyFriendListenerConfig {


    /**
     * 提供一个事件监听注册器，向其中注册各种事件。
     * <p>
     * {@link EventListenerRegistrar} 由 {@link love.forte.simbot.event.EventListenerManager} 实现，
     * 因此可以直接使用事件管理器。
     *
     * @param configuration 配置类
     */
    public static void config(SimpleListenerManagerConfiguration configuration) {
        // 此实例假设注册一个监听函数，
        // 它监听 好友消息事件，并在可以的情况下回复发消息的好友一句"是的"。

        // 好友消息事件属于一个simbot-api提供的标准通用事件类型，
        // 它可能被很多事件类型所实现, 引用范围大，同时api受限也比较大。

        final var logger = LoggerFactory.getLogger("好友消息事件");

        // 注册一个事件
        configuration.addListener(SimpleListeners.listener(
                FriendMessageEvent.Key, // 事件类型
                (context, event) -> {
                    logger.debug("收到消息消息, 其纯文本内容：「{}」", event.getMessageContent().getPlainText());
                    event.replyBlocking("是的"); // 或使用 replyAsync
                }
        ));
    }

}
