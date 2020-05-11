package final_exam;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	public static ArrayList<AuctionItem> globalList = new ArrayList<AuctionItem>();
	public static HashMap<String,String> userMap = new HashMap<String, String>();
	
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(100);
		if(args.length < 6) {
	        System.out.println("Error: no specified initial list of auction items/prices, usernames/passwords");
	        System.exit(1);
	    }
	    Scanner items = new Scanner(new FileInputStream(args[0]));
	    Scanner prices = new Scanner(new FileInputStream(args[1]));
	    Scanner usernames = new Scanner(new FileInputStream(args[2]));
	    Scanner passwords = new Scanner(new FileInputStream(args[3]));
	    Scanner buyPrices = new Scanner(new FileInputStream(args[4]));
	    Scanner descriptions = new Scanner(new FileInputStream(args[5]));
	    initialize(items,prices,usernames,passwords,buyPrices,descriptions);
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
	
	private static void initialize(Scanner items, Scanner prices, Scanner usernames, Scanner passwords, Scanner buyPrices, Scanner descriptions) {
		//reads input lists of auction items/prices and initializes the array 
		//TODO fix this to read from one file
		while(items.hasNext() && prices.hasNext() && buyPrices.hasNext() && descriptions.hasNext())
		{
			AuctionItem newItem = new AuctionItem(items.nextLine(),prices.nextDouble(), buyPrices.nextDouble(), descriptions.nextLine());
			globalList.add(newItem);
		}
		//TODO should really make this more secure lmao
		while(usernames.hasNext() && passwords.hasNext())
		{
			userMap.put(usernames.next(), passwords.next());
		}
		Thread updater = new ServerUpdater();
		updater.start();
	}
	
	public static synchronized boolean makeBid(String itemName, double newBid, String bidderId) {
		for(int i=0;i < globalList.size();i++) {
			AuctionItem item = new AuctionItem("temp", 0, 0, "temp");
			item = globalList.get(i);
			if(item.getName().contentEquals(itemName) && newBid > item.getCurrentPrice() && !item.isExpired()) {	//if item is not expired, and new bid is greater set new price
				item.setCurrentPrice(newBid);															//set new bid
				ArrayList<String> history = item.getBidHistory();										//add to history
				history.add(bidderId+" bid $" + String.format("%.2f",item.getCurrentPrice()));
				return true;
			}
		}
		return false;
	}
	
	public static synchronized boolean makeUser(String username, String password) {
		if(userMap.containsKey(username)) {
			//username already exists
			return false;
		}
		else {
			userMap.put(username, password);
			return true;
		}
	}
}


