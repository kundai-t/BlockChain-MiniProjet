package gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import application.Transaction;
import blockchain.Block;
import blockchain.BlockChain;
import broadcasting.Client1Connect;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**@author KR Tambo
 * @version miniproject 
 * 201707367  
 * 
 * */

public class SellerUDP extends GridPane {
	
	
	
	//creating a server socket using Datagram 
	public static DatagramSocket clientSocket1, clientSocket2;
	public static DatagramPacket thePacket;
	public static BufferedReader buffRead;
	public static InetAddress inet;
	public int serverport = 7020, clientport1 = 9877, clientport2 = 8867, client1listening = 4444;
	public static byte buff [] = new byte [1024];
	List <String> myList = new ArrayList<String>();	
	
	
	private TextField nameField;
	private TextField surnameField;
	private TextField streeTextField;
	private TextField countryField;
	private TextField itemField;
	private TextField emailField;
	private TextField statusConnection;
	private Button orderButton;
	private Button connectButton;
	private Button retrieveButton;
	private Label sellername;	
	private Label sellerSurname;
	private Label sellerStreetAddress;
	private Label sellerCountry;
	private Label itemName;
	private Label sellerEmail;
	private Label buyerInformation;
	private TextArea receivedInfo;	
	
	//creating an instance of a client connection to listen to a certain port 
	Client1Connect myClient = new Client1Connect();		   
	BlockChain theBlockChain = new BlockChain();
	Transaction transaction = new Transaction(myList);	
	Block buyerBlock = new Block(new java.util.Date(), Arrays.asList(transaction));
	
	
	public SellerUDP () {
	
		
        
		sellername = new Label("Name");
		sellerSurname = new Label("Surname");
		sellerEmail = new Label("Email");
		sellerStreetAddress = new Label("Street Address");
		new Label("City");
		new Label("State");
		new Label("Phone number");
		sellerCountry = new Label("Country");
		itemName = new Label("Item bought");
		new Label("SID Number");
		new Label("CID Number");
		
		orderButton = new Button("Place Order");
		connectButton = new Button("Connect");
		retrieveButton = new Button("Retrieve");
		
		//aading the appropriate fields 
		nameField = new TextField();
		surnameField = new TextField();
		emailField = new TextField();
		streeTextField = new TextField();
		new TextField();
		new TextField();
		new TextField();
		countryField = new TextField();
		itemField = new TextField();
		statusConnection = new TextField();
		new TextField();
		new TextField();
		
		GridPane myPaneLeft = new GridPane();		
		myPaneLeft.setPadding(new Insets(10,11,12,13));
		myPaneLeft.setVgap(5);
		myPaneLeft.setHgap(5);
		
		myPaneLeft.add(sellername,0,0);
		myPaneLeft.add(nameField,1,0);
		myPaneLeft.add(sellerSurname,0,1);
		myPaneLeft.add(surnameField,1,1);
		myPaneLeft.add(sellerEmail,0,2);
		myPaneLeft.add(emailField,1,2);
		myPaneLeft.add(sellerStreetAddress,0,3);
		myPaneLeft.add(streeTextField,1,3);		
		myPaneLeft.add(sellerCountry,0,4);
		myPaneLeft.add(countryField,1,4);
		myPaneLeft.add(itemName,0,5);
		myPaneLeft.add(itemField,1,5);		
		myPaneLeft.add(connectButton,0,6);
		myPaneLeft.add(orderButton,1,7);
		myPaneLeft.add(statusConnection,1,6);
		statusConnection.setEditable(false);
	
		
		buyerInformation = new Label("Buyer Information");
		receivedInfo = new TextArea();		
		
		GridPane myPaneRight = new GridPane();
		myPaneRight.setPadding(new Insets(10,11,12,13));
		myPaneRight.setVgap(5);
		myPaneRight.setHgap(5);
		myPaneRight.add(buyerInformation,0,0);
		receivedInfo.setWrapText(true);
		receivedInfo.setEditable(false);
		receivedInfo.setMaxWidth(400);
		receivedInfo.setPrefHeight(350);
		myPaneRight.add(receivedInfo,0,1);	
		myPaneRight.add(retrieveButton,2,1);
				
		VBox myBox = new VBox();
		myBox.getChildren().addAll(myPaneLeft);
		HBox hmyBox = new HBox();
		hmyBox.getChildren().addAll(myBox,myPaneRight);
		getChildren().addAll(hmyBox);
		
		//connecting to the braodcast class using a helper method
		connectButton.setOnAction(e->{
			
			try {
				clientConnect();
				
				Thread thisThread = new Thread(myClient);
				thisThread.start();
				
			} catch (IOException e1) {
			
				e1.printStackTrace();
				System.out.println("Client 2 could not connect");
			}
		});
		
		//placing an order 
		orderButton.setOnAction(e->{
			
			try {
				sendInformation();
			} catch (Exception e1) {
			
				e1.printStackTrace();
				System.out.println("Could not place order");
			}
		});
		
		//initiating the listening port for the client 
		retrieveButton.setOnAction(e->{		
			
			String mystring = myClient.getMsgString();
						
			//theBlockChain.addBlock(buyerBlock);			
			receivedInfo.clear();
			receivedInfo.appendText("Latest information " + mystring);
			
		});
		
	}
	
