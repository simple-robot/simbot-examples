package simbot.example.listener;

import love.forte.di.annotation.Beans;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.event.FriendMessageEvent;

/**
 * 好友相关事件监听器。
 *
 * @author ForteScarlet
 */
@Beans
public class MyFriendListener {

    /**
     * 监听好友消息，并且回复这个好友一句"是的"。
     *
     * @param event 监听的事件对象
     */
    @Listener
    public void friendListen(FriendMessageEvent event) {
        // 回复消息你可以：
        // 1. 先判断 event 事件对象是否允许"回复"，在允许的情况使用"reply(reply)", 不允许则通过获取好友来直接发送消息。
        // 2. 直接获取好友发送消息，不通过事件回复。
        // 下面的示例选择方案1
        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking("是的");
        } else {
            event.getFriend().sendBlocking("是的");
        }

    }

    /**
     * 如果好友发送的消息是"你好"，那么回复一句"你也好"
     */
    @Filter("你好")
    @ContentTrim // 将filter所需的匹配内容进行 trim 操作。
    @Listener
    public void friendListen2(FriendMessageEvent event) {
        // 这里将会直接通过好友对象进行消息发送
        event.getFriend().sendBlocking("你也好");

    }

}
