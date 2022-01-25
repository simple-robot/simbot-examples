package simbot.example.listener

import love.forte.simboot.annotation.Filter
import love.forte.simboot.annotation.Listener
import love.forte.simbot.ExperimentalSimbotApi
import love.forte.simbot.action.replyIfSupport
import love.forte.simbot.event.ContinuousSessionContext
import love.forte.simbot.event.EventResult
import love.forte.simbot.event.FriendMessageEvent
import love.forte.simbot.event.waitingForOnMessage
import kotlin.time.Duration.Companion.minutes


@Listener
suspend fun FriendMessageEvent.myListen() {
    replyIfSupport("是的")
}


@OptIn(ExperimentalSimbotApi::class)
@Filter("绑定")
@Listener
suspend fun FriendMessageEvent.myListen3(sessionContext: ContinuousSessionContext): EventResult {
    val friend = friend()
    friend.send("请输入账号")
    val code: String =
        sessionContext.waitingForOnMessage(sourceEvent = this, timeout = 5.minutes) { event, _, provider ->
            provider.push(event.messageContent.plainText)
        }

    friend.send("绑定成功！账号: $code")
    return EventResult.truncate()
}