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


/**
 * 接收一个好友消息事件 [FriendMessageEvent], 并实现如下会话场景:
 *
 * ```
 * > 好友：绑定                 (1)
 * > bot：请输入账号            (2)
 * > 好友：xxxx                (3)
 * > bot：绑定成功！账号：xxxx   (4)
 *```
 *
 * 你在测试的时候会发现，在进行第 `(3)` 步的时候，很有可能会触发 [myListen] 监听函数。
 *
 * 这属于「逻辑上」的正常现象，但很有可能不是你所期望的「业务上」的结果。
 * 目前阶段，你需要自行想办法解决这个问题，例如借助ID或者拦截器等方式在某些情况下阻止某些监听函数的执行。
 *
 *
 */
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