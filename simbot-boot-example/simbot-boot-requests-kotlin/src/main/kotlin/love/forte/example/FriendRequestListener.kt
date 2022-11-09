package love.forte.example

import love.forte.di.annotation.Beans
import love.forte.simboot.annotation.Listener
import love.forte.simbot.ExperimentalSimbotApi
import love.forte.simbot.event.FriendAddRequestEvent
import love.forte.simbot.event.FriendIncreaseEvent
import org.slf4j.LoggerFactory

/**
 *
 * @author ForteScarlet
 */
@Beans
class FriendRequestListener {


    companion object {
        private val logger = LoggerFactory.getLogger(FriendRequestListener::class.java)
    }

    /**
     * 好友添加申请事件
     */
    @OptIn(ExperimentalSimbotApi::class)
    @Listener
    suspend fun FriendAddRequestEvent.friendAdd() {
        logger.info("好友申请！{}", this)
        logger.info("\t==> requester:    {}", requester())
        logger.info("\t==> requester.id: {}", requester().id)
        logger.info("\t==> message:      {}", message)

        // 同意申请
        accept()

        // 拒绝申请: reject()
    }

    /**
     * 好友增加事件。
     */
    @Listener
    suspend fun FriendIncreaseEvent.friendIncrease() {
        logger.info("好友增加！{}", this)
        logger.info("\t==> friend:    {}", friend())
        logger.info("\t==> friend.id: {}", friend().id)
    }

}