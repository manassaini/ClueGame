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
		this.grid = new TestBoardCell[ROWS][COLS];
		this.targets = new HashSet<TestBoardCell>();
		this.visited = new HashSet<TestBoardCell>();
		
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				TestBoardCell cell = new TestBoardCell(i, j);
				this.grid[i][j] = cell;
			}
		}
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				calcAdjacencies(this.grid[i][j]);
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		calcAdjacencies(startCell);
		this.visited.add(startCell);
		
		for (TestBoardCell adjCell: startCell.getAdjList()) {
			
			if (!this.visited.contains(adjCell) && !adjCell.getIsOccupied()) {
				this.visited.add(adjCell);
				
				if (pathlength == 1) {
					this.targets.add(adjCell);
				} else {
					calcTargets(adjCell, pathlength - 1);
				}
				this.visited.remove(adjCell);
			}	
		}
	}
	
	public Set<TestBoardCell> getTargets(){
		return this.targets;
	}
	
	public TestBoardCell getCell(int row, int col) { // no cell at grid[row][col]
		return this.grid[row][col];
	}
	
	public void calcAdjacencies(TestBoardCell cell) {
		if (cell.getCol() > 0) {
			cell.addAdjacency(grid[cell.getRow()][cell.getCol() - 1]);
		}
		if (cell.getRow() > 0) {
			cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
		}
		if (cell.getCol() < (COLS-1)) {
			cell.addAdjacency(grid[cell.getRow()][cell.getCol() + 1]);	
		}
		if (cell.getRow() < (ROWS-1)) {
			cell.addAdjacency(grid[cell.getRow() + 1][cell.getCol()]);
		}	
	}
	
}
	
