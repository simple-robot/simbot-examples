package simbot.example;

import love.forte.simbot.Identifies;
import love.forte.simbot.component.mirai.MiraiBot;
import love.forte.simbot.component.mirai.MiraiBotManager;
import love.forte.simbot.component.mirai.MiraiFriend;
import love.forte.simbot.core.event.CoreListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这是一个在Java中直接使用 {@code simbot-core} 以及 {@code mirai}组件的示例。
 * <p>
 * {@code simbot-core} 作为基础模块，其使用方式可能会略显繁琐（在Java中尤为明显），
 * 但同时它相对于 {@code simbot-boot} 模块来讲拥有更高的自由度。
 *
 * @author ForteScarlet
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    // 你bot的账号
    public static final long BOT_CODE = 0L;

    // 你bot的密码
    // 3.x中，也支持使用密码的MD5字节数组作为密码，而不是明文
    public static final String BOT_PASS = "密码";

    // public static final byte[] PASS_MD5 = new byte[]{-24, 7, -15, -4, -8, 45, 19, 47, -101, -80, 24, -54, 103, 56, -95, -97};

    /**
     * Main
     * @param args args
     */
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

        // 这里改成你自己bot的账号密码
        final MiraiBot bot = miraiBotManager.register(BOT_CODE, BOT_PASS);

        // 注册后的bot不会立刻启动，你可以继续注册，或尝试启动它。
        bot.startBlocking();

        // 在启动之后，你可以进行一些操作。
        // 有些bot在启动之前也可能允许进行消息发送，而有些组件下的bot不允许，这取决于组件平台的特性。
        // 如果你无法确定，那么建议启动后在进行操作。

        // 此处给一个账号发送一句话，比如给你自己的大号。
        final MiraiFriend friend = bot.getFriend(Identifies.ID(0));
        assert friend != null;
        friend.sendBlocking("我好了");


        // 你可以将它转化为一个 Future 进行操作。
        /*
            final Future<Unit> integerFuture = bot.toAsync();
            try {
                integerFuture.get(); // 等待结束
            } catch (InterruptedException | ExecutionException e) {
                // 被中断
                LOGGER.error("我报错了", e);
            }
         */


        // 当然，你也可以直接等待整个 botManager 的结束。
        // botManager的结束通常由使用者或OriginBotManager控制，它不会因为bot全部关闭而结束，
        // 因此尽管你终止了所有的bot，botManager依旧会处于存活状态。
        miraiBotManager.waiting();


    }
}
