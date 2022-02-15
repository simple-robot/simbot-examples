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

    @OptIn(ExperimentalSimbotApi::class)
    @Listener
    suspend fun FriendAddRequestEvent.friendAdd() {
        logger.info("好友申请！{}", this)
        logger.info("requester: {}", requester())
        logger.info("requester.id: {}", requester().id)
        logger.info("message: {}", message)

        // 自动同意申请
        accept()
    }

    @Listener
    suspend fun FriendIncreaseEvent.friendIncrease() {
        logger.info("好友增加！{}", this)
        logger.info("friend: {}", friend())
        logger.info("friend.id: {}", friend().id)
    }

}