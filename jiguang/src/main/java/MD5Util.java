
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhangbing on 16/5/24.
 */
public class MD5Util {

    public static String MD5(String plainText) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] cipherData = md5.digest(plainText.getBytes());
        StringBuilder builder = new StringBuilder();
        for(byte cipher : cipherData) {
            String toHexStr = Integer.toHexString(cipher & 0xff);
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
        }

        return builder.toString();
    }
}
