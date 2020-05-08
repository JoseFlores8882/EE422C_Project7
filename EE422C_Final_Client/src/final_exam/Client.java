package final_exam;
	
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Client extends Application {
	public static Socket s;
	public static ObjectInputStream fromServer;
	public static ObjectOutputStream toServer;
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		try {
			s = new Socket("127.0.0.1",100);
			fromServer = new ObjectInputStream(s.getInputStream());
			toServer = new ObjectOutputStream(s.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("Connection failed, terminating");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Connection failed, terminating");
			e.printStackTrace();
		}
		launch(args);
	}
}
