package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Player;
import model.Reel;
import model.Symbol;

/*
 * Controller class of the main window of the slot machine game
 * @author - Gayashan Bombuwala 2015047
 */
public class SlotMachine_Controller {
	public List<Reel> reelList;
	private Player player;
	public Thread reelThread_1;
	public Thread reelThread_2;
	public Thread reelThread_3;
	private Symbol currentSymbol_reel1;
	private Symbol currentSymbol_reel2;
	private Symbol currentSymbol_reel3;
	private int matchigSymbolValue;
	private boolean isTie;

	/*
	 * Default no-arg constructor
	 */
	public SlotMachine_Controller() {
		reelList = new ArrayList<Reel>();

		reelList.add(new Reel());
		reelList.add(new Reel());
		reelList.add(new Reel());

		player = new Player(null);
	}

	/*
	 * adds coins to the player's credit amount
	 * 
	 * @param amount - amount that needs to be added
	 * 
	 * @return - player's new credit amount
	 */
	public int addCoin(int amount) {
		player.setCreditAmount(amount);
		return player.getCreditAmount();
	}

	/*
	 * adds a bet to the player's credit amount
	 * 
	 * @param amount - amount that needs to be added
	 * 
	 * @return - player's new bet amount
	 */
	public int addBet(int amount) {
		// checks whether the player has enough credit coins
		if (player.getCreditAmount() >= amount) {
			player.setBetAmount(amount);
			player.setCreditAmount(-amount);
		} else {
			JOptionPane.showMessageDialog(null, "Your cureent credit amount is not enough to add this bet!",
					"Insufficient Credits", JOptionPane.ERROR_MESSAGE);
		}
		return player.getBetAmount();
	}

	/*
	 * zeros the player's current bet amount and adds that amount to the
	 * player's credit amount
	 * 
	 * @return - player's new credit amount
	 */
	public int resetBet() {
		player.setCreditAmount(player.getBetAmount());
		player.setBetAmount(-player.getBetAmount());
		return player.getCreditAmount();
	}

	// getter method of the player variable
	public Player getPlayer() {
		return player;
	}

	/*
	 * assigns a new player reference to the Player instance
	 */
	public void startNewGame(String playerName) {
		this.player = new Player(playerName);
	}

	/*
	 * calculates the average amount of credits that the player has bet for the
	 * game
	 * 
	 * @return - the average bet amount
	 */
	public double calculateNettedAmount() {
		return (player.getBetAmount() / (player.getBetAmount() * 1.0 + player.getCreditAmount())) * 100;
	}

	/*
	 * calculates the amount of credits that the player has won game
	 * 
	 * @return - the amount of won credits
	 */
	public int calculateWonCredits() {
		return player.getBetAmount() * matchigSymbolValue;
	}

	/*
	 * adds the game result to the player's won list or lost list
	 * 
	 * @return - the amount of won credits (0 if lost)
	 */
	public int saveGameResult() {
		int wonCredits = 0;
		// Lost = 0, Tie = -1
		boolean isWon = isWon();
		if (isWon) {
			wonCredits = calculateWonCredits();
			player.getWonList().add((double) wonCredits);
			player.setBetAmount(-player.getBetAmount());
		} else if (!isWon && isTie) {
			wonCredits = -1;
			player.getWonList().add((double) wonCredits);
			isTie = false;
		} else {
			player.getLostList().add((double) -player.getBetAmount());
			player.setBetAmount(-player.getBetAmount());
		}

		return wonCredits;
	}

	/*
	 * finds the result of the game
	 * 
	 * @return - whether the player has won or lost
	 */
	public boolean isWon() {

		// comparing the values of currently stuck stuck symbols in all the
		// three reels
		int[] result1 = currentSymbol_reel1.compareValues(currentSymbol_reel2);
		int[] result2 = currentSymbol_reel1.compareValues(currentSymbol_reel3);
		int[] result3 = currentSymbol_reel3.compareValues(currentSymbol_reel2);

		// finding the matching symbol if any
		if (result1[0] == 1 && result2[0] == 1 && result3[0] == 1) {
			if (result1[0] == 1) {
				matchigSymbolValue = result1[1];
			} else if (result2[0] == 1) {
				matchigSymbolValue = result2[1];
			} else if (result3[0] == 1) {
				matchigSymbolValue = result3[1];
			}
			return true;
		} else if ((result1[0] == 1 || result2[0] == 1 || result3[0] == 1)
				&& !(result1[0] == 1 && result2[0] == 1 && result3[0] == 1)) {
			isTie = true;
			return false;
		} else {
			return false;
		}
	}

	/*
	 * creates and ImageIcon using a given path
	 * 
	 * @param path - path to the image
	 * 
	 * @return - the ImageIcon of the given path
	 */
	public ImageIcon createIcon(String path) {
		URL url = getClass().getResource(path);
		if (url == null) { // if the image is not found
			System.err.println("error opening: " + path);
		}
		ImageIcon icon = new ImageIcon(url);
		return icon;
	}

	// getter method of the currentSymbol variable of the first reel
	public Symbol getCurrentSymbol_reel1() {
		return currentSymbol_reel1;
	}

	// setter method of the currentSymbol variable of the first reel
	public void setCurrentSymbol_reel1(Symbol currentSymbol_reel1) {
		this.currentSymbol_reel1 = currentSymbol_reel1;
	}

	// getter method of the currentSymbol variable of the second reel
	public Symbol getCurrentSymbol_reel2() {
		return currentSymbol_reel2;
	}

	// setter method of the currentSymbol variable of the second reel
	public void setCurrentSymbol_reel2(Symbol currentSymbol_reel2) {
		this.currentSymbol_reel2 = currentSymbol_reel2;
	}

	// getter method of the currentSymbol variable of the third reel
	public Symbol getCurrentSymbol_reel3() {
		return currentSymbol_reel3;
	}

	// setter method of the currentSymbol variable of the third reel
	public void setCurrentSymbol_reel3(Symbol currentSymbol_reel3) {
		this.currentSymbol_reel3 = currentSymbol_reel3;
	}

}
