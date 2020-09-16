package RSA;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;


public class MD5 {
    static final String ALGORITHM_MD5 = "MD5";


    /*public static void main(String[] args) throws Exception {
        String source = "我是程序猿！我很骄傲！";
        printBase64(encryptionMD5(source));
    }*/

    static void printBase64(byte[] out) throws Exception {
        System.out.println(encodeBase64(out));
    }

    static byte[] encryptionMD5(String source) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
        md.update(source.getBytes("UTF-8"));
        return md.digest();
    }


    static String encodeBase64(byte[] source) throws Exception{
        return new String(Base64.encodeBase64(source), "UTF-8");
    }

}
