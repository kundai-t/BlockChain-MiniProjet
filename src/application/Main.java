package application;

import broadcasting.Broadcast;
import gui.BuyerUDP;
import gui.SellerUDP;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**@author KR Tambo
 * @version miniproject
 * 201707367  
 * */

public class Main extends Application {
	
	//instantiating my client GUIs 
	BuyerUDP myBuyerUDP = new BuyerUDP();
	SellerUDP mySellerUDP = new SellerUDP();
	Scene myScene1,myScene2;	
	
	private Label scene1label,scene2label;
	private Button SwitchScene1;
	private Button SwitchScene2;
	Broadcast broadcast;
	
	
	public static void main(String[] args) {
		launch(args);		

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		broadcast = new Broadcast();
		Thread myThread = new Thread(broadcast);
		myThread.start();	
		
		
		//creating scene one									
		SwitchScene1 = new Button("Open Buyer UI");
		SwitchScene1.setTextFill(Color.GREEN);
		SwitchScene2 = new Button("Open Seller UI");
		SwitchScene2.setTextFill(Color.DARKOLIVEGREEN);
		scene1label = new Label("Buyer Panel");
		scene2label = new Label("Seller Panel");
				
		SwitchScene2.setOnAction(e->
		{
			primaryStage.setScene(myScene2);
		});
		
		//adding all scenes onto the application 
		VBox buyerBox = new VBox();
		buyerBox.setStyle("-fx-background-color: BLANCHEDALMOND");
		myBuyerUDP.setStyle("-fx-background-color: BLANCHEDALMOND");
		buyerBox.getChildren().addAll(scene1label,myBuyerUDP,SwitchScene2);	
		myScene1 = new Scene(buyerBox,800,600);		
		
		
		//creating scene two with different UI
		SwitchScene1.setOnAction(e->
		{
			primaryStage.setScene(myScene1);
		});
		
		VBox sellerBox = new VBox();
		sellerBox.setStyle("-fx-background-color: AQUAMARINE");
		mySellerUDP.setStyle("-fx-background-color: AQUAMARINE");
		sellerBox.getChildren().addAll(scene2label,mySellerUDP,SwitchScene1);
		
		myScene2 = new Scene(sellerBox,800,600);		
		
		primaryStage.setScene(myScene1);
		primaryStage.show();		
		
		
	}	
	
	
	
}
