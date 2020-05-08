package final_exam;

import java.util.ArrayList;

public class AuctionItem {
	private String name;
	private String userId;
	private double currentPrice;
	private int secondsLeft;
	private boolean expired;
	
	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getSecondsLeft() {
		return secondsLeft;
	}

	public void setSecondsLeft(int secondsLeft) {
		this.secondsLeft = secondsLeft;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public AuctionItem(String name, double price)
	{
		this.name = name;
		this.currentPrice = price;
		this.userId = "";			//starter item, no user id
		this.secondsLeft = 86400;   //one day default
		this.expired = false;
	}
}
