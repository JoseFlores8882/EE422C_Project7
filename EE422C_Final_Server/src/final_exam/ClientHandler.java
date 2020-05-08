package final_exam;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
	private Socket s;
	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	
	public ClientHandler(Socket sock, ObjectInputStream in, ObjectOutputStream out)
	{
		this.s = sock;
		this.clientInput = in;
		this.clientOutput = out;
	}
	
	@Override
	public void run()
	{
		
	}
}
