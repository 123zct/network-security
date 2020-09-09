package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class client {
	BigInteger n1=new BigInteger("10403");
	BigInteger n2=new BigInteger("10403");
	BigInteger n3=new BigInteger("10403");
	BigInteger n4=new BigInteger("10403");
	BigInteger d1=new BigInteger("1349");
	BigInteger d2=new BigInteger("6277");
	BigInteger d3=new BigInteger("1057");
	BigInteger d4=new BigInteger("7243");
	BigInteger e1=new BigInteger("9149");
	BigInteger e2=new BigInteger("13");
	BigInteger e3=new BigInteger("193");
	BigInteger e4=new BigInteger("307");
	String bs,mw,qianming,nn_1;//获取消息以后将消息分割成bs：判断位，mw：明文位；qianming：签名位;dd:判断位做识别

	int vv =0,nn=0;
	char[] x=new char[1];
	
	
	
	String str3;

	String IP="172.20.10.7";
	int str6;
	int port=7777;
	Socket socket = null ;
	BufferedReader br = null;
	PrintWriter pw = null;
	boolean canWaiter=true;
	ChatRoom window = new ChatRoom();
	Waiter waiter;//创建一个线程
	public String username;
	public client(String name)
	{
		username=name;
		while(username.length()!=6)
		{
			username+=" ";
		}
		//window.txtMsg.append("vicvuewf");
		window.frame.setVisible(true);
		try {
			//客户端socket指定服务器的地址和端口号
			socket = new Socket(IP, port);
			System.out.println("Socket=" + socket);
			
			/*建立一个客户端UI*/

			//同服务器原理一样
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));

			waiter =new Waiter();//建立线程
			waiter.start();//启动线程
			pw.println(username+"进入聊天室=====================================================================================================================================================================================================================================================进入聊天室");
			pw.flush();
			window.button_1.addActionListener(new ActionListener() {//界面触发事件,发送消息
				public void actionPerformed(ActionEvent e) {
					System.out.println(window.textField_2.getText());
					//window.txtMsg.append(window.textField_2.getText());
					sendMsg(window.textField_2.getText());
					window.textField_2.setText("");
					window.textField_2.requestFocus();//重置文本框
				}
			});
			window.btnNewButton_1.addActionListener(new ActionListener() {//退出
				public void actionPerformed(ActionEvent e) {
					window.frame.dispose();
					sendexit("退出聊天室");
//					try {
//						pw.close();
//						br.close();
//						socket.close();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
					
				}
			});
			window.btnNewButton.addActionListener(new ActionListener() {//显示用户名
				public void actionPerformed(ActionEvent e) {
					window.textField.setText(username);
				}
			});
//			pw.println("END");
//			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}


	private void sendMsg(String str){//发送消息
		try{
			SimpleDateFormat formater=new SimpleDateFormat("HH:mm:ss");//输出实时时间
			String time=formater.format(new Date());
			System.out.println("strtime========================"+time);
			if(str.length()<10) {
				while(str.length()<10) {
					str="0"+str;
					//补零操作，使原始数据格式固定为10位
				}
			}
			System.out.println("str补零========================"+str);
            BigInteger c=new BigInteger(datatest.test.hashcode(str));
            System.out.println("str哈希========================"+c);
			String ddd=c.toString(),zzz,yyy;
			System.out.println("strBiginter转Sring========================"+ddd);
			zzz=ddd.substring(0, 4);
			System.out.println("str分半========================"+zzz);
			yyy=ddd.substring(4);
			System.out.println("str分半========================"+yyy);
		    BigInteger c1=new BigInteger(zzz);
			BigInteger c2=new BigInteger(yyy);
			c1=c1.modPow(d4, n4);
			System.out.println("str加密========================"+c1);
			c2=c2.modPow(d4, n4);//将hash过的msg分两半加密=====签名{分两半原因是原数求平方太大}
			System.out.println("str加密========================"+c2);
			str=str+c1+" "+c2;
			System.out.println("str合并========================"+str);
			
			
			
			
			
			//username+":"+time+"  "+str+c1+" "+c2;
			
			pw.println(username+time+str);//向服务器发送消息
			System.out.println("str合并========================"+str);
			//pw.println(time+"  "+str);//向服务器发送消息
			pw.flush();
		}catch(Exception ee){
			JOptionPane.showMessageDialog(null, "发送消息失败！");
		}
	}
	private void sendexit(String str){//退出
		try{
			pw.println(username+str);//向服务器发送消息
//			canWaiter=false;
			pw.flush();
//			pw.close();
//			br.close();
//			socket.close();
		}catch(Exception ee){
			JOptionPane.showMessageDialog(null, "发送消息失败！");
		}
	}
	
	private class Waiter extends Thread{
		public void run(){
		//window.frame.setVisible(true);
		while(canWaiter){
				String msg;
				try {
					BufferedReader br2 = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					String dd=" ";
					msg = br2.readLine();
					if(50>msg.length()&&msg.length()>25) {
					System.out.println("msg========================"+msg);
					String str2=msg.substring(6);
					System.out.println("str2========================"+str2);
					 str3=msg.substring(0,6);//username RSA判断
					System.out.println("str3========================"+str3);
					String str4=msg.substring(14,24);//补0的明文
					System.out.println("str4========================"+str4);
					String str5=msg.substring(24);//签名
					System.out.println("str5========================"+str5);
			        String[] sArray=str5.split(" ") ;
			        str6=Integer.parseInt(str4);
			        
			        BigInteger qianming_1=new BigInteger(sArray[0]);
			        System.out.println("sArray[0]======================="+sArray[0]);
					BigInteger qianming_2=new BigInteger(sArray[1]);
					System.out.println("sArray[1]======================="+sArray[1]);
					switch(str3) {
					case ("00    "):dd=(qianming_1.modPow(e1, n1)).toString()+(qianming_2.modPow(e1, n1)).toString();break;
					case ("01    "):dd=(qianming_1.modPow(e2, n2)).toString()+(qianming_2.modPow(e2, n2)).toString();break;
					case ("10    "):dd=(qianming_1.modPow(e3, n3)).toString()+(qianming_2.modPow(e3, n3)).toString();break;
					case ("11    "):dd=(qianming_1.modPow(e4, n4)).toString()+(qianming_2.modPow(e4, n4)).toString();break;//dd:数字签名解密后的数据==原始消息hash后的消息
					}
					System.out.println("dd========================"+dd);
					String yuan=datatest.test.hashcode(str4);
					System.out.println("yuan0========================"+yuan);
					if(yuan.equals(dd)) {
						
						window.txtMsg.append("消息是来自"+str3+"本人"+"\n");
						System.out.println("消息是来自"+str3+"本人"+"\n");
					}
					
					
					
					
					
					}
					
					if(msg.equals("exit"))//接收Application断开连接回应
					{
						break;
					}
				 //复制输入流上的内容
					if(!msg.equals(null))
					window.txtMsg.append(str3+": "+str6+"\n");
					//window.txtMsg.append(msg+"\n");
					System.out.println(msg);
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//txtMsg.append(time+"  "+name1+"："+msg+"\n");
			}
		}
	}
	public static void main(String[] args) {
		
		//new client("df");
		
	}

}



