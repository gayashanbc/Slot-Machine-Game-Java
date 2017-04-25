package model;

import java.util.ArrayList;
import java.util.List;

/*
 * a class that defines the functionality of a player
 * @author - Gayashan Bombuwala 2015047
 */
public class Player {
	private String name;
	private int creditAmount;
	private int betAmount;
	private List<Double> wonList;
	private List<Double> lostList;

	/*
	 * Default no-arg constructor
	 */
	public Player(String name) {
		creditAmount = 10;
		this.name = name;
		wonList = new ArrayList<Double>();
		lostList = new ArrayList<Double>();
	}

	/*
	 * getter and setter methods for the class fields
	 */
	public int getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(int creditAmount) {
		this.creditAmount += creditAmount;
	}

	public List<Double> getWonList() {
		return wonList;
	}

	public List<Double> getLostList() {
		return lostList;
	}

	public int getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(int betAmount) {
	
			this.betAmount += betAmount;
	}

	public String getName() {
		// checking whether the name variable is null or empty
		return (name == null || name.equals("")) ? "Not entered" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
