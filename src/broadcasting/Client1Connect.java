package broadcasting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**@author KR Tambo
 * @version miniproject 
 * 201707367
 * */

public class Client1Connect extends Thread  {
	
	public MulticastSocket castsocket;
	public String theIP = "230.0.0.0";	
	public InetAddress address;	
	public int listeningport = 4444;
	public boolean listening = true;		
	public String msgString=null;
	
	public String receiveMessage ()
	{
		
		//instantiating the listening class used by clients 
		try {
			castsocket = new MulticastSocket(listeningport);
			address = InetAddress.getByName(theIP);
			castsocket.joinGroup(address);
			
			while (listening)
			{
				byte [] mybyte = new byte [1024];
				System.out.println("Listening for a message");
				DatagramPacket thePacket = new DatagramPacket(mybyte,mybyte.length);
				castsocket.receive(thePacket);
				msgString = new String(thePacket.getData(),thePacket.getOffset(),thePacket.getLength());
				System.out.println("From Broadcast class we have " + msgString + "\n");
			}
			
			castsocket.leaveGroup(address);
			castsocket.close();
			
		} catch (IOException e) {
			
			System.out.println("Could not listen");
			e.printStackTrace();
		}
		return msgString;
	}
	
	
	//getters and setters
	
	  public String getMsgString() {
		return msgString;
	}




	public void setMsgString(String msgString) {
		this.msgString = msgString;
	}




	@Override public void run ()
	  { 
		  
		  receiveMessage(); 
		  
	  }
	 

}
