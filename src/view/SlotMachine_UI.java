package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.SlotMachine_Controller;

/*
 * User interface class of the main window of the slot machine game
 * @author - Gayashan Bombuwala 2015047
 */
public class SlotMachine_UI extends JFrame {
	private JButton btnReel_1;
	private JButton btnReel_2;
	private JButton btnReel_3;

	private JPanel buttonAreaPanel;
	private JPanel displayArePanel;
	private JPanel reel1Panel;
	private JPanel reel2Panel;
	private JPanel reel3Panel;

	private JButton btnAddCoin;
	private JButton btnBetOne;
	private JButton btnBetMax;
	private JButton btnReset;
	private JButton btnStats;
	private JButton btnNewGame;

	private JLabel lblCreditArea;
	private JLabel lblBetArea;
	private JButton btnSpin;

	private SlotMachine_Controller controller;

	private Timer timerReel_1;
	private Timer timerReel_2;
	private Timer timerReel_3;

	private int roundCount1, roundCount2, roundCount3;// to monitor the spinning
														// count of each reel
	private boolean isSpinning; // to check whether the reels are spinning

	/*
	 * Default no-arg constructor
	 */
	public SlotMachine_UI() {
		super("Slot Machine Game"); // name of the window
		controller = new SlotMachine_Controller(); // initializes the controller

		// setups GUI components
		prepare_GUI();
		addListeners();

		// sets the name of the initial player
		String playerName = JOptionPane.showInputDialog(this, "Enter your name to continue");
		controller.getPlayer().setName(playerName);
	}

	/*
	 * prepares GUI components for this window
	 */
	private void prepare_GUI() {
		setLayout(new BorderLayout());

		// creating the main sections of this windows
		createButtonAreaPannel();
		createReels();
		createDisplayAreaPannel();

		// customizing components
		setComponentPadding();
		setComponentFont();

		// adding components to the windows's layout
		add(buttonAreaPanel, BorderLayout.NORTH);
		add(reel1Panel, BorderLayout.WEST);
		add(reel2Panel, BorderLayout.CENTER);
		add(reel3Panel, BorderLayout.EAST);
		add(displayArePanel, BorderLayout.SOUTH);

		setSize(900, 700);
		setLocationRelativeTo(null); // appears the windows in the center of the
										// screen
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack(); // removes unnecessary space in the window
		setVisible(true);
	}

	/*
	 * Adds event listeners to UI components
	 */
	private void addListeners() {
		// listener for the "Statistics" button
		btnStats.addActionListener(event -> {
			// ensures whether the player has played at least one game
			if (controller.getPlayer().getWonList().size() == 0 && controller.getPlayer().getLostList().size() == 0) {
				showMsgBox("No game records available",
						"You must have played at least once in order to view the statistics", 0);
			} else {
				new GameStats_UI(controller.getPlayer()); // displaying the
															// statistics window
			}
		});

		// listener for the "Add Coin" button
		btnAddCoin.addActionListener(event -> updateCreditAndBetLabels(1, 0));

		// listener for the "Bet One" button
		btnBetOne.addActionListener(event -> {
			updateCreditAndBetLabels(0, 1);
		});

		// listener for the "Bet Max" button
		btnBetMax.addActionListener(event -> {
			updateCreditAndBetLabels(0, 3);
		});

		// listener for the "Reset" button
		btnReset.addActionListener(event -> {
			// ensures that the player has bet at least one coin
			if (controller.getPlayer().getBetAmount() != 0) {
				controller.resetBet();
				updateCreditAndBetLabels(0, 0);
			} else {
				showMsgBox("Unable to reset coins", "You haven't bet any coins yet!", 0);
			}
		});

		// listener for the "New Game" button
		btnNewGame.addActionListener(event -> {
			// setting new plaer's name
			String playerName = JOptionPane.showInputDialog(this, "Enter your name to continue");
			controller.startNewGame(playerName);

			// loading the defaults
			updateCreditAndBetLabels(0, 0);
			btnReel_1.setIcon(controller.createIcon("../img/plum.png"));
			btnReel_2.setIcon(controller.createIcon("../img/plum.png"));
			btnReel_3.setIcon(controller.createIcon("../img/plum.png"));
		});

		// listener for the "Spin" button
		btnSpin.addActionListener(event -> beginSpinning());

		// listeners for the three reels
		btnReel_1.addActionListener(event -> stopSpinning(1));
		btnReel_2.addActionListener(event -> stopSpinning(2));
		btnReel_3.addActionListener(event -> stopSpinning(3));

		// listener for the window
		addWindowListener(new WindowAdapter() {

			// capturing the window closing event
			@Override
			public void windowClosing(WindowEvent e) {
				int isConfirmed = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to exit?",
						"This application is about to be closed", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (isConfirmed == JOptionPane.YES_OPTION) {// checks player's
															// choice
					dispose(); // destroys the window object
				}
			}
		});
	}

