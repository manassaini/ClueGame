package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	protected int column;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seen;
	protected ArrayList<Card> unseen;
	protected ArrayList<Card> unseenWeapons;
	protected ArrayList<Card> unseenRooms;
	protected ArrayList<Card> unseenPeople;
	protected int row;
	protected int col;
	
	
	public Player(String name) {
		super();
		this.name = name;
		this.hand = new ArrayList<Card>();
		this.seen = new ArrayList<Card>();
		this.unseen = new ArrayList<Card>();
		
		this.unseenWeapons = new ArrayList<Card>();
		this.unseenRooms = new ArrayList<Card>();
		this.unseenPeople = new ArrayList<Card>();
		
		Board theInstance = Board.getInstance();
		this.unseenWeapons = theInstance.getWeaponCards();
		this.unseenRooms = theInstance.getRoomCards();
		this.unseenPeople = theInstance.getPeronCards();

	}

	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setLoc (int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getCol() {
		return this.col;
	}

	public abstract boolean isComputer();
	
	public abstract ArrayList<Card> getHand();
	
	public abstract void addToHand(Card card);
	
	public abstract void removeFromHand(Card card);
	
	public abstract Card disproveSuggestion(Card person, Card weapon, Card room);
	
	public abstract void updateHand(Card card);
	
	public abstract void updateSeen(Card seenCard);
		
}
