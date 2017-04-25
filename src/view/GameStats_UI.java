package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import controller.GameStats_Controller;
import model.Player;

/*
 * User Interface class of the statistics window of the slot machine game
 * @author - Gayashan Bombuwala 2015047
 */
public class GameStats_UI extends JFrame {
	private JButton btnSave;
	private JLabel lblPlayerName;
	private JLabel lblTotalWIns;
	private JLabel lblTotalLoses;
	private JLabel lblAverageNettedAmount;
	private JProgressBar progressBar;

	private JPanel buttonAreaPanel;
	private JPanel displayArePanel;
	private JPanel panel;

	private GameStats_Controller controller;

	/*
	 * Default no-arg constructor
	 */
	public GameStats_UI(Player player) {
		super("Game Statistics"); // name of the window
		controller = new GameStats_Controller(player);

		// setups GUI components
		prepareGUI();
		addListeners();
	}

	/*
	 * prepares GUI components for this window
	 */
	private void prepareGUI() {
		setLayout(new BorderLayout());

		// setting component texts
		lblPlayerName = new JLabel("Player Name: " + controller.getPlayer().getName());
		lblTotalLoses = new JLabel("Total Loses: " + controller.getPlayer().getLostList().size());
		lblTotalWIns = new JLabel("Total Wins: " + controller.getPlayer().getWonList().size());
		lblAverageNettedAmount = new JLabel();
		btnSave = new JButton("Save statistics");

		lblTotalWIns.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		lblTotalLoses.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		lblPlayerName.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		lblAverageNettedAmount.setHorizontalAlignment((int) CENTER_ALIGNMENT);

		double totalCredits = 0.0;

		for (int i = 0; i < controller.getGameRecordList().size(); i++) {
			totalCredits += controller.getGameRecordList().get(i);
		}

		controller.setAverageNettedAmount(totalCredits / controller.getGameRecordList().size());
		lblAverageNettedAmount.setText("Average Netted Amount: " + controller.getAverageNettedAmount());

		// I'm using a JProgressbar to visualize the average amount netted per
		// game, since I can show it as a percentage
		progressBar = new JProgressBar(-100, 100);
		progressBar.setValue((int) Math.round(controller.getAverageNettedAmount()));
		progressBar.setStringPainted(true);

		// setting component layouts
		buttonAreaPanel = new JPanel(new FlowLayout());
		displayArePanel = new JPanel(new GridLayout(0, 1));
		panel = new JPanel(new FlowLayout());

		displayArePanel.add(lblPlayerName);
		displayArePanel.add(lblTotalWIns);
		displayArePanel.add(lblTotalLoses);
		displayArePanel.add(lblAverageNettedAmount);
		buttonAreaPanel.add(btnSave);
		panel.add(progressBar);

		setComponentFont();
		setComponentPadding();

		// adding components to the windows's layout
		add(displayArePanel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		add(buttonAreaPanel, BorderLayout.SOUTH);

		setSize(800, 300);
		setLocationRelativeTo(this);// appears the windows in the center of the
									// screen
		pack();// removes unnecessary space in the window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/*
	 * Adds event listeners to UI components
	 */
	private void addListeners() {
		btnSave.addActionListener(event -> {
			controller.saveStats();
			JOptionPane.showMessageDialog(this, "Statistics successfully saved!");
		});
	}

	/*
	 * adds padding to the UI components
	 */
	private void setComponentPadding() {
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		progressBar.setBorder(padding);
		displayArePanel.setBorder(padding);
		buttonAreaPanel.setBorder(padding);

		padding = BorderFactory.createEmptyBorder(0, 30, 5, 30);
		lblPlayerName.setBorder(padding);
		lblTotalWIns.setBorder(padding);
		lblTotalLoses.setBorder(padding);
		lblAverageNettedAmount.setBorder(padding);
	}

	/*
	 * changes the font of the UI components
	 */
	private void setComponentFont() {
		Font font = new Font("Comic Sans MS", Font.BOLD, 20);

		lblTotalLoses.setFont(font);
		lblTotalWIns.setFont(font);
		lblAverageNettedAmount.setFont(font);
		btnSave.setFont(font);

		font = new Font("Comic Sans MS", Font.BOLD, 25);

		lblPlayerName.setFont(font);

		font = new Font("Comic Sans MS", Font.BOLD, 15);
		progressBar.setFont(font);
	}
}
