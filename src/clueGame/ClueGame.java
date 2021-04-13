package clueGame;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	static Board board;
	static GameControlPanel gameControlPanel;
	CardPanel panel;
	static JButton nextButton;

	public ClueGame() {
		super();
		this.panel = new CardPanel();
		this.gameControlPanel = new GameControlPanel();
		this.nextButton = gameControlPanel.getNextButton();
		
		board = Board.getInstance();
		// set the file names to use my config files
		//used mark's files for this, not really working
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		add(panel, BorderLayout.EAST);
		add(gameControlPanel, BorderLayout.SOUTH);
		add(board, BorderLayout.CENTER);
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.setControlPanel(gameControlPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(700, 900);
		setVisible(true);
	}

	public static void main(String[] args) {
		ClueGame cluegame = new ClueGame();
		board.displayStartMessage();
		board.nextClicked();
	}

	
	
	
	
	
	
}
