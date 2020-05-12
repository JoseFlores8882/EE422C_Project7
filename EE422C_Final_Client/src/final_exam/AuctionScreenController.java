package final_exam;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class AuctionScreenController implements Initializable{
	private String userId;
	private AuctionItem item;
	@FXML private Button backButton;
	@FXML private Button quitButton;
	@FXML private Button bidButton;
	@FXML private Button nextButton;
	@FXML private Button prevButton;
	@FXML private Button buyButton;
	@FXML private Button refreshButton;
	@FXML private Button descriptionButton;
	@FXML private Button historyButton;
	@FXML private TextField inputBid;
	@FXML private Label currentBid;
	@FXML private Label buyPrice;
	@FXML private Label bidError;
	@FXML private Label itemTitle;
	@FXML private Label timeLeft;
	@FXML private Label description;
	@FXML private Label highBidder;
	@FXML private Label auctioner;
	@FXML private ImageView picture;

	
	public void initItem(AuctionItem displayItem, String id) {
		this.userId = id;
		this.item = displayItem;
		this.currentBid.setText("$" + String.format("%.2f",item.getCurrentPrice()));
		if(buyPrice!=null) {
			this.buyPrice.setText("$" + String.format("%.2f",item.getBuyPrice()));
		}
		else {
			this.buyButton.setText("");
			buyButton.setDisable(true);
		}
		this.timeLeft.setText(this.item.updateTimeLeft());
		this.itemTitle.setText(this.item.getName());
		this.description.setText(this.item.getDescription());
		if(this.item.getItemPicture()!=null) {
			this.picture.setImage(this.item.getItemPicture());
		}
		highBidder.setText(this.item.getPurchaseId());
		auctioner.setText(this.item.getUserId());
		this.inputBid.setText("0.00");
		if(this.item.isExpired() || this.userId.contentEquals(this.item.getUserId())) {
			bidButton.setDisable(true);
			buyButton.setDisable(true);
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void bidButtonPushed(ActionEvent action) {
		try {
			double bid = Double.parseDouble(inputBid.getText());
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);
			double newbid = Double.parseDouble(df.format(bid));
			if(newbid!=bid) {
				bidError.setText("Warning: bid rounded to" + newbid +".");
			}
			if(newbid <= (item.getCurrentPrice() + .99)) {
				bidError.setText("Error: please bid at least $1 higher than current.");
				return;
			}
			if(newbid >= item.getBuyPrice()) {
				bidError.setText("Error: bid higher than Buy It Now! price.");
				return;
			}
			Message request = new Message("New bid");
			request.setNewBid(newbid);
			request.setItemName(item.getName());
			request.setBidderId(userId);
			Client.toServer.writeObject(request);
			Message returnMessage = (Message) Client.fromServer.readObject();
			if(returnMessage.getReturnMessage().contentEquals("success")){
				bidError.setText("Successfully bid!");
				return;
			}
			else {
				bidError.setText("Error: please try again.");
			}
		} catch (NumberFormatException e) {
			bidError.setText("Error: please input a $ amount.");
			return;
		} catch (IllegalFormatException e) {
			bidError.setText("Error: please input a $ amount.");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void backButtonPushed(ActionEvent action) throws IOException {
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
	
	public void buyButtonPushed(ActionEvent action) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("BuyPage.fxml"));
		Parent mainPageParent = loader.load();
        
		//pass the Id to details scene
        BuyPageController controller = loader.getController(); 
        controller.initItem(this.userId, this.item);
        
        //set next scene
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
		Message request = new Message("update auction disp");
		request.setItemName(this.item.getName());
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		AuctionItem item = returnMessage.getAuctionItem();
		if(item.isExpired())
		{
			this.bidButton.setDisable(true);
			this.buyButton.setDisable(true);
		}
		else {
			this.timeLeft.setText(item.getTimeLeft());
			this.currentBid.setText("$" + String.format("%.2f",item.getCurrentPrice()));
			this.highBidder.setText(item.getPurchaseId());
		}
	}
	
	public void historyButtonPushed(ActionEvent action) throws IOException, ClassNotFoundException {
		Message request = new Message("history");
		request.setItemName(this.item.getName());
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		ArrayList<String> history = returnMessage.getAuctionItem().getBidHistory();
		String msg = "History:\n";
		for(int i=0;i < history.size();i++) {
			msg += (history.get(i) + "\n");
		}
		description.setText(msg);
	}
	
	public void descriptionButtonPushed(ActionEvent action) throws ClassNotFoundException, IOException {
		Message request = new Message("description");
		request.setItemName(this.item.getName());
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		description.setText(returnMessage.getAuctionItem().getDescription());
	}
}
