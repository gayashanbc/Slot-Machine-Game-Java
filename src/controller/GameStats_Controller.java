package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Player;

/*
 * Controller class of the statistics window of the slot machine game
 * @author - Gayashan Bombuwala 2015047
 */
public class GameStats_Controller {
	private Player player;
	private List<Double> gameRecordList;
	private double averageNettedAmount;

	/*
	 * Constructor
	 * 
	 * @param player - the current player of the SlotMachine_UI controller
	 */
	public GameStats_Controller(Player player) {
		this.player = player;
		gameRecordList = new ArrayList<Double>(); // a super set of the game
													// records
		gameRecordList.addAll(player.getWonList());
		gameRecordList.addAll(player.getLostList());
		averageNettedAmount = 0;
	}

	/*
	 * saves the statistics of the current player
	 */
	public void saveStats() {
		try {
			FileWriter fw = new FileWriter(LocalDate.now() + ".txt", true); // opening
																			// the
																			// file
																			// with
																			// append
																			// mode
			PrintWriter pw = new PrintWriter(fw);

			// writing the record to the text file
			pw.println("Name: " + player.getName());
			pw.println("Total Wins: " + player.getWonList().size());
			pw.println("Total Loses: " + player.getLostList().size());
			pw.println("Average Netted Amount: " + getAverageNettedAmount());
			pw.println("___________________________________________________");
			pw.println();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// getter method of the player variable
	public Player getPlayer() {
		return player;
	}

	// getter method of the getGameRecordList (superset of game records)
	// variable
	public List<Double> getGameRecordList() {
		return gameRecordList;
	}

	public double getAverageNettedAmount() {
		return averageNettedAmount;
	}

	public void setAverageNettedAmount(double averageNettedAmount) {
		this.averageNettedAmount = averageNettedAmount;
	}

}
