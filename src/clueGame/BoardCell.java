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
	boolean isOccupied;
	
	private BoardCell() {
		super();
	}
	
	public void addAdj (BoardCell cell) {
		adjList.add(cell);
	}
	
}
