package simbot.example.listener;

import lombok.RequiredArgsConstructor;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.EventResult;
import love.forte.simbot.event.FriendMessageEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
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
    public Mono<EventResult> reply(FriendMessageEvent friendMessageEvent) {
        final String plainText = friendMessageEvent.getMessageContent().getPlainText().trim();

        final Mono<String> reply = replyService.reply(plainText);

        /*
            Note: simbot支持对Reactive形式的返回值进行处理，其中就包括当前函数返回的 Mono，
            他们每次都会在此监听函数处理结束后立即处理。

            但是你可能发现了，simbot的部分API（例如 getFriend() 和 sendBlocking）依旧是 **阻塞的**，
            所以实际上，在Java下使用simbot + Reactive api的效果并不是很好，
            这也是为什么我更推荐你使用Kotlin配合协程, 或者直接使用java blocking api来使用的原因。
         */

        return reply.map(content -> {
            if (content == null) {
                return EventResult.defaults();
            } else {
                // 如果有，发送消息，并阻止后续事件的执行。
                final Friend currentFriend = friendMessageEvent.getFriend();
                currentFriend.sendBlocking(content);

                // 返回 EventResult.truncate 代表阻止后续其他监听函数的执行。
                return EventResult.truncate();
            }
        });


    }

}
