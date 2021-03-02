package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
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
		this.isDoorway = false;
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
	
}
