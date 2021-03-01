package experiment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjList;
	private boolean isRoom;
	boolean isOccupied;
	
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return this.adjList;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
		
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getIsOccupied() {
		return this.isOccupied;
	}
	
	public boolean getIsRoom() {
		return this.isRoom;
	}
	
	public String toString() {
		return "exists";
	}
	
}
