package final_exam;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler extends Thread {
	private Socket s;
	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	
	public ClientHandler(Socket socket, ObjectInputStream in, ObjectOutputStream out)
	{
		this.s = socket;
		this.clientInput = in;
		this.clientOutput = out;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try {
				Message clientMessage = (Message) clientInput.readObject();
				String request = clientMessage.getRequest();
				if(request.contentEquals("login"))
				{
					String username = clientMessage.getUsername();
					String password = clientMessage.getPassword();
					if(Server.userMap.containsKey(username) && Server.userMap.get(username).contentEquals(password)) {
						clientMessage.setReturnMessage("success");
					}
					else {
						clientMessage.setReturnMessage("failure");
					}
					clientOutput.writeObject(clientMessage);
				}
				//TODO:else if...
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
