package final_exam;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	public static ArrayList<AuctionItem> globalList = new ArrayList<AuctionItem>();
	public static HashMap<String,String> userMap = new HashMap<String, String>();
	public static HashMap<String,ArrayList<AuctionItem>> userAuctions = new HashMap<String,ArrayList<AuctionItem>>();
	public static HashMap<String,ArrayList<AuctionItem>> userBids = new HashMap<String, ArrayList<AuctionItem>>();
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
			if(item.getName().contentEquals(itemName) && newBid > item.getCurrentPrice() && !item.isExpired() && !item.getUserId().contentEquals(bidderId)) {	//if item is not expired, and new bid is greater set new price
				item.setCurrentPrice(newBid);															//set new bid
				item.setPurchaseId(bidderId);
				ArrayList<AuctionItem> userBid = userBids.get(bidderId);
				if(userBid==null) {
					userBid = new ArrayList<AuctionItem>();
				}
				userBid.add(item);
				userBids.put(bidderId,userBid);
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
			ArrayList<AuctionItem> newUserAuctions = new ArrayList<AuctionItem>();
			ArrayList<AuctionItem> newUserBids = new ArrayList<AuctionItem>();
			userAuctions.put(username, newUserAuctions);
			userBids.put(username,newUserBids);
			return true;
		}
	}
	
	public static synchronized boolean buyNow(String itemName, String bidderId) {
		for(int i=0;i < globalList.size();i++) {
			AuctionItem item = new AuctionItem("temp", 0, 0, "temp");
			item = globalList.get(i);
			if(item.getName().contentEquals(itemName) && !item.isExpired()) {	//if item is not expired
				item.setExpired(true);											//set as expired and write purchaser
				item.setPurchaseId(bidderId);
				item.setSecondsLeft(0);
				item.updateTimeLeft();
				return true;
			}
		}
		return false;
	}
}


