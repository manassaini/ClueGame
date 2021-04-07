package clueGame;

import java.awt.HeadlessException;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	Board board;

	public ClueGame() {
		super();
		board = Board.getInstance();
		// set the file names to use my config files
		//used mark's files for this, not really working
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(700, 900);
	}

	public static void main(String[] args) {
		ClueGame cluegame = new ClueGame();
	}
}
