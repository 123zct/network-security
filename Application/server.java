package Application;
  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class server {
	String dd;
	BigInteger n1=new BigInteger("10403");
	BigInteger n2=new BigInteger("10403");
	BigInteger n3=new BigInteger("10403");
	BigInteger n4=new BigInteger("10403");
	BigInteger e1=new BigInteger("9149");
	BigInteger e2=new BigInteger("13");
	BigInteger e3=new BigInteger("193");
	BigInteger e4=new BigInteger("307");

	public static int PORT = 7777;
	SUI window = new SUI();
	public List<serverone> clients = new ArrayList<serverone>(); //����ͻ����߳���
	public server()
	{
		
		ServerSocket s = null;
		Socket socket  = null;
		try {
			s = new ServerSocket(PORT);
			
			window.frame.setVisible(true);
			
			//�ȴ������󡢷���һֱ����
			while(true){
				socket = s.accept();
				
				//System.out.println("socket:"+socket);
				//window.txtMsg1.append(socket+"\n");
				serverone c=new serverone(socket);
				 //new Thread(c).start(); //�����߳�
	                clients.add(c); //����߳���
			}
		} catch (Exception e) {
			try {
				//socket.close();
			} catch (Exception e1) {
			
				e1.printStackTrace();
			}
		}finally{
			try {
				//s.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		

	}
	
	public class serverone extends Thread{

		private Socket socket = null;
		private BufferedReader br = null;
		private PrintWriter pw = null;
		
		public serverone(Socket s){
			socket = s;
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				start();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		void sendall(String str)
		{	
	        try {  
	        	
	            pw.println(str);//����Ϣ������ͻ���
	           // window.txtMsg1.append(str+"\n");
	            pw.flush();
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		public void run() {
			while(true){
				String str,str2;
				try {
					str = br.readLine();
					str2=str.substring(6);
					String str3=str.substring(0,6);//username RSA�ж�
					String str4=str.substring(14,24);//��0������
					String str5=str.substring(24);//ǩ��
			        String[] sArray=str5.split(" ") ;
			        BigInteger qianming_1=new BigInteger(sArray[0]);
					BigInteger qianming_2=new BigInteger(sArray[1]);
					switch(str3) {
					case ("00    "):dd=(qianming_1.modPow(e1, n1)).toString()+(qianming_2.modPow(e1, n1)).toString();break;
					case ("01    "):dd=(qianming_1.modPow(e2, n2)).toString()+(qianming_2.modPow(e2, n2)).toString();break;
					case ("10    "):dd=(qianming_1.modPow(e3, n3)).toString()+(qianming_2.modPow(e3, n3)).toString();break;
					case ("11    "):dd=(qianming_1.modPow(e4, n4)).toString()+(qianming_2.modPow(e4, n4)).toString();break;//dd:����ǩ�����ܺ������==ԭʼ��Ϣhash�����Ϣ
					}
					
					
					//username+":"+time+"  "+str+c1+" "+c2;
					String yuan=datatest.test.hashcode(str4);
					if(yuan.equals(dd)) {//
						pw.println("��Ϣ������"+str3+"����"+"\n");
					
					
					for(serverone e:clients){//�������
						//window.txtMsg1.append(str+"\n");
		              
						e.sendall(str+"\n");
					}
					if(str2.equals("�˳�������")){
						window.txtMsg1.append(str+"\n");
						System.out.println(str2);
						br.close();
						pw.close();
						socket.close();
						
					}
					if(str2.equals("����������")){
						window.txtMsg1.append(str+"��Kerberos��֤ͨ����\n");
						System.out.println(str2);
						
					}
					System.out.println("Client Socket Message:"+str);
					
				}
				  //  pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
					
//					pw.println(""+str);
//					pw.flush();
				} catch (Exception e) {
					try {
						br.close();
						pw.close();
						socket.close();
					} catch (IOException e1) {
					
						e1.printStackTrace();
					}
				}
			}
		}
	}	
	public static void main(String[] args) {
		new server();

	}

}
