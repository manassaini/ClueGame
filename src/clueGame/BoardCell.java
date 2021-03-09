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
	private boolean isSecretPassage = false;
	private Set<BoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway = false;
	private DoorDirection direction = DoorDirection.NONE;
	
	public BoardCell() {
		super();
		this.adjList = new HashSet<BoardCell>();
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
		this.isSecretPassage = true;
		this.secretPassage = c;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
	public boolean getIsSecretPassage() {
		return this.isSecretPassage;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public Set<BoardCell> getAdjList() {
		return this.adjList;
	}
	
	public char getInitial() {
		return this.initial;
	}
	
}
