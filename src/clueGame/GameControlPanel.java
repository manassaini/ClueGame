package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField player;
	private JTextField roll;
	private JTextField guess;
	
	public GameControlPanel() {
		setLayout(new GridLayout(4,0));
		JPanel panel = createTurnPanel();
		add(panel);
		
		panel = createRollPanel();
		add(panel);
		
		panel = createGuessPanel();
		add(panel);
		/*
		panel = createTwoButtons();
		add(panel);
		
		/*
		JPanel turnAndRoll = new JPanel();
		turnAndRoll.setLayout(new GridLayout(1,2));
		JLabel playerLabel = new JLabel("Whose turn?");
		JLabel rollLabel = new JLabel("Roll:");
		turnAndRoll.add(playerLabel);
		turnAndRoll.add(turnAndRoll);
		*/
	}
	
	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		JLabel whoseTurn = new JLabel("Whose turn?");
		player = new JTextField(5);
		panel.add(whoseTurn);
		panel.add(player);
		//panel.setBackground("to whatever it should be");
		return panel;
	}
	private JPanel createRollPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		JLabel rollLabel = new JLabel("Roll:");
		roll = new JTextField(5);
		panel.add(rollLabel);
		panel.add(roll);
		return panel;
	}
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		guess = new JTextField(10);
		panel.add(guess);
		setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
		
	}
	private JPanel createTwoButtons() {
		return null;
	}

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();
		JLabel turnLabel = new JLabel("Whose turn?");
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		

	}

}
