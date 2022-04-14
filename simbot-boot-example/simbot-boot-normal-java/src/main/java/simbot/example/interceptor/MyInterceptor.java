package simbot.example.interceptor;

import love.forte.di.annotation.Beans;
import love.forte.simbot.ID;
import love.forte.simbot.event.BlockingEventProcessingInterceptor;
import love.forte.simbot.event.Event;
import love.forte.simbot.event.EventProcessingResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * 配置一个事件流程拦截器，此拦截器的作用：在日志中打印每一次完整的事件处理所使用的时间。
 *
 * @author ForteScarlet
 */
@Beans
public class MyInterceptor implements BlockingEventProcessingInterceptor {

    private static final Logger log = LoggerFactory.getLogger("处理时间");

    /**
     * 一个 {@code Event Processing} （事件流程） 拦截器，会针对每一个事件的推送而拦截一次。
     * 此拦截发生在所有监听函数执行之前。
     *
     * @param context 拦截上下文实例
     * @return 执行结果
     */
    @NotNull
    @Override
    public EventProcessingResult doIntercept(@NotNull Context context) {
        // 事件ID，用于日志
        final Event event = context.getEventContext().getEvent();
        final ID eventId = event.getId();
        // 开始时间
        final Instant start = Instant.now();

        // 执行正常的后续流程
        final EventProcessingResult process = context.proceedBlocking();

        // 如果你想要进行"拦截"，阻止其正常的执行流程，
        // 那么就不需要执行 context.proceedBlocking,
        // 而是直接返回一个 EventProcessingResult 实例，
        // 比如 EventProcessingResult.Empty


        // 结束时间
        final Instant end = Instant.now();
        final Duration duration = Duration.between(start, end);
        log.info("事件「{}」处理时间: {}, Event: {}", eventId, duration, event);
        return process;
    }

}
