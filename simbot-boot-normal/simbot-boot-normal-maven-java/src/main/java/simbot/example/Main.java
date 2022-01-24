package simbot.example;

import love.forte.simboot.SimbootApp;
import love.forte.simboot.SimbootApplicationException;
import love.forte.simboot.SimbootContext;
import love.forte.simboot.core.SimbootApplication;

/**
 * @author ForteScarlet
 */
@SimbootApplication
public class Main {
    public static void main(String[] args) throws SimbootApplicationException {
        final SimbootContext context = SimbootApp.run(Main.class, args);

        // 直到 context.cancel 被调用
        context.joinBlocking();

    }
}
