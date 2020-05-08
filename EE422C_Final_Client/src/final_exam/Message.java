package final_exam;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String request;
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayList<Double> currentBids = new ArrayList<Double>();
	private String itemDescription;
	private String username;
	private String password;
	private String returnMessage; 
	
	public Message(String request, String usr, String pass)
	{
		this.request = request;
		this.username = usr;
		this.password = pass;
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
	
	
	public ArrayList<String> getItems() {
		return items;
	}
	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	
	
	public ArrayList<Double> getCurrentBids() {
		return currentBids;
	}
	public void setCurrentBids(ArrayList<Double> currentBids) {
		this.currentBids = currentBids;
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
