package final_exam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.IllegalFormatException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MakeItemGUIController implements Initializable {

	@FXML private Button makeButton;
	@FXML private Button backButton;
	@FXML private Button quitButton;
	@FXML private Label errorOut;
	@FXML private Button chooseImage;
	@FXML private ImageView picture;
	@FXML private TextField itemName;
	@FXML private TextField description;
	@FXML private TextField startBid;
	@FXML private TextField startBuy;
	@FXML private TextField days;
	@FXML private CheckBox buy;
	
	private Image pic;
	private String userId;
	private FileChooser fileChooser;
	private File filePath;
	
	public void init(String userId) {
		this.userId = userId;
		this.errorOut.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void chooseImageButtonPushed(ActionEvent action) {
        Stage window = (Stage)((Node)action.getSource()).getScene().getWindow(); 	//get stage info
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(window);
        
        try {
        	BufferedImage bufferedImage = ImageIO.read(filePath);
        	pic = SwingFXUtils.toFXImage(bufferedImage,null);
        	picture.setImage(pic);
        } catch(IOException e) {
        	
        }
	}
	
	public void makeButtonPushed(ActionEvent action) {
		try {
			AuctionItem newItem = null;
			String name = itemName.getText();
			String descr = description.getText();
			if(name.contentEquals("")) {
				errorOut.setText("Error: Please input name for item.");
				return;
			}
			if(descr.contentEquals("")) {
				errorOut.setText("Error: Please input description for item.");
				return;
			}
			if(pic==null) {
				errorOut.setText("Error: Please select an image.");
				return;
			}
			
			int days = Integer.parseInt(this.days.getText());
			double bid = Double.parseDouble(startBid.getText());
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);
			double startbid = Double.parseDouble(df.format(bid));
			double buybid,startbuy;
			if(buy.isSelected()) {
				buybid = Double.parseDouble(startBid.getText());
				DecimalFormat buydf = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);
				startbuy = Double.parseDouble(df.format(bid));
				newItem = new AuctionItem(name,this.userId,startbid,startbuy,descr,days);
			}
			else {
				newItem = new AuctionItem(name,this.userId,startbid,descr,days);
			}
			Message request = new Message("new item");
			request.setAuctionItem(newItem);
			Client.toServer.writeObject(request);
			Message returnMessage = (Message) Client.fromServer.readObject();
			if(returnMessage.getReturnMessage().contentEquals("failure")) {
				errorOut.setText("Error: Please try again.");
				return;
			}
			if(returnMessage.getReturnMessage().contentEquals("success")){
				errorOut.setText("Successfully made item!");
				return;
			}
		
		} catch (NumberFormatException e) {
			errorOut.setText("Error: Please input dollar amounts for bids, integers for days.");
			return;
		} catch (IllegalFormatException e) {
			errorOut.setText("Error: Please input dollar amounts for bids, integers for days.");
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void quitButtonPushed(ActionEvent action) throws IOException {
		Message request = new Message("quit");
		Client.toServer.writeObject(request);
		Client.toServer.close();
		Client.fromServer.close();
		System.exit(1);
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
}
