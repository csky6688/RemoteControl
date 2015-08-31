package hph.app.UITest;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


enum SendModels
{
	Send,SendAndRecieve
}
public class ServerMutual {
	private static final String ipInfoRequeString="getserver";
	public static final String MulticastAddress = "224.0.0.1";
	private static final int ServerMulticastPort=8888;
	private static final int ClientMulticastPort=7777;
	public static MulticastInfo GetServerAddressInfo() {
		MulticastRecieve mr=new InfoMulticastRecieve();
		sendMulticastMsg(ServerMulticastPort,ClientMulticastPort,ipInfoRequeString,mr);
		return (MulticastInfo)mr.recieveData;
	}
	
	public static MulticastInfo SendAuthenticationString(String authenticationString) {
		MulticastRecieve mr=new InfoMulticastRecieve();
		sendMulticastMsg(ServerMulticastPort,ClientMulticastPort,authenticationString,mr);
		return (MulticastInfo)mr.recieveData;
	}
	
	public static void sendMulticastMsg(int serverPort,int clientPort,String msg)
	{
		sendMulticastMsg(serverPort,clientPort,msg,null);
	}
	
	public static void sendMulticastMsg(int serverPort,int clientPort,String msg,MulticastRecieve mr){
		MulticastSocket socket=null; 	
		try {
			socket = new MulticastSocket(clientPort);  
			socket.setSoTimeout(500);
	        //IPЭ��Ϊ���㲥�ṩ�����������IP��ַ����ЩIP��ַ�ķ�Χ��224.0.0.0��239.255.255.255  
	        InetAddress address = InetAddress.getByName(MulticastAddress);
	        socket.joinGroup(address); 
	        DatagramPacket packet;  
	        //�������ݰ�  
	        //Log.v(TAG, "send packet");  
	        byte[] buf = msg.getBytes();  
	        packet = new DatagramPacket(buf, buf.length, address, serverPort);  
	        socket.send(packet);  
	       if (mr!=null) {
			mr.Recieve(socket);
	       }
	        socket.close();
	          
		} catch (Exception e) {
			// TODO: handle exception
			 socket.close();
		}
	}
}
