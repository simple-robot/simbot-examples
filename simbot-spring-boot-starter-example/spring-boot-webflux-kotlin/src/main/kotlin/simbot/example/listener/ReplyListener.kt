package simbot.example.listener

import love.forte.simboot.annotation.Listener
import love.forte.simbot.event.EventResult
import love.forte.simbot.event.FriendMessageEvent
import love.forte.simbot.event.inFriend
import org.springframework.stereotype.Service
import simbot.example.service.ReplyService

/**
 * simbot中的监听类
 *
 * @author ForteScarlet
 */
@Service
class ReplyListener(private val replyService: ReplyService) {

    /**
     * 参数中使用 [FriendMessageEvent], 代表监听好友消息事件。
     * 注意观察包路径，是 `love.forte.simbot.event.FriendMessageEvent` .
     *
     * @param friendMessageEvent 好友消息事件
     * @return 事件结果，用于控制事件流程。
     */
    @Listener
    suspend fun reply(friendMessageEvent: FriendMessageEvent): EventResult {
        val plainText = friendMessageEvent.messageContent.plainText.trim()
        // 如果没有可回复内容，不做任何处理。
        val reply = replyService.reply(plainText) ?: return EventResult.defaults()

        // 如果有，发送回复消息，并阻止后续事件的执行。
        friendMessageEvent.inFriend {
            send(reply)
        }

        return EventResult.truncate()
    }
}