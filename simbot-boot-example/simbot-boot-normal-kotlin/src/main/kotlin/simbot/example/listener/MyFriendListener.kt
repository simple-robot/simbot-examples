package simbot.example.listener

import love.forte.simboot.annotation.Filter
import love.forte.simboot.annotation.Listener
import love.forte.simbot.ExperimentalSimbotApi
import love.forte.simbot.PriorityConstant
import love.forte.simbot.event.*


/**
 * 一个只要收到消息就会尝试回复"是的"的监听函数。
 */
@Listener
suspend fun FriendMessageEvent.myListen() {
    reply("是的")
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
 */
@OptIn(ExperimentalSimbotApi::class)
@Filter("绑定")
@Listener(priority = PriorityConstant.PRIORITIZED_9)
suspend fun FriendMessageEvent.myListen3(sessionContext: ContinuousSessionContext): EventResult {
    // 好友对象
    val friend = friend()
    
    // 发送一句提示
    friend.send("请输入账号")
    
    // 等待下一个消息
    val account: String = sessionContext {
        nextMessage(FriendMessageEvent).plainText
    }
    
    friend.send("绑定成功！账号: $account")
    
    // 阻断后续执行
    return EventResult.truncate()
}