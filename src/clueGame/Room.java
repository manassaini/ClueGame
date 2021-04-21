package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
	// generic room
	
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private BoardCell secretPassage;
	private boolean hasSecret;
	private Set<BoardCell> doors;
	private Card card;
	
	public Room(String name) {
		super();
		this.name = name;
		this.hasSecret = false;
		this.doors = new HashSet<BoardCell>();
	}
	
	public String getName() {
		return this.name;
	}

	public BoardCell getLabelCell() {
		return this.labelCell;
	}
	
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
	
	public void setCenter(BoardCell center) {
		this.centerCell = center;
	}
	
	public void setLabel(BoardCell label) {
		this.labelCell = label;
	}
	
	public void setSecretPassage(BoardCell sp) {
		this.hasSecret = true;
		this.secretPassage = sp;
	}
	
	public BoardCell getSecretPassage() {
		return this.secretPassage;
	}
	
	public void addDoor(BoardCell door) {
		this.doors.add(door);
	}
	
	public Set<BoardCell> getDoors() {
		return this.doors;
	}
	
	public boolean hasSecret() {
		return this.hasSecret;
	}
	
	public void setCard(Card c) {
		this.card = card;
	}
	
	public Card getCard() {
		return this.card;
	}
	
}
