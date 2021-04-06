package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField playerName;
	private JTextField playerRoll;
	private JTextField guessField;
	//private JTextField guessResultField;

	public GameControlPanel() {
		JPanel panel = createTop();
		setLayout(new GridLayout(2,0));
		add(panel);
		JPanel panel1 = createBottom();
		add(panel1);
	}

	private JPanel createTop() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));

		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		JLabel turnLabel = new JLabel("Whose turn?");
		playerName = new JTextField(10);
		turnPanel.add(turnLabel);
		turnPanel.add(playerName);
		panel.add(turnPanel);

		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1,2));
		JLabel rollLabel = new JLabel("Roll");
		playerRoll = new JTextField(1);
		rollPanel.add(rollLabel);
		rollPanel.add(playerRoll);
		panel.add(rollPanel);

		JButton makeAccusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT");
		panel.add(makeAccusation);
		panel.add(next);

		return panel;
	}

	private JPanel createBottom() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));

		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessField = new JTextField();
		guessPanel.add(guessField);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		panel.add(guessPanel);

		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1,0));
		guessField = new JTextField();
		guessResultPanel.add(guessField);
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		panel.add(guessResultPanel);

		return panel;
	}

	public void setTurn(ComputerPlayer player, int roll) {
		String name = player.getName();
		Color color = player.getColor();
		
	}
	
	public void setGuess(String guess) {
		
	}
	public void setGuessResult(String guessResult) {
		
	}

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}