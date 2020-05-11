package final_exam;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.*;

public class LoginGUIController implements Initializable {

	//Login screen fields
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private Label loginOut;
	@FXML private Button newUserButton;
	@FXML private Button quitButton;
	//main page fields
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginOut.setText("");
	}
	
	public void loginButtonPushed(ActionEvent action) throws IOException, ClassNotFoundException
	{
		String usr = username.getText();
		String pass = password.getText();
		Message request = new Message("login",usr,pass);
		Client.toServer.writeObject(request);
		Message returnMessage = (Message) Client.fromServer.readObject();
		if(returnMessage.getReturnMessage().contentEquals("failure")) {
			loginOut.setText("Username/Password not found.");
			return;
		}
		else {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("gBayMainPage.fxml"));
			Parent mainPageParent = loader.load();
	        
			//pass the Id to details scene
	        gBayMainPageController controller = loader.getController(); 
	        controller.initItem(usr);
	        
	        //set next scene
	        Scene mainPageScene = new Scene(mainPageParent);
	        Stage window = (Stage)((Node)action.getSource()).getScene().getWindow(); 	//get stage info
	        window.setScene(mainPageScene);
	        window.show();
		}
	}
	
	public void newUserButtonPushed(ActionEvent action) throws IOException {
		//load fxml file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("newUserGUI.fxml"));
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
