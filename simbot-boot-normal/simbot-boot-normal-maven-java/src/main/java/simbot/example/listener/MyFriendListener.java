package simbot.example.listener;

import love.forte.di.annotation.Beans;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.Identifies;
import love.forte.simbot.PriorityConstant;
import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.ContinuousSessionContext;
import love.forte.simbot.event.ContinuousSessionProvider;
import love.forte.simbot.event.EventResult;
import love.forte.simbot.event.FriendMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 好友相关事件监听器。
 *
 * @author ForteScarlet
 */
@Beans
public class MyFriendListener {

    private static final Logger LOGGER = LoggerFactory.getLogger("好友消息");

    /**
     * 监听好友消息，并且回复这个好友一句"是的"。
     *
     * @param event 监听的事件对象
     */
    @Listener
    public void friendListen(FriendMessageEvent event) {
        final Friend friend = event.getFriend();
        LOGGER.info("friend: {}({})", friend.getUsername(), friend.getId());
        LOGGER.info("message: {}", event.getMessageContent().getPlainText());
        // 回复消息你可以：
        // 1. 先判断 event 事件对象是否允许"回复"，在允许的情况使用"reply(reply)", 不允许则通过获取好友来直接发送消息。
        // 2. 直接获取好友发送消息，不通过事件回复。
        // 下面的示例选择方案1
        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking("是的");
        } else {
            friend.sendBlocking("是的");
        }

    }

    /**
     * 如果好友发送的消息是"你好"，那么回复一句"你也好"
     */
    @Filter("你好")
    @ContentTrim // 将filter所需的匹配内容进行 trim 操作。
    @Listener
    public void friendListen2(FriendMessageEvent event, ContinuousSessionContext sessionContext) {
        // 这里将会直接通过好友对象进行消息发送
        event.getFriend().sendBlocking("你也好");

    }

    /**
     * 此过滤器模拟一个 持续会话 的场景，场景如下：
     * <p>
     * 用户发送：绑定
     * <p>
     * bot回复：请输入账号
     * <p>
     * 用户下一句回复一个账号，bot回复：绑定成功：账号为：xxx
     * <p>
     * Tips:
     * <ul>
     *     <li>
     *         此监听函数最好设置为 @Listener(async = true) (异步执行的), 因为一个存在持续会话的监听函数可能会持续较长时间。
     *         此时如果此监听函数后续还有其他事件，那么就会影响到他们的执行。
     *     </li>
     *     <li>
     *         但是相对的，假如在你的预期内，此事件应该被执行一次，且直接阻止后续事件的执行，那么就不要使用异步执行，
     *         而是在执行完成后直接截断后续监听。
     *     </li>
     * </ul>
     *
     * @param event          事件本体
     * @param sessionContext 持续会话上下文
     */
    @Filter("绑定")
    @Listener(priority = PriorityConstant.PRIORITIZED_1) // 使用较高的优先级。
    public EventResult friendListen3(FriendMessageEvent event, ContinuousSessionContext sessionContext) throws InterruptedException {
        // 给出提示
        final Friend friend = event.getFriend();
        friend.sendBlocking("请输入账号");

        // 通过持续会话上下文等待此好友的下一个消息
        final String value = sessionContext.waitingForOnMessage(
                Identifies.ID("abc"),
                // Identifies.randomID(),  // 会话ID, 默认随机.
                30_000L,                // 超时时间，单位毫秒，超时后此会话将会被关闭. 默认为0, 即永不超时. 不建议使用默认
                event,                  // 一个消息事件
                (sessionEvent, context, provider) -> {
                    // 得到下一个事件中的文本消息
                    // final String plainText = sessionEvent.getMessageContent().getPlainText();

                    // 推送此文本
                    // provider.push(plainText);
                });

        String message;
        message = "绑定成功！账号为: " + value;

        friend.sendBlocking(message);


        // 返回 EventResult的truncate，以代表截断后续的事件执行，整个事件流程将会自此结束。
        return EventResult.truncate();
    }

}
