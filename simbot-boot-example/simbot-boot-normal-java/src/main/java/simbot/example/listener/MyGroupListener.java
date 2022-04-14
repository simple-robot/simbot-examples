package simbot.example.listener;

import love.forte.di.annotation.Beans;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.component.mirai.event.MiraiGroupMessageEvent;
import love.forte.simbot.component.mirai.event.MiraiReceivedMessageContent;
import love.forte.simbot.message.Messages;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 群相关事件
 *
 * @author ForteScarlet
 */
@Beans
public class MyGroupListener {
    private static final Logger log = LoggerFactory.getLogger("群消息");

    /**
     * 这里监听的是mirai组件所提供的特殊事件类型: {@link MiraiGroupMessageEvent}
     * 一个组件的特殊事件能够提供更丰富的特性，并且更有针对性。
     * <p>
     * <p>
     * 此事件在控制台打印所有的群消息，不做其他操作。
     *
     * @param event 事件本体
     */
    @Listener
    public void myGroupListen(MiraiGroupMessageEvent event) {
        // 事件发生的群
        final String groupName = event.getGroup().getName();
        final String authorName = event.getAuthor().getUsername();
        final MiraiReceivedMessageContent messageContent = event.getMessageContent();

        // nativeMessageChain是mirai中的原生事件对象
        // 只有在使用mirai组件下的特殊事件类型的时候才会有
        final MessageChain nativeMessageChain = messageContent.getOriginalMessageChain();

        // Messages 则是simbot提供的消息类型，
        // 无论如何都能获取到，只不过可能其中包含了mirai组件下提供的消息类型实现.
        final Messages messages = messageContent.getMessages();

        log.info("Messages: {}", messages);

        // 这里直接展示mirai的原生消息对象
        log.info("「{}」在「{}」里发送了消息：{}", authorName, groupName, nativeMessageChain);
    }


}
