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
import javafx.stage.Stage;

public class NewUserGUIController implements Initializable {

	@FXML private TextField username;
	@FXML private PasswordField pass1;
	@FXML private PasswordField pass2;
	@FXML private Label textOut;
	@FXML private Button createButton;
	@FXML private Button quitButton;
	@FXML private Button backButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textOut.setText("");
	}

	public void createButtonPushed(ActionEvent action) throws IOException, ClassNotFoundException {
		String user = username.getText();
		String password1 = pass1.getText();
		String password2 = pass2.getText();
		//username at least 6 chars
		if(user.length() < 6) {
			textOut.setText("Error: username must be at least 6 characters.");
			return;
		}
		
		//password fields must match
		if(!password1.contentEquals(password2)) {
			textOut.setText("Error: password fields do not match.");
			return;
		}
		
		//password at least 10 chars
		if(password1.length() < 10) {
			textOut.setText("Error: password must be at least 10 characters.");
			return;
		}
		
		Message request = new Message("New user",user,password1);
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		if(returnMessage.getReturnMessage().contentEquals("failure")) {
			textOut.setText("Error: username already exists, please try again.");
			return;
		}
		textOut.setText("Account successfully created!");
	}
	
	public void backButtonPushed(ActionEvent action) throws IOException {
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
		//TODO: close client observer thread
		Client.toServer.close();
		Client.fromServer.close();
		System.exit(1);
	}
}
