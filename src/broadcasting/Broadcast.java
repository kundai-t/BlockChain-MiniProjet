package broadcasting;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Broadcast extends Thread {
	
	public static DatagramSocket serverDatagramSocket1;
	public static DatagramPacket thePacket;
	public static BufferedReader bufferedReader;
	public static InetAddress inet;
	public String address = "230.0.0.0";
	public static byte mybyte [] = new byte[1024];
	public boolean broadcastrun; 	
	public int serverport = 7020, clientport1 = 9877, clientport2 = 8867, client1listening = 4444;
	
	
	public Broadcast ()
	{
		
		try {
			
			serverDatagramSocket1 = new DatagramSocket(serverport);			
			
			System.out.println("**** Broadcast is ready for Connection **** \n");
			
		} catch (SocketException e) {
			
			e.printStackTrace();
			System.out.println("Socket connection Error");
			
		}		
		
	}	
	
		public void run() {
			
			broadcastrun = true; 
			thePacket = new DatagramPacket(mybyte,mybyte.length);
			
			while (broadcastrun)
			{				
				
				try {					
					
					serverDatagramSocket1.receive(thePacket);					
					String datastring = new String(thePacket.getData(),0,thePacket.getLength());
					inet = InetAddress.getLocalHost();
					
					if (datastring.equals("CONNECT") && (thePacket.getPort()==clientport1)) {						
											
						String reply = "Client One Connected Succesfully";						
						byte [] sbyte = reply.getBytes();						
						serverDatagramSocket1.send(new DatagramPacket(sbyte, sbyte.length,inet,clientport1));		
						System.out.println("Client 1 Connected");	
					}
					
					if (!datastring.equals("CONNECT") && (thePacket.getPort())==clientport1) {							
						  
						  byte [] thebyte = datastring.getBytes(); 
						  DatagramPacket packet = new DatagramPacket(thebyte,thebyte.length,inet,client1listening);
						  serverDatagramSocket1.send(packet);			 
						
					}
					if (datastring.equals("CONNECT2") && (thePacket.getPort()==clientport2))
					{						
						int port = thePacket.getPort();
						String reply = "Client Two Connected Succesfully";
						byte [] thebyte = reply.getBytes();
						DatagramPacket pack = new DatagramPacket(thebyte,thebyte.length,inet,port);
						serverDatagramSocket1.send(pack);
					}
					if ((thePacket.getPort()==clientport2) && (!datastring.equals("CONNECT2"))) {
						
					//	System.out.println("This is the data sent from client 2 " + datastring + "\n");
																		
						mybyte = datastring.getBytes();
						thePacket = new DatagramPacket(mybyte,mybyte.length,inet,client1listening);
						serverDatagramSocket1.send(thePacket);
					}
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
					System.out.println("Packet not received");
				}
				
				
				
			}
			
		}
	

	
	

}
