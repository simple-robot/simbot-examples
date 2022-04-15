package simbot.example.listener;

import lombok.RequiredArgsConstructor;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.EventResult;
import love.forte.simbot.event.FriendMessageEvent;
import org.springframework.stereotype.Service;
import simbot.example.service.ReplyService;

/**
 * simbot中的监听类
 *
 * @author ForteScarlet
 */
@Service
@RequiredArgsConstructor
public class ReplyListener {

    private final ReplyService replyService;


    /**
     * 参数中使用 {@link FriendMessageEvent}, 代表监听好友消息事件。
     * 注意观察包路径，是 {@code love.forte.simbot.event.FriendMessageEvent }.
     *
     * @param friendMessageEvent 好友消息事件
     * @return 事件结果，用于控制事件流程。
     * 如果你不打算控制整个监听流程，返回值也可以直接使用 void .
     */
    @Listener
    public EventResult reply(FriendMessageEvent friendMessageEvent) {
        final String plainText = friendMessageEvent.getMessageContent().getPlainText().trim();

        final String reply = replyService.reply(plainText);
        if (reply == null) {
            // 如果没有此消息没有对应的回应消息，跳过
            return EventResult.defaults();
        }

        // 如果有，发送消息，并阻止后续事件的执行。
        final Friend currentFriend = friendMessageEvent.getFriend();
        currentFriend.sendBlocking(reply);

        // 返回 EventResult.truncate 代表阻止后续其他监听函数的执行。
        return EventResult.truncate();

    }

}
