package final_exam;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.Stage;

public class BuyPageController implements Initializable  {
	private String userId;
	private AuctionItem item;
	
	@FXML private Button yesButton;
	@FXML private Button noButton;
	@FXML private Button quitButton;
	@FXML private Button backButton;
	@FXML private Label itemName;
	@FXML private Label errorOut;
	@FXML private ImageView picture;
	
	public void initItem(String username, AuctionItem buyItem) {
		this.userId = username;
		this.item = buyItem;
		this.itemName.setText(buyItem.getName());
		this.errorOut.setText("");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void quitButtonPushed(ActionEvent action) throws IOException {
		Message request = new Message("quit");
		Client.toServer.writeObject(request);
		Client.toServer.close();
		Client.fromServer.close();
		System.exit(1);
	}
	
	public void yesButtonPushed(ActionEvent action) throws IOException, ClassNotFoundException {
		Message request = new Message("buy now");
		request.setBidderId(this.userId);
		request.setItemName(this.item.getName());
		Client.toServer.writeObject(request);
		Message received = (Message) Client.fromServer.readObject();
		if(received.getReturnMessage().contentEquals("failure")) {
			errorOut.setText("Error: Item time has expired or has been purchased.");
			return;
		}
		else {
			errorOut.setText("Success! Item purchased.");
			return;
		}
	}
	
	public void noButtonPushed(ActionEvent action) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("gBayMainPage.fxml"));
		Parent mainPageParent = loader.load();
        
		//pass the Id to details scene
        gBayMainPageController controller = loader.getController(); 
        controller.initItem(this.userId);
        
        //set next scene
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage)((Node)action.getSource()).getScene().getWindow(); 	//get stage info
        window.setScene(mainPageScene);
        window.show();
	}

}
