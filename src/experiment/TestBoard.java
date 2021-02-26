package experiment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int ROWS = 4;
	final static int COLS = 4;
	
	public TestBoard() {
		TestBoardCell[][] grid = new TestBoardCell[ROWS][COLS];
		Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
		Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {}
	
	public Set<TestBoardCell> getTargets(){
		return this.targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
}
	
