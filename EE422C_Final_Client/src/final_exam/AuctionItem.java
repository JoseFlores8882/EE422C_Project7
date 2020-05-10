package final_exam;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class AuctionItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String userId;
	private Double currentPrice;
	private Double buyPrice;
	private String timeLeft;
	private int secondsLeft;
	private boolean expired;
	private ArrayList<String> bidHistory = new ArrayList<String>();
	private Image itemPicture;
	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
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

	public AuctionItem(String name, double price, double buyPrice)
	{
		this.name = name;
		this.currentPrice = price;
		this.buyPrice = buyPrice;
		this.userId = "starter";				
		this.timeLeft = "1d 00h 00s";   //one day default
		this.secondsLeft = 86400;
		this.expired = false;
	}
}
