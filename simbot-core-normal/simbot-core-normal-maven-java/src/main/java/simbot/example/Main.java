package simbot.example;

import kotlin.Unit;
import love.forte.simbot.component.mirai.MiraiBot;
import love.forte.simbot.component.mirai.MiraiBotManager;
import love.forte.simbot.core.event.CoreListenerManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

        //// 好友消息
        MyFriendListenerConfig.config(listenerManager);

        //// 群聊消息
        MyGroupListenerConfig.config(listenerManager);


        // 注册bot
        // 事件注册完了，但是你还没有注册任何bot。
        // bot的注册、管理由对应的BotManager负责，且Bot应当仅能从BotManager中进行构建。
        // bot的注册方法可能会有很多，不同的bot管理器会针对其所属的特定平台提供更加丰富的注册方法。

        final MiraiBot bot = miraiBotManager.register(123, "密码");

        // 注册后的bot不会立刻启动，你可以继续注册，或尝试启动它。
        bot.startBlocking();

        // 你可以将它转化为一个 Future 进行操作。
        final Future<Unit> integerFuture = bot.toAsync();
        try {
            integerFuture.get(); // 等待结束
        } catch (InterruptedException | ExecutionException e) {
            // 被中断
            e.printStackTrace();
        }

    }
}
