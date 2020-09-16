package RSA;

public class MainTest {

    public static void main(String[] args) throws Exception {
        String filepath="E:/RSADemo/";
        RSAEncrypt.genKeyPair(filepath);
     //   System.out.println("--------------��Կ����˽Կ���ܹ���-------------------");
        String plainText="AAAABBBB��";
        //��Կ���ܹ���
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());
        String cipher= Base64.encode(cipherData);
        //˽Կ���ܹ���
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        String restr=new String(res);
        System.out.println("���ģ�"+plainText);
        System.out.println("���ģ�"+cipher);
        System.out.println("���ܣ�"+restr);
        System.out.println();



    }
}