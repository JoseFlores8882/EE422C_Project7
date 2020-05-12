package final_exam;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class AuctionItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String userId;	//this is who made it
	private Double currentPrice;
	private Double buyPrice;
	private String timeLeft;
	private int secondsLeft;
	private boolean expired;
	private ArrayList<String> bidHistory = new ArrayList<String>();
	private String description;
	private Image itemPicture;
	private String purchaseId;
	//getters and setters
	
	
	public String getName() {
		return name;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public int getSecondsLeft() {
		return secondsLeft;
	}

	public void setSecondsLeft(int secondsLeft) {
		this.secondsLeft = secondsLeft;
	}

	public Image getItemPicture() {
		return itemPicture;
	}

	public void setItemPicture(Image itemPicture) {
		this.itemPicture = itemPicture;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String updateTimeLeft() {
		int days = secondsLeft/86400;
		int hours = (secondsLeft/3600)%24;
		int seconds = secondsLeft%60;
		this.timeLeft = days + "d " + hours + "h " + seconds +"s";
		return timeLeft;
	}

	public String getTimeLeft() {
		return timeLeft;
	}
	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public ArrayList<String> getBidHistory() {
		return bidHistory;
	}

	public void setBidHistory(ArrayList<String> bidHistory) {
		this.bidHistory = bidHistory;
	}

	public AuctionItem(String name, double price, double buyPrice, String description)
	{
		this.name = name;
		this.currentPrice = price;
		this.buyPrice = buyPrice;
		this.userId = "starter";				
		this.timeLeft = "1d 00h 00s";   //one day default
		this.purchaseId = "";
		this.secondsLeft = 86400;
		this.expired = false;
		this.description = description;
	}
	public AuctionItem(String name, String userId, double price, double buyPrice, String description, int days)
	{
		this.name = name;
		this.userId = userId;
		this.currentPrice = price;
		this.buyPrice = buyPrice;
		this.description = description;
		this.secondsLeft = days * 86400;
		this.timeLeft = days + "d 00h 00s";
	}
	public AuctionItem(String name, String userId, double price, String description, int days)
	{
		this.name = name;
		this.userId = userId;
		this.currentPrice = price;
		this.description = description;
		this.secondsLeft = days * 86400;
		this.timeLeft = days + "d 00h 00s";
	}
	
}
