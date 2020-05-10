package final_exam;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.control.*;


public class AuctionScreenController implements Initializable{
	private AuctionItem item;
	@FXML private Button backButton;
	@FXML private Button bidButton;
	@FXML private Button nextButton;
	@FXML private Button prevButton;
	@FXML private TextField inputBid;
	@FXML private Label currentBid;
	@FXML private Label buyPrice;
	@FXML private Label bidError;
	@FXML private Label itemTitle;
	@FXML private Label timeLeft;

	
	public void initItem(AuctionItem displayItem) {
		this.item = displayItem;
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
