package RSA;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Base64;

public class RSAUtils {
    /**
     * ���ɹ�Կ��˽Կ, һ��һ��������, �洢���ļ��н��зַ���ʹ��
     */
    public static void generateKey() {  
        try {  
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");  
            kpg.initialize(1024);  //һ����˵, ����Ϊ2048����õ�, Ҳ���Ƽ���
            KeyPair kp = kpg.genKeyPair();  
            PublicKey pbkey = kp.getPublic();  
            PrivateKey prkey = kp.getPrivate();  
            // ���湫Կ  
            FileOutputStream f1 = new FileOutputStream("d:/pubkey.dat");  
            ObjectOutputStream b1 = new ObjectOutputStream(f1);  
            b1.writeObject(pbkey);  
            // ����˽Կ  
            FileOutputStream f2 = new FileOutputStream("d:/privatekey.dat");  
            ObjectOutputStream b2 = new ObjectOutputStream(f2);  
            b2.writeObject(prkey);  
        } catch (Exception e) {  
        }  
    }
    
    /**
     * ��Կ����, һ������ߴ�������, �ӱ��ش洢��ȡ��Կ���м���
     * @param plainTxt
     * @return
     * @throws Exception
     */
    public static String pubEncrypt(String plainTxt) throws Exception {  
        String s = Base64.encodeBase64String(plainTxt.getBytes("UTF-8"));  
        // ��ȡ��Կ������e,n  
        FileInputStream f = new FileInputStream("d:/pubkey.dat");  
        ObjectInputStream b = new ObjectInputStream(f);  
        RSAPublicKey pbk = (RSAPublicKey) b.readObject();  
        BigInteger e = pbk.getPublicExponent();  
        BigInteger n = pbk.getModulus();  
        // ��ȡ����m  
        byte ptext[] = s.getBytes("UTF-8");  
        BigInteger m = new BigInteger(ptext);  
        // ��������c  
        BigInteger c = m.modPow(e, n);  
        // ��������  
        String ciperTxt = c.toString();  
        return ciperTxt; 
    }
    
    /**
     * ˽Կ����, һ������ߴ�������, �ӱ��ش洢��ȡ˽Կ���н���
     * @param ciperTxt
     * @return
     * @throws Exception
     */
    public static String privDecrypt(String ciperTxt) throws Exception {  
        BigInteger c = new BigInteger(ciperTxt);  
        // ��ȡ˽Կ  
        FileInputStream f = new FileInputStream("d:/privatekey.dat");  
        ObjectInputStream b = new ObjectInputStream(f);  
        RSAPrivateKey prk = (RSAPrivateKey) b.readObject();  
        BigInteger d = prk.getPrivateExponent();  
        // ��ȡ˽Կ����������  
        BigInteger n = prk.getModulus();  
        BigInteger m = c.modPow(d, n);  
        // ��ʾ���ܽ��  
        byte[] mt = m.toByteArray();  
        String plainTxt = new String(Base64.decodeBase64(mt),"UTF-8");
        return plainTxt;
    }  
    public static void main(String args[]) {  
        try {  
            // generateKey();  
            String ciperTxt = pubEncrypt("���Դ��л�123��");
            System.out.println("��Կ�������ģ�" + ciperTxt);
            System.out.println("˽Կ���ܣ�" + privDecrypt(ciperTxt));  
        } catch (Exception e) {  
            System.out.println(e.toString());  
        }  
    }
}