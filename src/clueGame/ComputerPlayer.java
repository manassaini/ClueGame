package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name) {
		super(name);
		ArrayList<Card> hand = new ArrayList<Card>();
		this.hand = hand;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addToHand(Card card) {
		// TODO Auto-generated method stub
		this.hand.add(card);
	}

	
	@Override
	public void removeFromHand(Card card) {
		// TODO Auto-generated method stub
		this.hand.remove(card);
	}
	
	@Override
	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	@Override
	public boolean isComputer() {
		return true;
	}

}
