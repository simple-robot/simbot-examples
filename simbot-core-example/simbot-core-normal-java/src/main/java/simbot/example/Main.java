package simbot.example;

import kotlin.Unit;
import love.forte.simbot.Identifies;
import love.forte.simbot.application.Applications;
import love.forte.simbot.component.mirai.MiraiComponent;
import love.forte.simbot.component.mirai.bot.MiraiBotManager;
import love.forte.simbot.core.application.Simple;

import java.util.concurrent.ExecutionException;

/**
 * 这是一个在Java中直接使用 {@code simbot-core} 以及 {@code mirai}组件的示例。
 * <p>
 * {@code simbot-core} 作为基础模块，其使用方式可能会略显繁琐（在Java中尤为明显），
 * 但同时它相对于 {@code simbot-boot} 模块来讲拥有更高的自由度。
 * <p>
 * Java中，不建议直接使用 {@code simbot-core} 模块，毕竟如你所见，这很繁琐。
 *
 * @author ForteScarlet
 */
public class Main {

    /**
     * 你bot的账号
     */
    public static final long BOT_CODE = 0L;

    /**
     * 你bot的密码
     * 3.x中，也支持使用密码的MD5字节数组作为密码，而不是明文
     */
    public static final String BOT_PASS = "密码";

    // public static final byte[] PASS_MD5 = new byte[]{-24, 7, -15, -4, -8, 45, 19, 47, -101, -80, 24, -54, 103, 56, -95, -97};

    /**
     * Main
     *
     * @param args args
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final var builder = Applications.buildSimbotApplication(Simple.INSTANCE);

        builder.build((appBuilder, appConfig) -> {
            // builder.
            // 注册组件信息，例如Mirai的组件和bot管理器
            appBuilder.install(MiraiComponent.Factory, (cpConfig, perceivable) -> Unit.INSTANCE);
            appBuilder.install(MiraiBotManager.Factory, (bmConfig, perceivable) -> Unit.INSTANCE);

        });

        // 构建 SimpleApplication, 然后进行操作
        var waitingForApplication = builder.createAsync().thenCompose(simpleApplication -> {
            // 注册监听函数
            // 1. 得到监听函数管理器
            final var eventListenerManager = simpleApplication.getEventListenerManager();

            // 2. 注册各种监听函数
            // 好友消息监听配置
            MyFriendListenerConfig.config(eventListenerManager);
            // 群消息监听配置
            MyGroupListenerConfig.config(eventListenerManager);

            // 注册bot
            // 1. 得到Bot管理器集
            final var botManagers = simpleApplication.getBotManagers();
            // 2. 假如你要注册的是mirai的bot，寻找mirai的bot管理器
            for (final var provider : botManagers) {
                if (provider instanceof MiraiBotManager miraiBotManager) {
                    // 启动并启动bot
                    final var bot = miraiBotManager.register(BOT_CODE, BOT_PASS);
                    // 注册后的bot不会立刻启动，你可以继续注册，或尝试启动它。
                    final var botStartFuture = bot.startAsync();

                    // 在启动之后，你可以进行一些操作。
                    // 有些bot在启动之前也可能允许进行消息发送，而有些组件下的bot不允许，这取决于组件平台的特性。
                    // 如果你无法确定，那么建议启动后再进行操作。

                    botStartFuture.thenCompose(stated -> {
                        // 此处给另外一个账号发送一句话，比如给你自己的大号。
                        final var otherCode = 0L;
                        return bot.getFriendAsync(Identifies.ID(otherCode));
                    }).thenAccept(friend -> {
                        // 假设此好友存在
                        assert friend != null;
                        // 发送一个消息
                        friend.sendAsync("我好了");
                    });

                    // bot的注册、管理由对应的BotManager负责，且Bot应当仅能从BotManager中进行构建。
                    // bot的注册方法可能会有很多，不同的bot管理器会针对其所属的特定平台提供更加丰富的注册方法。

                }
            }

            // 其他操作都结束了，将 Application 转化为 Future 并返回
            return simpleApplication.asFuture();
        });

        // 阻塞并一直持续到 Application 被关闭
        waitingForApplication.get();
    }
}
