package simbot.example;

import love.forte.simbot.component.mirai.MiraiBotManager;
import love.forte.simbot.core.event.CoreListenerManager;

/**
 * 这是一个在Java中直接使用 {@code simbot-core} 以及 {@code mirai}组件的示例。
 * <p>
 * {@code simbot-core} 作为基础模块，其使用方式可能会略显繁琐（在Java中尤为明显），
 * 但同时它相对于 {@code simbot-boot} 模块来讲拥有更高的自由度。
 *
 * @author ForteScarlet
 */
public class Main {
    public static void main(String[] args) {
        // 构建最主要的监听管理器，也就是事件处理器。
        final CoreListenerManager listenerManager = MyListenerManagerFactory.newManager();

        // 之后的步骤没有特别严格的前后顺序
        // 此处我们再构建一个mirai组件中的bot管理器
        final MiraiBotManager miraiBotManager = MyBotManagerFactory.newManager(listenerManager);

        // 接下来，我们可以开始注册一些监听函数，用于处理各种事件。
        // 通过事件管理器进行监听函数注册。
        MyFriendListenerConfig.config(listenerManager);




    }
}
