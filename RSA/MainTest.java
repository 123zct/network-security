package RSA;

public class MainTest {

    public static void main(String[] args) throws Exception {
        String filepath="E:/RSADemo/";
        RSAEncrypt.genKeyPair(filepath);
     //   System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText="AAAABBBB！";
        //公钥加密过程
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());
        String cipher= Base64.encode(cipherData);
        //私钥解密过程
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        String restr=new String(res);
        System.out.println("明文："+plainText);
        System.out.println("密文："+cipher);
        System.out.println("解密："+restr);
        System.out.println();



    }
}