package final_exam;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(100);
		if(args.length < 1) {
	        System.out.println("Error: no specified initial list of auction items");
	        System.exit(1);
	    }
	    Scanner inFile = new Scanner(new FileInputStream(args[0]));
	    initialize(inFile);
		while(true) 
		{
			Socket newClient = null;
			try 
			{
				newClient = ss.accept();
				DataInputStream in = new DataInputStream(newClient.getInputStream());
				DataOutputStream out = new DataOutputStream(newClient.getOutputStream());
			} 
			catch(Exception e) 
			{
				newClient.close();
				e.printStackTrace();
			}
			
			
			
			
			
			
			
		}
	}
	
	private static void initialize(Scanner inFile) {
		//reads input list of auction items and initializes the array 
		
	}
}
