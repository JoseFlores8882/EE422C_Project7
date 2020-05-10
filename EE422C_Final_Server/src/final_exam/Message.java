package final_exam;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String request;
	private String itemDescription;
	private String username;
	private String password;
	private String returnMessage;
	private ArrayList<AuctionItem> itemList = new ArrayList<AuctionItem>();
	 
	
	public Message(String request, String usr, String pass)
	{
		this.request = request;
		this.username = usr;
		this.password = pass;
	}
	
	public Message(String request)
	{
		this.request = request;
	}
	
	//getters and setters
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	
	
	public ArrayList<AuctionItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<AuctionItem> itemList) {
		this.itemList = itemList;
	}

	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
