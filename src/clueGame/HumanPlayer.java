package clueGame;

import java.util.ArrayList;
import java.util.Random;

public class HumanPlayer extends Player{

	public HumanPlayer(String name) {
		super(name);
		ArrayList<Card> hand = new ArrayList<Card>();
		this.hand = hand;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addToHand(Card card) {
		// TODO Auto-generated method stub
		this.hand.add(card);
		this.seen.add(card);
		if (card.getCardType() == CardType.PERSON) {
			this.unseenPeople.remove(card);
		} else if (card.getCardType() == CardType.ROOM) {
			this.unseenRooms.remove(card);
		} else {
			this.unseenWeapons.remove(card);
		}
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
		return false;
	}

	@Override
	public Card disproveSuggestion(Card person, Card weapon, Card room) {
		// TODO Auto-generated method stub
		ArrayList<Card> options = new ArrayList<Card>();
		for (Card card: this.seen) {
			if (card.equals(person)) {
				options.add(person);
			} 
			else if (card.equals(weapon)) {
				options.add(weapon);
			}
			else if (card.equals(room)) {
				options.add(room);
			}
		}

		if (options.size() > 1) {
			return this.getRandomCard(options);
		} 
		else if (options.size() == 0) {
			return null;
		}
		else {
			return options.get(0);
		}
	}
	
	public Card getRandomCard(ArrayList<Card> list) {		// based off code from geeks for geeks
		Random random = new Random();
		Card card = list.get(random.nextInt(list.size()));
		list.remove(card);
		return card;
	}
	

	@Override
	public void updateHand(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSeen(Card seenCard) {
		// TODO Auto-generated method stub
		this.seen.add(seenCard);
		if (seenCard.getCardType() == CardType.PERSON) {
			this.unseenPeople.remove(seenCard);
		} else if (seenCard.getCardType() == CardType.ROOM) {
			this.unseenRooms.remove(seenCard);
		} else {
			this.unseenWeapons.remove(seenCard);
		}
	}
}
