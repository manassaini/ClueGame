package clueGame;

import java.awt.Color;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	
	public abstract void updateHand(Card card);

}
