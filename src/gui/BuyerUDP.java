package gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import application.Transaction;
import application.TransactionData;
import application.TransactionInfo;
import blockchain.Block;
import blockchain.BlockChain;
import broadcasting.Client1Connect;
import graph.Graph;
import graph.Graph.Vertex;
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
 * */

public class BuyerUDP extends GridPane {	
	
	//creating the sockets for the client as the buyer.
	public static DatagramSocket clientSocket1, clientSocket2;
	public static DatagramPacket thePacket;
	public static BufferedReader buffRead;
	public static InetAddress inet;
	public int serverport = 7020, clientport1 = 9877, clientport2 = 8867, client1listening = 4444;
	public static byte buff [] = new byte [1024];
	BlockChain theBlockChain = new BlockChain();
	List <String> myList = new ArrayList<String>();
	
	public MulticastSocket castsocket;
	public static byte [] mybye = new byte[1024];
	public InetAddress address;	
	public int listeningport = 4444;
	public boolean listening = true;
	public String ipString = "230.0.0.0";
	
	private TextField nameField;
	private TextField surnameField;
	private TextField streeTextField;
	private TextField  cityField;
	private TextField  phoneField;
	private TextField countryField;
	private TextField itemField;
	private TextField statusConnection;
	
	private TextArea receiveInfoArea;
	
	private Button connectButton;
	private Button orderButton;
	private Button retrieveButton;
	
	private Label buyername;
	private Label buyerSurname;
	private Label buyerStreetAddress;
	private Label buyerCity;
	private Label buyerPhone;
	private Label buyerCountry;
	private Label buyerItem;
	private Label sellerInfoLabel;	
	
	
	public BuyerUDP () 
	{
		
		//creating an instance of the client listening classes 
		Client1Connect myClient = new Client1Connect();
		
		//adding the various components of the UI 
		buyername = new Label("Name");
		buyerSurname = new Label("Surname");		
		buyerStreetAddress = new Label("Street Address");
		buyerCity = new Label("City");		
		buyerPhone = new Label("Phone number");
		buyerCountry = new Label("Country");
		buyerItem = new Label("Item bought");		
		orderButton = new Button("Order Now");
		connectButton = new Button("Connect");
		retrieveButton = new Button("Retrieve");
		
		//adding tetxtfield to the Gridpane
		nameField = new TextField();		
		surnameField = new TextField();		
		streeTextField = new TextField();
		cityField = new TextField();		
		phoneField = new TextField();
		countryField = new TextField();
		itemField = new TextField();
		statusConnection = new TextField();
		statusConnection.setEditable(false);		
		
		GridPane myPaneBuy = new GridPane();
		myPaneBuy.setPadding(new Insets(10,11,12,13));
		myPaneBuy.setVgap(5);
		myPaneBuy.setHgap(5);
		myPaneBuy.setStyle("-fx-background-color: BLANCHEDALMOND");
		
		//adding all components to the pane
		myPaneBuy.add(buyername,0,0);
		myPaneBuy.add(nameField,1,0);
		myPaneBuy.add(buyerSurname,0,1);
		myPaneBuy.add(surnameField,1,1);		
		myPaneBuy.add(buyerStreetAddress,0,2);
		myPaneBuy.add(streeTextField,1,2);
		myPaneBuy.add(buyerCity,0,3);
		myPaneBuy.add(cityField,1,3);		
		myPaneBuy.add(buyerCountry,0,4);
		myPaneBuy.add(countryField,1,4);
		myPaneBuy.add(buyerItem,0,5);
		myPaneBuy.add(itemField,1,5);
		myPaneBuy.add(buyerPhone,0,6);
		myPaneBuy.add(phoneField,1,6);	
		myPaneBuy.add(connectButton,0,7);
		myPaneBuy.add(statusConnection,1,7);
		myPaneBuy.add(orderButton,1,8);	
		
		sellerInfoLabel = new Label("Seller Information");
		receiveInfoArea = new TextArea();		
		
		GridPane myPaneRight = new GridPane();
		myPaneRight.setPadding(new Insets(10,11,12,13));
		myPaneRight.setVgap(10);
		myPaneRight.setHgap(10);
		myPaneRight.setStyle("-fx-background-color: BLANCHEDALMOND");
		myPaneRight.add(sellerInfoLabel,0,0);
		receiveInfoArea.setWrapText(true);
		receiveInfoArea.setEditable(false);
		receiveInfoArea.setMaxWidth(400);
		receiveInfoArea.setPrefHeight(350);
		myPaneRight.add(receiveInfoArea,0,1);	
		myPaneRight.add(retrieveButton,2,1);
				
		VBox myBox = new VBox();
		myBox.getChildren().addAll(myPaneBuy);
		HBox hmyBox = new HBox();
		hmyBox.getChildren().addAll(myBox,myPaneRight);
		getChildren().addAll(hmyBox);		
		
		//button to connect to the broadcast class of the clients
		connectButton.setOnAction(e->
		{
			clientConnect();
		});		
		
		//placing an order 
		orderButton.setOnAction(e->
		{
			
			try {
				
				sendInformation();
			} catch (Exception e1) {
				
			
				e1.printStackTrace();
				System.out.println("Client error connection");
			}
		});	
		
		//button to retrieve all information 
		retrieveButton.setOnAction(e->{
						
				String mystring = myClient.getMsgString();
				receiveInfoArea.appendText(mystring);
				
			
		});
	}
	
