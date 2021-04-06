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
	private static Set<Card> peopleSeen = new HashSet<Card>();
	private static Set<Card> weaponsSeen = new HashSet<Card>();
	private static Set<Card> roomsSeen = new HashSet<Card>();
	private static Set<Card> peopleHand = new HashSet<Card>();
	private static Set<Card> weaponsHand = new HashSet<Card>();
	private static Set<Card> roomsHand = new HashSet<Card>();
	
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
		return updateDisplay(peopleSeen, peopleHand, "People", panel);
	}

	private JPanel createSecond() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		return updateDisplay(roomsSeen, roomsHand, "Rooms", panel);
	}
	
	private JPanel createThird() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		return updateDisplay(weaponsSeen, weaponsHand, "Weapons", panel);
	}
	
	
	
	public void setSeenPeople(Card person) {
		peopleSeen.add(person);
	}
	
	public void setPeopleHand(Card person) {
		peopleHand.add(person);
	}
	
	public void setSeenWeapons(Card weapon) {
		weaponsSeen.add(weapon);
	}
	
	public void setWeaponsHand(Card weapon) {
		weaponsHand.add(weapon);
	}
	
	public void setSeenRooms(Card room) {
		roomsSeen.add(room);
	}
	
	public void setRoomsHand(Card room) {
		roomsHand.add(room);
	}
	
	
	public JPanel updateDisplay(Set<Card> seenCards, Set<Card> handCards, String name, JPanel panel) {
		HashSet<JTextField> handBoxes = new HashSet<JTextField>();
		HashSet<JTextField> seenBoxes = new HashSet<JTextField>();
		
		JLabel inHandLabel = new JLabel("In Hand:");
		if (!handCards.isEmpty()) {
			for (Card c:handCards) {
				JTextField field = new JTextField(c.getCardName());
				handBoxes.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			handBoxes.add(field);
		}
		
		JLabel seenLabel = new JLabel("Seen:");
		if (!seenCards.isEmpty()) {
			for (Card c: seenCards) {
				JTextField field = new JTextField(c.getCardName());
				seenBoxes.add(field);
			}
		}
		else {
			JTextField field = new JTextField("None");
			seenBoxes.add(field);
		}
		
		
		panel.add(inHandLabel);
		for (JTextField field: handBoxes) {
			panel.add(field);
		}
		
		panel.add(seenLabel);
		for (JTextField field: seenBoxes) {
			panel.add(field);
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), name));
		return panel;
	}
	
	
	
	
	
	public static void main(String[] args) {
		Card card1 = new Card("Knife");
		Card card2 = new Card("Sophie");
		Card card3 = new Card("Mario");
		
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		panel.setPeopleHand(card2);
		panel.setPeopleHand(card3);
//		updateDisplay(peopleHand, peopleSeen, "People", panel);
		panel.setWeaponsHand(card1);
		
		

	}

}
