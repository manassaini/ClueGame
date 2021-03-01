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
		
		//TestBoardCell cell = new TestBoardCell(0,0);
		
		for (int i = 0; i < ROWS; i++) {		// either called 3 times or running incorrectly
			for (int j = 0; j < COLS; j++) {
				TestBoardCell cell = new TestBoardCell(i, j);
				grid[i][j] = cell;
				System.out.println(grid[i][j]);
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		for (TestBoardCell adjCell: startCell.getAdjList()) {
			if (!visited.contains(adjCell)) {
				visited.add(adjCell);
				if (pathlength == 1) {
					targets.add(adjCell);
				}
				calcTargets(adjCell, pathlength - 1);
			}
				visited.remove(adjCell);
		}
	}
	
	public Set<TestBoardCell> getTargets(){
		return this.targets;
	}
	
	public TestBoardCell getCell(int row, int col) { // no cell at grid[row][col]
		System.out.println(grid[row][col]);
		return grid[row][col];
	}
	
	public void calcAdjancies(TestBoardCell cell) {
		if (cell.getCol() > 0 && !(grid[cell.getRow()][cell.getCol() - 1].getIsOccupied())) {
			cell.addAdjacency(grid[cell.getRow()][cell.getCol() - 1]);
		}
		if (cell.getRow() > 0 && !grid[cell.getRow() - 1][cell.getCol()].getIsOccupied()) {
			cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
		}
		if (cell.getCol() < COLS && !grid[cell.getRow()][cell.getCol() + 1].getIsOccupied()) {
			cell.addAdjacency(grid[cell.getRow()][cell.getCol() + 1]);
		}
		if (cell.getRow() < ROWS && !grid[cell.getRow() + 1][cell.getCol()].getIsOccupied()) {
			cell.addAdjacency(grid[cell.getRow() + 1][cell.getCol()]);
		}
		
		
		
	}
	
}
	
