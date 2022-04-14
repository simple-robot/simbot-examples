package simbot.example;

import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.core.event.CoreListenerManagerConfiguration;
import love.forte.simbot.core.event.CoreListenerUtil;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.EventListenerRegistrar;
import love.forte.simbot.event.FriendMessageEvent;
import org.slf4j.Logger;
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
    public static void config(CoreListenerManagerConfiguration configuration) {
        // 此实例假设注册一个监听函数，
        // 它监听 好友消息事件，并在可以的情况下回复发消息的好友一句"是的"。

        // 好友消息事件属于一个simbot-api提供的标准通用事件类型，
        // 它可能被很多事件类型所实现, 引用范围大，同时api受限也比较大。

        final Logger logger = LoggerFactory.getLogger("好友消息事件");

        // 注册一个事件
        configuration.addListener(CoreListenerUtil.newCoreListener(
                FriendMessageEvent.Key, // 事件类型
                (context, event) -> {
                    logger.debug("收到消息消息, 其纯文本内容：「{}」", event.getMessageContent().getPlainText());
                    if (event instanceof ReplySupport) {
                        ((ReplySupport) event).replyBlocking("是的");
                    } else {
                        logger.error("事件「{}」不支持直接回复消息。", event);
                        // 事件本体无法进行回复的时候，尝试获取好友对象然后发送消息。
                        // 当然，你也可以选择一上来就这么做。
                        final Friend friend = event.getFriend();
                        friend.sendBlocking("是的");
                    }
                }
        ));
    }

}
