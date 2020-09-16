package TGSSocket;

import DES.DES;
//256
public class Authenticatorc {
	 String IDc;
	 String ADc;
	 String TS3;
	 
	 //封装报文（Kc,tgs,IDC,ADC,TS3）
	 //         (对称密钥，C的用户标识符，C的网络地址，存活时间)
	 String enclosure(String password,String IDc,String ADc,String TS3)
	 {
		 String message=""; 
		 message=IDc+ADc+TS3;
		 DES des=new DES(); 
		//DES对称加密
		 message=des.Encryption(message,password);
		 return message;
		 
	 }
      
	 //解封Authenticatorc
	  @SuppressWarnings("null")
	 Authenticatorc deblocking(String message,String password)
	  {
		  DES des=new DES();
		  message=des.Decryption(message, password);

		  //判断长度是否符合标准
		  String[] result=null;
		  Authenticatorc a=new Authenticatorc();
		  if(message.length()==28)
		  {
		    //按固定格式截取报文
		    a.IDc=message.substring(0, 4);
		    a.ADc=message.substring(4, 16);
		    a.TS3=message.substring(16, 28);
		 
		  }
		  else 
		  {
			System.out.println("报文长度有误，请重新发送");
			
		  }

		  return a;
	  }

	 public static void main(String args[]){
		   Authenticatorc deal=new Authenticatorc();
	       //Authenticatorc b;
	       //实例deal封装报文
           String a = deal.enclosure("88888888", "0001", "192168001021", "170627212121");
           //System.out.println(a.length());

	       
	      }
}
