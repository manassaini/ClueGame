package experiment;

import java.util.Collections;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjList;
	private boolean isRoom;
	boolean isOccupied;
	
	
	public TestBoardCell(int row, int col) {
		super();
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		Set<TestBoardCell> adjList = Collections.EMPTY_SET;
		return adjList;
	}
	
	public void setRoom(boolean isRoom) {}
		
	public void setIsOccupied(boolean isOccupied) {}
	
	public boolean getIsOccupied() {
		return false;
	}
	
	public boolean getIsRoom() {
		return false;
	}
	
	
}
