package simbot.example

import love.forte.di.annotation.Beans
import love.forte.simbot.event.EventProcessingInterceptor
import love.forte.simbot.event.EventProcessingResult
import org.slf4j.LoggerFactory
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

/**
 *
 * 这是一个自定义拦截器，其作用为拦截每一次的完整事件处理流程，
 * 并计算整个事件的处理时长。
 *
 * @author ForteScarlet
 */
@Beans
class MyInterceptor : EventProcessingInterceptor {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(MyInterceptor::class.java)
    }
    @OptIn(ExperimentalTime::class)
    override suspend fun intercept(context: EventProcessingInterceptor.Context): EventProcessingResult {
        val timedValue = measureTimedValue {
            context.proceed()
        }
        val event = context.eventContext.event
        LOGGER.info("事件「{}」处理时长: {}", event.id, timedValue.duration)
        return timedValue.value
    }
}