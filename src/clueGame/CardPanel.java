package clueGame;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardPanel extends JPanel{
	private Set<Card> peopleSeen;
	private Set<Card> weaponsSeen;
	private Set<Card> roomsSeen;
	private Set<Card> peopleHand;
	private Set<Card> weaponsHand;
	private Set<Card> roomsHand;
	private HashSet<JTextField> handCardsPeople = new HashSet<JTextField>();
	private HashSet<JTextField> handCardsWeapons = new HashSet<JTextField>();
	private HashSet<JTextField> handCardsRooms = new HashSet<JTextField>();
	private HashSet<JTextField> seenCardsPeople = new HashSet<JTextField>();
	private HashSet<JTextField> seenCardsWeapons = new HashSet<JTextField>();
	private HashSet<JTextField> seenCardsRooms = new HashSet<JTextField>();
	
	
	public CardPanel() {
		setLayout(new GridLayout(3,1));
		JPanel panel = createFirst();
		
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
		if (peopleHand != null) {
			for (Card c: peopleHand) {
			JTextField field = new JTextField(c.getCardName());
			handCardsPeople.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			handCardsPeople.add(field);
		}
		
		JLabel seenLabel = new JLabel("Seen:");
		if (peopleSeen!= null) {
			for (Card c: peopleSeen) {
			JTextField field = new JTextField(c.getCardName());
			seenCardsPeople.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			seenCardsPeople.add(field);
		}
		
		
		panel.add(inHandLabel);
		for (JTextField field: handCardsPeople) {
			panel.add(field);
		}
		
		panel.add(seenLabel);
		for (JTextField field: seenCardsPeople) {
			panel.add(field);
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		return panel;
	}

	private JPanel createSecond() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JLabel inHandLabel = new JLabel("In Hand:");
		if (roomsHand != null) {
			for (Card c: roomsHand) {
				JTextField field = new JTextField(c.getCardName());
				handCardsRooms.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			handCardsRooms.add(field);
		}
		
		JLabel seenLabel = new JLabel("Seen:");
		if (roomsSeen != null) {
			for (Card c: roomsSeen) {
				JTextField field = new JTextField(c.getCardName());
				seenCardsRooms.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			seenCardsRooms.add(field);
		}
		
		
		panel.add(inHandLabel);
		for (JTextField field: handCardsRooms) {
			panel.add(field);
		}
		
		
		panel.add(seenLabel);
		for (JTextField field: seenCardsRooms) {
			panel.add(field);
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		return panel;
	}
	
	private JPanel createThird() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JLabel inHandLabel = new JLabel("In Hand:");
		if (weaponsHand != null) {
			for (Card c: weaponsHand) {
				JTextField field = new JTextField(c.getCardName());
				handCardsWeapons.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			handCardsWeapons.add(field);
		}
		
		JLabel seenLabel = new JLabel("Seen:");
		if (weaponsSeen != null) {
			for (Card c: weaponsSeen) {
				JTextField field = new JTextField(c.getCardName());
				seenCardsWeapons.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			seenCardsWeapons.add(field);
		}
		
		
		panel.add(inHandLabel);
		for (JTextField field: handCardsWeapons) {
			panel.add(field);
		}
		
		panel.add(seenLabel);
		for (JTextField field: seenCardsWeapons) {
			panel.add(field);
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		return panel;
	}
	
	public static void main(String[] args) {
		Set<Card> peopleHand = new HashSet<Card>();
		Set<Card> weaponsHand = new HashSet<Card>();
		Set<Card> roomsHand = new HashSet<Card>();
		Set<Card> peopleSeen = new HashSet<Card>();
		Set<Card> weaponsSeen = new HashSet<Card>();
		Set<Card> roomsSeen = new HashSet<Card>();
		
		Card card1 = new Card("Knife");
		Card card2 = new Card("Sophie");
		Card card3 = new Card("Museum");
		
		peopleHand.add(card2);
		weaponsHand.add(card1);
		roomsHand.add(card3);
		
		
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		
	}

}
