package final_exam;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class gBayMainPageController implements Initializable {

	private String userId;
	@FXML private TableView<AuctionItem> itemDisplay;
	@FXML private TableColumn<AuctionItem,String> itemName;
	@FXML private TableColumn<AuctionItem,String> itemTimeRemaining;
	@FXML private TableColumn<AuctionItem,Double> itemCurrentBid;
	@FXML private Button viewItem;
	@FXML private Button viewBids;
	@FXML private Button auctionItem;
	@FXML private Button viewMyAuctions;
	@FXML private Button quitButton;
	@FXML private Button logoutButton;
	@FXML private Button refreshButton;
	@FXML private Button makeItem;
	
	public void initItem(String id) {
		this.userId = id;
		viewItem.setDisable(true);
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		itemName.setCellValueFactory(new PropertyValueFactory<AuctionItem,String>("name"));
		itemCurrentBid.setCellValueFactory(new PropertyValueFactory<AuctionItem,Double>("currentPrice"));
		//itemBuyPrice.setCellValueFactory(new PropertyValueFactory<AuctionItem,Double>("buyPrice"));
		itemTimeRemaining.setCellValueFactory(new PropertyValueFactory<AuctionItem,String>("timeLeft"));
		Message request = new Message("initialize List");
		try {
			Client.toServer.writeObject(request);
			Message returnMessage = (Message) Client.fromServer.readObject();
			itemDisplay.setItems(FXCollections.observableArrayList(returnMessage.getItemList()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		viewItem.setDisable(true);
		itemDisplay.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	public void itemClicked() {
		viewItem.setDisable(false);
	}
	
	public void viewItemDetails(ActionEvent action) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AuctionScreen.fxml"));
		Parent mainPageParent = loader.load();
        
		//pass the selected auction item to details scene
        AuctionScreenController controller = loader.getController(); 
        AuctionItem XD = itemDisplay.getSelectionModel().getSelectedItem();
        controller.initItem(XD, userId);
        
        //set next scene
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage)((Node)action.getSource()).getScene().getWindow(); 	//get stage info
        window.setScene(mainPageScene);
        window.show();
		
	}
	
	public void logoutButtonPushed(ActionEvent action) throws IOException {
		//load fxml file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("LoginGUI.fxml"));
		Parent mainPageParent = loader.load();
			
		Scene mainPageScene = new Scene(mainPageParent);
		Stage window = (Stage)((Node)action.getSource()).getScene().getWindow(); 	//get stage info
		window.setScene(mainPageScene);
		window.show();
	}
	
	public void quitButtonPushed(ActionEvent action) throws IOException {
		Message request = new Message("quit");
		Client.toServer.writeObject(request);
		Client.toServer.close();
		Client.fromServer.close();
		System.exit(1);
	}
	
	public void refreshButtonPushed(ActionEvent action) throws IOException, ClassNotFoundException {
		Message request = new Message("initialize List");
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		itemDisplay.setItems(FXCollections.observableArrayList(returnMessage.getItemList()));
	}
	
	public void makeButtonPushed(ActionEvent action) throws IOException {
		//load fxml file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MakeAuctionGUI.fxml"));
		Parent mainPageParent = loader.load();
		
		MakeItemGUIController controller = loader.getController(); 
        controller.init(this.userId);
			
		Scene mainPageScene = new Scene(mainPageParent);
		Stage window = (Stage)((Node)action.getSource()).getScene().getWindow(); 	//get stage info
		window.setScene(mainPageScene);
		window.show();
	}
	
	public void viewBidsButtonPushed(ActionEvent action) throws IOException, ClassNotFoundException {
		Message request = new Message("my bids");
		request.setUsername(userId);
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		itemDisplay.setItems(FXCollections.observableArrayList(returnMessage.getItemList()));
	}
	
	public void viewMyItemsButtonPushed(ActionEvent action) throws IOException, ClassNotFoundException {
		Message request = new Message("my items");
		request.setUsername(userId);
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		itemDisplay.setItems(FXCollections.observableArrayList(returnMessage.getItemList()));
	}

}
