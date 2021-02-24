package experiment;

import java.util.Collections;
import java.util.Set;

public class TestBoard {
	
	public TestBoard() {}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {}
	
	public Set<TestBoardCell> getTargets(){
		Set<TestBoardCell> getTargets = Collections.EMPTY_SET;
		return getTargets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell getCell = new TestBoardCell(row, col);
		return getCell;
	}
	
}
