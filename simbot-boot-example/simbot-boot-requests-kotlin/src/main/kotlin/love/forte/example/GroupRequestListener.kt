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

    /**
     * 群添加申请事件。bot应当拥有相应的权限才会收到。
     */
    @Listener
    @OptIn(ExperimentalSimbotApi::class)
    suspend fun GroupJoinRequestEvent.groupAdd() {
        logger.info("收到群申请！{}", this)
        logger.info("\t==> message:      {}", message)
        logger.info("\t==> requester:    {}", requester())
        logger.info("\t==> requester.id: {}", requester().id)
        logger.info("\t==> inviter.id:   {}", inviter()?.id)

        // 同意申请
        accept()

        // 拒绝申请: reject()

    }

    /**
     * 群成员增加事件。
     */
    @Listener
    suspend fun MemberIncreaseEvent.increase() {
        logger.info("群成员增加！{}", this)
        logger.info("\t==> source:       {}", source())
        logger.info("\t==> source.id:    {}", source().id)
        logger.info("\t==> actionType:   {}", actionType)
        logger.info("\t==> operator:     {}", operator())
        logger.info("\t==> operator?.id: {}", operator()?.id)
        logger.info("\t==> member:       {}", member())
        logger.info("\t==> member.id:    {}", member().id)
    }

}