package simbot.example.listener

import love.forte.simboot.annotation.Filter
import love.forte.simboot.annotation.Listener
import love.forte.simbot.ExperimentalSimbotApi
import love.forte.simbot.PriorityConstant
import love.forte.simbot.action.replyIfSupport
import love.forte.simbot.event.ContinuousSessionContext
import love.forte.simbot.event.EventResult
import love.forte.simbot.event.FriendMessageEvent
import love.forte.simbot.event.waitingForOnMessage
import kotlin.time.Duration.Companion.minutes


/**
 * 一个只要收到消息就会尝试回复"是的"的监听函数。
 */
@Listener
suspend fun FriendMessageEvent.myListen() {
    replyIfSupport("是的")
}


@OptIn(ExperimentalSimbotApi::class)
@Filter("绑定")
@Listener(priority = PriorityConstant.PRIORITIZED_9)
suspend fun FriendMessageEvent.myListen3(sessionContext: ContinuousSessionContext): EventResult {
    // 好友对象
    val friend = friend()

    // 发送一句提示
    friend.send("请输入账号")

    // 得到当前发送人的下一句话
    val code: String =
        sessionContext.waitingForOnMessage(sourceEvent = this, timeout = 5.minutes) { event, _, provider ->
            provider.push(event.messageContent.plainText)
        }

    friend.send("绑定成功！账号: $code")

    // 阻断后续执行
    return EventResult.truncate()
}