	/*
	 * creates UI components related to the button are panel
	 */
	private void createButtonAreaPannel() {
		buttonAreaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));

		btnAddCoin = new JButton("Add Coin");
		btnBetOne = new JButton("Bet One");
		btnBetMax = new JButton("Bet Max");
		btnReset = new JButton("Reset");
		btnStats = new JButton("Statistics");
		btnNewGame = new JButton("New Game");

		buttonAreaPanel.add(btnAddCoin);
		buttonAreaPanel.add(btnBetOne);
		buttonAreaPanel.add(btnBetMax);
		buttonAreaPanel.add(btnReset);
		buttonAreaPanel.add(btnStats);
		buttonAreaPanel.add(btnNewGame);
	}

	/*
	 * creates and customizes the three reels
	 */
	private void createReels() {
		reel1Panel = new JPanel(new FlowLayout());
		reel2Panel = new JPanel(new FlowLayout());
		reel3Panel = new JPanel(new FlowLayout());

		btnReel_1 = new JButton();
		btnReel_2 = new JButton();
		btnReel_3 = new JButton();

		// setting initial symbols for the reels
		btnReel_1.setIcon(controller.createIcon("../img/plum.png"));
		btnReel_2.setIcon(controller.createIcon("../img/plum.png"));
		btnReel_3.setIcon(controller.createIcon("../img/plum.png"));

		btnReel_1.setBackground(Color.LIGHT_GRAY);
		btnReel_2.setBackground(Color.LIGHT_GRAY);
		btnReel_3.setBackground(Color.LIGHT_GRAY);

		reel1Panel.add(btnReel_1);
		reel2Panel.add(btnReel_2);
		reel3Panel.add(btnReel_3);
	}

	/*
	 * creates UI components related to the display are panel
	 */
	private void createDisplayAreaPannel() {
		displayArePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 10));

		lblCreditArea = new JLabel("Credit Area: 10");
		lblBetArea = new JLabel("Bet Area: 0");
		btnSpin = new JButton("Spin");

		displayArePanel.add(lblCreditArea);
		displayArePanel.add(btnSpin);
		displayArePanel.add(lblBetArea);
	}

	/*
	 * adds padding to the UI components
	 */
	private void setComponentPadding() {
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		reel1Panel.setBorder(padding);
		reel2Panel.setBorder(padding);
		reel3Panel.setBorder(padding);

		displayArePanel.setBorder(padding);
		buttonAreaPanel.setBorder(padding);
	}

	/*
	 * changes the font of the UI components
	 */
	private void setComponentFont() {
		Font font = new Font("Comic Sans MS", Font.BOLD, 20);

		btnAddCoin.setFont(font);
		btnBetOne.setFont(font);
		btnBetMax.setFont(font);
		btnReset.setFont(font);
		btnStats.setFont(font);
		btnNewGame.setFont(font);
		btnSpin.setFont(font);

		font = new Font("Comic Sans MS", Font.BOLD, 30);

		lblCreditArea.setFont(font);
		lblBetArea.setFont(font);

		font = new Font("Arial", Font.PLAIN, 15);

		UIManager.put("OptionPane.buttonFont", font);
		UIManager.put("OptionPane.messageFont", font);
	}

	/*
	 * starts the reel spinning
	 */
	private void beginSpinning() {
		// check for the minimum bet amount and whether the reels are spinning
		// already
		if (controller.getPlayer().getBetAmount() > 0 && !isSpinning) {

			// setting to default values
			roundCount1 = 0;
			roundCount2 = 0;
			roundCount3 = 0;
			isSpinning = true;

			/*
			 * defining threads and thread jobs with Swing timers for the
			 * spinning of the three reels
			 */
			controller.reelThread_1 = new Thread(() -> {
				// defining the Swing timer
				timerReel_1 = new Timer(100, event -> { // callback function
														// that fires each time
														// the timer stops
					btnReel_1.setIcon(// changing the symbol
							controller.createIcon(controller.reelList.get(0).spin().get(roundCount1).getImage()));

					// assigns the currently stuck symbol in the reel to a
					// variable
					controller.setCurrentSymbol_reel1(controller.reelList.get(0).spin().get(roundCount1));

					// if the 6 symbol sequence is over, resets the counter
					// variable
					if (roundCount1 == controller.reelList.get(0).spin().size() - 1) {
						roundCount1 = 0;
					} else {
						roundCount1++;
					}
				});

				// removing the initial delay and starting the timer
				timerReel_1.setInitialDelay(0);
				timerReel_1.start();
			});
			controller.reelThread_1.start(); // starting the new thread

			controller.reelThread_2 = new Thread(() -> {
				timerReel_2 = new Timer(100, event -> {
					btnReel_2.setIcon(
							controller.createIcon(controller.reelList.get(1).spin().get(roundCount2).getImage()));
					controller.setCurrentSymbol_reel2(controller.reelList.get(1).spin().get(roundCount2));
					if (roundCount2 == controller.reelList.get(1).spin().size() - 1) {
						roundCount2 = 0;
					} else {
						roundCount2++;
					}
				});
				timerReel_2.setInitialDelay(0);
				timerReel_2.start();
			});
			controller.reelThread_2.start();

			controller.reelThread_3 = new Thread(() -> {
				timerReel_3 = new Timer(100, event -> {
					btnReel_3.setIcon(
							controller.createIcon(controller.reelList.get(2).spin().get(roundCount3).getImage()));
					controller.setCurrentSymbol_reel3(controller.reelList.get(2).spin().get(roundCount3));
					if (roundCount3 == controller.reelList.get(2).spin().size() - 1) {
						roundCount3 = 0;
					} else {
						roundCount3++;
					}
				});
				timerReel_3.setInitialDelay(0);
				timerReel_3.start();
			});
			controller.reelThread_3.start();
		} else {
			// identifying the relevant error message
			if (isSpinning) {
				showMsgBox("Unable to perform this action", "The reels are already spinning!", 0);
			} else {
				showMsgBox("No credits have been bet", "You must bet at least 1 coin to play the game!", 0);
			}
		}
	}

	/*
	 * stops a particular reel from spinning
	 */
	private void stopSpinning(int reelNo) {
		if (isSpinning) { // checks if that reel has already stopped
			try {
				if (reelNo == 1) {
					timerReel_1.stop();
				} else if (reelNo == 2) {
					timerReel_2.stop();
				} else if (reelNo == 3) {
					timerReel_3.stop();
				}

				// checks whether all the three reels have stopped spinning
				if (!timerReel_1.isRunning() && !timerReel_2.isRunning() && !timerReel_3.isRunning()) {
					try {
						// waiting for all the spinning threads to finish
						// execution
						controller.reelThread_1.join();
						controller.reelThread_2.join();
						controller.reelThread_3.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						// decide and save the result of the game
						int wonCredits = controller.saveGameResult();
						if (wonCredits > 0) { // game won
							showMsgBox("Congratulations", "You won " + wonCredits + " credits!", 1);
						} else if (wonCredits == 0)  { // game lost
							showMsgBox("Game over", "You lost! Try again.", 1);
						}else{ // game tied
							wonCredits = 0;
							showMsgBox("Congratulations", "You won, but only two symbols matched!, You can spin again. ", 1);
						}
						updateCreditAndBetLabels(wonCredits, 0);
						isSpinning = false;
					}
				}
			} catch (NullPointerException e) {// occurs when spinning threads
												// and timers haven't been
												// initialized
				showMsgBox("Problem encountered", "You haven't started spinning the reels yet!", 2);
			}
		}

	}

	/*
	 * updates the credit are label and the bet area label
	 * 
	 * @param creditAmount - amount that needs to be set for the credit area
	 * label
	 * 
	 * @param betAmount - amount that needs to be set for the bet area label
	 */
	private void updateCreditAndBetLabels(int creditAmount, int betAmount) {
		lblBetArea.setText("Bet Area: " + controller.addBet(betAmount));
		lblCreditArea.setText("Credit Area: " + controller.addCoin(creditAmount));
	}

	/*
	 * displays a JOptionPane Message Dialog based on given options
	 * 
	 * @param title - title of the Message dialog
	 * 
	 * @param message - message of the Message dialog
	 * 
	 * @param messageType - icon of the Message dialog
	 */
	private void showMsgBox(String title, String message, int messageType) {
		switch (messageType) {
		case 1:
			messageType = JOptionPane.INFORMATION_MESSAGE;
			break;
		case 2:
			messageType = JOptionPane.WARNING_MESSAGE;
			break;
		default:
			messageType = JOptionPane.ERROR_MESSAGE;
			break;
		}
		JOptionPane.showMessageDialog(this, message, title, messageType);
	}

}
