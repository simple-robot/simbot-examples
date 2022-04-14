package simbot.example;

import love.forte.simbot.component.mirai.MiraiGroup;
import love.forte.simbot.component.mirai.MiraiMember;
import love.forte.simbot.component.mirai.event.MiraiGroupMessageEvent;
import love.forte.simbot.component.mirai.event.MiraiReceivedMessageContent;
import love.forte.simbot.core.event.CoreListenerManagerConfiguration;
import love.forte.simbot.core.event.CoreListenerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ForteScarlet
 */
public class MyGroupListenerConfig {


    /**
     * 配置注册一个群消息监听，并在控制台打印出收到的消息，不做回复。
     *
     * @param configuration 配置类
     */
    public static void config(CoreListenerManagerConfiguration configuration) {
        Logger logger = LoggerFactory.getLogger("群消息日志");

        /*
            你可能注意到了，这里监听的是 MiraiGroupMessageEvent 事件类型。
            这个事件是mirai组件所提供的，并不属于simbot-api的标准通用事件类型，
            虽然此事件实现了部分标准事件类型，但是它会更有针对性，功能会更丰富。
            但是相对的这种事件类型只能在其所属组件环境下存在。
         */
        configuration.addListener(CoreListenerUtil.newCoreListener(
                MiraiGroupMessageEvent.Key,
                (context, event) -> {
                    final MiraiReceivedMessageContent messageContent = event.getMessageContent();
                    final MiraiGroup group = event.getGroup();
                    final MiraiMember author = event.getAuthor();
                    logger.debug(
                            "「{}({})」在群「{}({})」发送了消息：{}",
                            author.getUsername(), author.getId(),
                            group.getName(), group.getId(),
                            messageContent.getOriginalMessageChain() // 这里是直接得到原生的mirai消息链
                    );
                }
        ));


    }

}
