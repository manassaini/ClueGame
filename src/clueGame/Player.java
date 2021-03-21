package clueGame;

import java.awt.Color;

public abstract class Player {
	
	public Player(String name, String color) {
		super();
		this.name = name;
//		this.color = color;
	}



	private String name;
	private Color color;
	protected int row;
	protected int column;
	
	
	
	public abstract void updateHand(Card card);

}
