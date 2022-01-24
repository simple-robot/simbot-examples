package simbot.example;

import kotlin.Unit;
import love.forte.simbot.core.event.CoreListenerManager;
import love.forte.simbot.core.event.CoreListenerManagerConfiguration;
import love.forte.simbot.event.EventResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author ForteScarlet
 */
public class MyListenerManagerFactory {

    /**
     * 构建应该最先构建的主要元素：{@link love.forte.simbot.event.EventListenerManager 事件监听管理器}。
     * 此处使用核心模块提供的默认实现 {@link CoreListenerManager}.
     *
     * @return listener manager
     */
    public static CoreListenerManager newManager() {

        // 首先构建一个配置类
        final CoreListenerManagerConfiguration configuration = new CoreListenerManagerConfiguration();

        // 你可以通过configuration配置一些对于这个监听管理器而言的全局属性，例如拦截器等。
        // 后续版本或许还会为Java提供定制调度器（线程池）的api。
        // 下面提供一个通过日志展示每一次完整事件处理流程后的处理时间。
        final Logger logger = LoggerFactory.getLogger("处理时间");
        configuration.interceptors(generator -> {
            generator.listenerIntercept(context -> {
                final long startNano = System.nanoTime();
                // 放行，进行下一步逻辑。
                final EventResult process = context.proceedBlocking();
                final long endNano = System.nanoTime();
                final long time = endNano - startNano;
                logger.debug("事件处理时间: {} ms, {} ns", TimeUnit.NANOSECONDS.toMillis(time), time);
                return process;
            });

            // Kotlin的lambda在Java中的兼容问题，此返回为固定写法。
            return Unit.INSTANCE;
        });


        // 提供配置类，得到管理器实例。
        return CoreListenerManager.newInstance(configuration);
    }
}
