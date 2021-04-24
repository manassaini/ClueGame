package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class Player {
	protected String name;
	protected Color color;
	protected int column;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seen;
	protected ArrayList<Card> unseen;
	protected ArrayList<Card> unseenWeapons;
	protected ArrayList<Card> unseenRooms;
	protected ArrayList<Card> unseenPeople;
	protected int row;
	protected int col;
	protected Card person;
	
	
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
		this.unseenPeople = theInstance.getPersonCards();

	}

	public void draw(Graphics g, int yScale, int xScale) {		// draw circle at location
		g.setColor(this.color);
		g.fillOval(xScale * this.col, yScale * this.row, xScale, yScale);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
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
	
	
	public abstract void addToHand(Card card);
	
	public abstract void removeFromHand(Card card);
	
	public abstract Card disproveSuggestion(Card person, Card weapon, Card room);
	
	public abstract void updateHand(Card card);
	
	public abstract void updateSeen(Card seenCard, CardPanel panel);

	protected abstract Solution createSuggestion(Card currentRoom);
	
	protected ArrayList<Card> getSeen() {
		if (this.seen.size() == 0) {
			return null;
		}
		return this.seen;
	}
	
	public ArrayList<Card> getHand() {
		return this.hand;
	}

	protected abstract Solution makeAccusation();
		
}
