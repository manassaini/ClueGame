package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	@Test
	public void testAdjacency() {
		//testing adjacency for cell (0,0)
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList(); //list that contains all adjacency cells for a particular cell
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
		
		//testing adjacency for cell (1,3)
		TestBoardCell cell2 = board.getCell(1, 3);
		Set<TestBoardCell> testList2 = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList2.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList2.contains(board.getCell(2, 3)));
		Assert.assertEquals(3, testList2.size());
		
		//testing adjacency for cell (3,3)
		TestBoardCell cell3 = board.getCell(3, 3);
		Set<TestBoardCell> testList3 = cell.getAdjList();
		Assert.assertTrue(testList3.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList3.size());
		
		//testing adjacency for cell (3,0)
		TestBoardCell cell4 = board.getCell(3, 0);
		Set<TestBoardCell> testList4 = cell.getAdjList(); 
		Assert.assertTrue(testList4.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList4.contains(board.getCell(3, 1)));
		Assert.assertEquals(2, testList4.size());
		
		//testing adjacency for cell (2,2)
		TestBoardCell cell5 = board.getCell(2, 2);
		Set<TestBoardCell> testList5 = cell.getAdjList(); 
		Assert.assertTrue(testList5.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList5.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList5.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList5.contains(board.getCell(1, 2)));
		Assert.assertEquals(4, testList5.size());
		//add comment
	}
	

	

}
