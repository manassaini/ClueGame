import java.awt.GridLayout;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardPanel extends JPanel{
	private JTextField dummy;
	private JTextField dummy2;
	private HashSet<JTextField> handCards = new HashSet<JTextField>();
	
	
	public CardPanel() {
		JPanel panel = createFirst();
		setLayout(new GridLayout(3,1));
		add(panel);
		JPanel panel2 = createSecond();
		add(panel2);
		JPanel panel3 = createThird();
		add(panel3);
		
		//add all other panels first, going to add like top down fashion
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
	}
	
	private JPanel createFirst() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JLabel inHandLabel = new JLabel("In Hand:");
		dummy = new JTextField(10);
		JLabel seenLabel = new JLabel("Seen:");
		dummy2 = new JTextField(10);
		
		panel.add(inHandLabel);
		panel.add(dummy);
		panel.add(seenLabel);
		panel.add(dummy2);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		return panel;
	}

	private JPanel createSecond() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JLabel inHandLabel = new JLabel("In Hand:");
		dummy = new JTextField(10);
		JLabel seenLabel = new JLabel("Seen:");
		dummy2 = new JTextField(10);
		
		panel.add(inHandLabel);
		panel.add(dummy);
		panel.add(seenLabel);
		panel.add(dummy2);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		return panel;
	}
	
	private JPanel createThird() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JLabel inHandLabel = new JLabel("In Hand:");
		dummy = new JTextField(10);
		JLabel seenLabel = new JLabel("Seen:");
		dummy2 = new JTextField(10);
		
		panel.add(inHandLabel);
		panel.add(dummy);
		panel.add(seenLabel);
		panel.add(dummy2);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		return panel;
	}
	
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

	}

}
