package simbot.example;

import love.forte.simbot.component.mirai.MiraiBotManager;
import love.forte.simbot.event.EventProcessor;

/**
 * @author ForteScarlet
 */
public class MyBotManagerFactory {

    /**
     * 得到一个 mirai组件下的bot管理器 {@link MiraiBotManager}.
     * <p>
     * 几乎所有的bot管理器都应该需要一个事件处理器 {@link EventProcessor}. {@link EventProcessor} 由 {@link love.forte.simbot.event.EventListenerManager} 实现，
     * 因此直接提供监听事件管理器的实例即可。这也是为什么大多数情况下事件管理器应该是你第一个需要构建的东西。
     *
     * @param eventProcessor 事件处理器。
     * @return bot管理器
     */
    public static MiraiBotManager newManager(EventProcessor eventProcessor) {
        return MiraiBotManager.newInstance(eventProcessor);
    }

}
