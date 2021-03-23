package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	protected ArrayList<Card> hand;
	
	
	public Player(String name) {
		super();
		this.name = name;
		ArrayList<Card> hand = new ArrayList<Card>();

	}

	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public abstract boolean isComputer();
	
	public abstract ArrayList<Card> getHand();
	
	public abstract void addToHand(Card card);
	
	public abstract void removeFromHand(Card card);
		
}
