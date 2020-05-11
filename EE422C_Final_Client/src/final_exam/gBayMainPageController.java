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
	
	public void quitButtonPushed(ActionEvent action) {
		System.exit(1);
	}
}
