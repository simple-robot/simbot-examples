import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ForteScarlet
 */
public class Md5Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        String a = "1234567890";
        final MessageDigest digest = MessageDigest.getInstance("md5");
        final byte[] md5 = digest.digest(a.getBytes(StandardCharsets.UTF_8));
        for (byte b : md5) {
            System.out.print(b);
            System.out.print(", ");
        }

    }
}
