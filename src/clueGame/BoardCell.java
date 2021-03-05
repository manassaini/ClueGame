package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	// board cell
	
	private int row;
	private int col;
	private char initial;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private DoorDirection direction;
	
	public BoardCell() {
		super();
	}
	
	public void addAdj (BoardCell cell) {
		adjList.add(cell);
	}
	
	public boolean isRoomCenter() {
		return this.roomCenter;
	}
	
	public boolean isDoorway() {
		return this.isDoorway;
	}
	
	public DoorDirection getDoorDirection() {
		return this.direction;
	}
	
	public boolean isLabel() {
		return this.roomLabel;
	}
	
	public char getSecretPassage() {
		return this.secretPassage;
	}
	
	public void setInitial(char c) {
		this.initial = c;
		
	}
	
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	public void setDoorDirection(DoorDirection direction) {
		this.direction = direction;
	}
	
	public void setRoomLabel(boolean label) {
		this.roomLabel = label;
	}
	
	public void setRoomCenter(boolean center) {
		this.roomCenter = center;
	}
	
	public void setSecretPassage(char c) {
		this.secretPassage = c;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
}
