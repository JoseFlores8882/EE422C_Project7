package final_exam;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	public static ArrayList<AuctionItem> globalList = new ArrayList<AuctionItem>();
	public static HashMap<String,String> userMap = new HashMap<String, String>();
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(100);
		if(args.length < 4) {
	        System.out.println("Error: no specified initial list of auction items/prices, usernames/passwords");
	        System.exit(1);
	    }
	    Scanner items = new Scanner(new FileInputStream(args[0]));
	    Scanner prices = new Scanner(new FileInputStream(args[1]));
	    Scanner usernames = new Scanner(new FileInputStream(args[2]));
	    Scanner passwords = new Scanner(new FileInputStream(args[3]));
	    initialize(items,prices,usernames,passwords);
		while(true) 
		{
			Socket newClient = null;
			try 
			{
				newClient = ss.accept();
				ObjectOutputStream out = new ObjectOutputStream(newClient.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(newClient.getInputStream());
				Thread t = new ClientHandler(newClient,in,out);
				t.start();
			} 
			catch(Exception e) 
			{
				newClient.close();
				e.printStackTrace();
			}
		}
	}
	
	private static void initialize(Scanner items, Scanner prices, Scanner usernames, Scanner passwords) {
		//reads input lists of auction items/prices and initializes the array 
		//TODO fix this to read from one file
		while(items.hasNext() && prices.hasNext())
		{
			AuctionItem newItem = new AuctionItem(items.nextLine(),prices.nextDouble());
			globalList.add(newItem);
		}
		//TODO should really make this more secure lmao
		while(usernames.hasNext() && passwords.hasNext())
		{
			userMap.put(usernames.next(), passwords.next());
		}
	}
}
