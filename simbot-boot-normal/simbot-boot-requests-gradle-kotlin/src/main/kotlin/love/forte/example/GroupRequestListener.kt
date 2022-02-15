package love.forte.example

import love.forte.di.annotation.Beans
import love.forte.simboot.annotation.Listener
import love.forte.simbot.ExperimentalSimbotApi
import love.forte.simbot.event.GroupJoinRequestEvent
import love.forte.simbot.event.MemberIncreaseEvent
import org.slf4j.LoggerFactory

/**
 *
 * @author ForteScarlet
 */
@Beans
class GroupRequestListener {

    companion object {
        private val logger = LoggerFactory.getLogger(GroupRequestListener::class.java)
    }

    @Listener
    @OptIn(ExperimentalSimbotApi::class)
    suspend fun GroupJoinRequestEvent.groupAdd() {
        logger.info("收到群申请！{}", this)
        logger.info("message: {}", message)
        logger.info("requester: {}", requester())
        logger.info("requester.id: {}", requester().id)
        logger.info("inviter.id: {}", inviter()?.id)

        // 自动同意申请
        accept()
    }

    @Listener
    suspend fun MemberIncreaseEvent.increase() {
        logger.info("群成员增加！{}", this)
        logger.info("source: {}", source())
        logger.info("source.id: {}", source().id)
        logger.info("actionType: {}", actionType)
        logger.info("operator: {}", operator())
        logger.info("operator?.id: {}", operator()?.id)
        logger.info("target: {}", target())
        logger.info("target.id: {}", target().id)
    }

}