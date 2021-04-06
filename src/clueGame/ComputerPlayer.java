package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	

	public ComputerPlayer(String name) {
		super(name);
		this.hand = new ArrayList<Card>();
		
		Board theInstance = Board.getInstance();
		this.unseenWeapons = theInstance.getWeaponCards();
		this.unseenRooms = theInstance.getRoomCards();
		this.unseenPeople = theInstance.getPersonCards();
	}
	
	
	
	
	public Solution createSuggestion(Card currentRoom) {			
		Solution thisSolution = new Solution();						// object to be returned
		
		Card person = this.getRandomCard(this.unseenPeople);		// random unseen person
		thisSolution.setPerson(person);
		
		Card weapon = this.getRandomCard(this.unseenWeapons);		// random unseen weapon
		thisSolution.setWeapon(weapon);
		
		thisSolution.setRoom(currentRoom);							// current room, parameter
		
		return thisSolution;
	}
	
	
	
	
	@Override
	public void addToHand(Card card) {							// adds card to hand, removes the card from unseen list using method below
		this.hand.add(card);
		this.updateSeen(card);
	}
	
	
	
	@Override
	public void updateSeen(Card seenCard) {								// adding another card to seen list, could be through other player suggestion etc
		// TODO Auto-generated method stub
		this.seen.add(seenCard);
		this.unseen.remove(seenCard);
		if (seenCard.getCardType() == CardType.PERSON) {				// if person, removes from unseen people, if room, etc
			this.unseenPeople.remove(seenCard);
		} else if (seenCard.getCardType() == CardType.ROOM) {
			this.unseenRooms.remove(seenCard);
		} else {
			this.unseenWeapons.remove(seenCard);
		}
	}
	
	
	
	
	public BoardCell selectTarget(Set<BoardCell> targets) {					// pick a semi-random target based on criteria
		Board board = null;
		board = board.getInstance();
		ArrayList<BoardCell> newRoomCell = new ArrayList<BoardCell>();		// array list of cells of unseen rooms
		ArrayList<BoardCell> allTargets = new ArrayList<BoardCell>();		// array list of walkways, seen rooms, and other
		Map<Character, Room> roomMap = board.getRoomMap();					// initial and room map, used to connect cell object to room intial to room name


		// create arraylist of board cells in unseen room
		for (BoardCell cell: targets) {										
			if (cell.getIsRoom()) {
				for (Card room: this.unseenRooms) {
					char initial = cell.getInitial();
					String unseenName = room.getCardName();
					String mapName = roomMap.get(initial).getName();
					if (mapName.contentEquals(unseenName)) {   	// if name of unseen room matches name of cell's room
						newRoomCell.add(cell);
					}
				}
			}
			allTargets.add(cell);											// all targets, will only be used if no targets in newRoomCell
		}

		// choose target
		if (newRoomCell.size() == 1) {										// one potential new room
			return newRoomCell.get(0);
		} 
		else if (newRoomCell.size() > 1) {									// multiple potential new rooms
			int index = (int) (Math.random() * newRoomCell.size());
			return newRoomCell.get(index);
		} 
		else {																// no potential new rooms
			int index = (int) (Math.random() * allTargets.size());
			return allTargets.get(index);
		}
		
		
	}
	
	
	
	
	@Override
	public Card disproveSuggestion(Card person, Card weapon, Card room) {			// parameter of a solution in cards
		ArrayList<Card> options = new ArrayList<Card>();							// arraylist of potential options for matching cards
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
		// choose matching card

		if (options.size() > 1) {						// more than one option, choose random
			return this.getRandomCard(options);
		} 
		else if (options.size() == 0) {					// no matchers, return null
			return null;
		}
		else {											// one option, return that
			return options.get(0);
		}
	}
	
	
	
	public Card getRandomCard(ArrayList<Card> list) {		// based off code from geeks for geeks
		Random random = new Random();
		Card card = null;
		
		if (list.size() > 0) {
			card = list.get(random.nextInt(list.size()));
		} 
		
		return card;
		
	}
	
	
	
	@Override
	public void removeFromHand(Card card) {
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


	@Override
	public void updateHand(Card card) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setUnseenRooms(ArrayList<Card> unseenRooms) {				// test purposes
		this.unseenRooms = unseenRooms;
	}
	
	
	public void setUnseenPeople(ArrayList<Card> unseenPeople) {
		this.unseenPeople = unseenPeople;
	}
	
	public void setUnseenWeapons(ArrayList<Card> unseenWeapons) {
		this.unseenWeapons = unseenWeapons;
	}
	
	public void addUnseenPerson(Card person) {
		this.unseenPeople.add(person);
	}
	
	public void addUnseenWeapon(Card weapon) {
		this.unseenWeapons.add(weapon);
	}


}