	public void clientConnect () throws IOException
	{
		String string = "CONNECT2";
		clientSocket2 = new DatagramSocket(clientport2);	
		inet = InetAddress.getLocalHost();				
		buff = string.getBytes();
		thePacket = new DatagramPacket(buff, buff.length,inet,serverport);		
		clientSocket2.send(thePacket);
		
		byte [] thebyte = new byte [1024];
		thePacket = new DatagramPacket(thebyte,thebyte.length);
		clientSocket2.receive(thePacket);
		String response = new String(thePacket.getData(),0,thePacket.getLength());
		receivedInfo.appendText(response);		
		

	}	
	

	//helper method to make a transaction 
	public void sendInformation () throws Exception
	{	
		
		String name = nameField.getText();											
		String surname = surnameField.getText();
		String email = emailField.getText();
		String streetaddress = streeTextField.getText();			
		String country = countryField.getText();
		String item = itemField.getText();	
		
		String thename = myClient.getMsgString();
		String thesurname = myClient.getMsgString();
		String theemail = myClient.getMsgString();
		String thestreet = myClient.getMsgString();
		String thecountry =myClient.getMsgString();
		String theitem = myClient.getMsgString();
		
		List <String> genesisList = new ArrayList<>();
		
		genesisList.add(thename);
		genesisList.add(thesurname);
		genesisList.add(theemail);
		genesisList.add(thestreet);
		genesisList.add(thecountry);
		genesisList.add(theitem);
		
		myList.add(name);
		myList.add(surname);
		myList.add(email);
		myList.add(streetaddress);		
		myList.add(country);
		myList.add(item);	   
		    
		
		Transaction genesis = new Transaction(genesisList);
		
			       
	    Block buyerBlock1 = new Block(new java.util.Date(), Arrays.asList(genesis));
	    
			//adding blocks to the bloackchain
	        theBlockChain.addBlock(buyerBlock1);
	        theBlockChain.isBlockValid();
	        
	        System.out.println("This is the previousHash: " + buyerBlock1.getPreviousHash() + "\n" + "This is the current Hash: " + buyerBlock1.calculateHash());
	        
	        theBlockChain.addBlock(buyerBlock);
	        theBlockChain.isBlockValid();
				
	        sendMessage(transaction.getTrans().toString(), serverport);					
			
			System.out.println("This is the previousHash: " + buyerBlock.getPreviousHash() + "\n" + "This is the current Hash: " + buyerBlock.calculateHash());
		
			theBlockChain.isBlockValid();	
	}
	

	public void sendMessage(String msg,int port) throws IOException
	{
		inet = InetAddress.getLocalHost();				
		buff = msg.getBytes();
		thePacket = new DatagramPacket(buff, buff.length,inet,port);		
		clientSocket2.send(thePacket);
	}	 
		

}
