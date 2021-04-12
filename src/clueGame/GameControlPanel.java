package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

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
	private JTextField guessResult;
	private JButton next;
	private JButton makeAccusation;
	private static final int MAX_ROLL = 6;
	
	private Board board = Board.getInstance();

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

		this.makeAccusation = new JButton("Make Accusation");
		this.next = new JButton("NEXT");
		panel.add(this.makeAccusation);
		panel.add(this.next);
		next.addActionListener(new NextListener());

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
		guessResult = new JTextField();
		guessResultPanel.add(guessResult);
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		panel.add(guessResultPanel);

		return panel;
	}

	public void setTurn(Player player, int roll) {
		String name = player.getName();
		Color color = player.getColor();
		playerName.setBackground(color);
		playerName.setText(name);
		playerRoll.setText(String.valueOf(roll));
	}
	
	public void setGuess(String guess) {
		guessField.setText(guess);
	}
	
	
	public void setGuessResult(String guessResultText) {
		guessResult.setText(guessResultText);
	}
	
	public JButton getNextButton() {
		return this.next;
	}
	
	public JButton getAccusationButton() {
		return this.makeAccusation;
	}
	
	private class NextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			board.nextClicked();
		}
		
	}

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, Color.orange), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

}