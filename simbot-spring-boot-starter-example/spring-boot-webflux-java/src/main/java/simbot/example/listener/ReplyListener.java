package simbot.example.listener;

import lombok.RequiredArgsConstructor;
import love.forte.simboot.annotation.Listener;
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
        final var plainText = friendMessageEvent.getMessageContent().getPlainText().trim();

        final var replyMono = replyService.reply(plainText);

        /*
            Note: simbot支持对Reactive形式的返回值进行处理，其中就包括当前函数返回的 Mono，
            他们每次都会在此监听函数处理结束后立即处理。

            为了更好的利用 reactive api 的特性，我们更建议你配合使用异步API（xxxAsync）
         */

        return replyMono.flatMap(reply -> {
            if (reply == null) {
                return Mono.just(EventResult.defaults());
            }

            // friend mono
            final var friendMono = Mono.fromFuture(friendMessageEvent.getFriendAsync());

            // 发送消息,
            // 然后返回 EventResult.truncate 代表阻止后续其他监听函数的执行。
            return friendMono
                    .flatMap(friend -> Mono.fromFuture(friend.sendAsync(reply)))
                    .thenReturn(EventResult.truncate());
        });


    }

}