	//connect helper method 
	public void clientConnect () 
	{
		String info = "CONNECT";
		
		try {

			
			clientSocket1 = new DatagramSocket(clientport1);
			sendMessage(info,serverport);
			receiveMessage();
			
			
		} catch (SocketException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		
	}
	
	//send information helper method
	public void sendInformation () throws Exception
	{	
		
		String name = nameField.getText();
		String surname = surnameField.getText();		
		String streetaddress = streeTextField.getText();
		String city = cityField.getText();		
		String country = countryField.getText();
		String item = itemField.getText();
		String phonenumber = phoneField.getText();	
		
		
		myList.add(name);
		myList.add(surname);		
		myList.add(streetaddress);
		myList.add(city);		
		myList.add(country);
		myList.add(item);
		myList.add(phonenumber);	
		
		//creating an instance of a graph 
		Graph<TransactionData> myGraph = new Graph<TransactionData>();
		
		TransactionInfo info = new TransactionInfo(new java.util.Date(),myList);
		Graph.Vertex<TransactionData> myData = new Graph.Vertex<TransactionData>(info);
		myGraph.getVertices().add(myData);
		
		Vertex<TransactionData> from = null;
		Vertex<TransactionData> to = null;
		
		for (Graph.Vertex<TransactionData> data : myGraph.getVertices()) 
		{
			int a = 0;
			myGraph.getVertices().listIterator();
			from = myGraph.getVertices().get(a);
			
			if (myGraph.getVertices().size() + a > 1) {
				
				to = myGraph.getVertices().get(a + 1);
				Graph.Edge<TransactionData> theEdge = new Graph.Edge<TransactionData>(a + 1,from,to);
				myGraph.getEdges().add(theEdge);
				from.addEdge(theEdge);
				
				Graph.Edge<TransactionData> theEdge2 = new Graph.Edge<TransactionData>(theEdge.getCost(),theEdge.getFromVertex(),theEdge.getToVertex());
				myGraph.getEdges().add(theEdge2);
				to.addEdge(theEdge2);
				System.out.println(data);
			}
		}
		
		 //Creating a transaction 
			Transaction transaction = new Transaction(myList);		       
	        //creating a block
	        Block buyerBlock = new Block(new java.util.Date(), Arrays.asList(transaction));       
	        //adding to the blockchain
	        theBlockChain.addBlock(buyerBlock);
	        theBlockChain.isBlockValid();		
				
				inet = InetAddress.getLocalHost();								
				sendMessage(transaction.getTrans().toString(), serverport);					
				 
	        receiveInfoArea.clear();
			receiveInfoArea.appendText("This is the information you have sent to the seller \n" + transaction.getTrans().toString());
			System.out.println("This is the previousHash: " + buyerBlock.getPreviousHash() + "\n" + "This is the current Hash: " + buyerBlock.calculateHash() + "\n" + 
			"Block was created on " + buyerBlock.getDatetime() + "\n" + "This is the sent transaction " + transaction.getTrans().toString() + " Number of blocks in the Chain "+ theBlockChain.getChain().size() + "\n");					
		
		
		
		
	}
	
	//receive information helper method
	public void receiveMessage () throws IOException
	{	
		byte [] recBuff = new byte [1024];
		DatagramPacket pack = new DatagramPacket(recBuff,recBuff.length);
		clientSocket1.receive(pack);
		String response = new String(pack.getData(),0,pack.getLength());
		//receiveMessage(thePacket);	
		receiveInfoArea.clear();
		receiveInfoArea.appendText(response);
		
	}
	
	//send message helper method 
	public void sendMessage(String msg,int port) throws IOException
	{
		inet = InetAddress.getLocalHost();				
		buff = msg.getBytes();
		//thePacket = new DatagramPacket(buff, buff.length,inet,port);		
		clientSocket1.send(new DatagramPacket(buff,msg.length(),inet,port));
	}	 
	 
	 	
}
