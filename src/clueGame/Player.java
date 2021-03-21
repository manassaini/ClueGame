package clueGame;

import java.awt.Color;

public abstract class Player {
	
	public Player(String name) {
		super();
		this.name = name;
//		this.color = color;
	}



	private String name;
	private Color color;
	protected int row;
	protected int column;
	
	
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public abstract void updateHand(Card card);
		
}
