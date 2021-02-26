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

		@Test
		public void testTargetsNormal() {
			//test targets for cell (2,2) with a dice roll of 2
			//initializing the starting cell
			TestBoardCell cell = board.getCell(2, 2);
			//passing in start cell and path length
			board.calcTargets(cell, 2);
			//creating a set to pass all targets in
			Set<TestBoardCell> targets = board.getTargets();
			//checking to see if the number of targets is equal to the size of the set
			Assert.assertEquals(6, targets.size());
			//checking if targets2 contains the valid target locations
			Assert.assertTrue(targets.contains(board.getCell(2, 0)));
			Assert.assertTrue(targets.contains(board.getCell(3, 1)));
			Assert.assertTrue(targets.contains(board.getCell(1, 1)));
			Assert.assertTrue(targets.contains(board.getCell(0, 2)));
			Assert.assertTrue(targets.contains(board.getCell(1, 3)));
			Assert.assertTrue(targets.contains(board.getCell(3, 3)));

			//test targets for cell (0,0) with a dice roll of 3
			//initializing the starting cell
			TestBoardCell cell2 = board.getCell(0, 0);
			//passing in start cell and path length
			board.calcTargets(cell2, 3);
			//creating a set to pass all targets in
			Set<TestBoardCell> targets2 = board.getTargets();
			//checking to see if the number of targets is equal to the size of the set
			Assert.assertEquals(6, targets.size());
			//checking if targets2 contains the valid target locations
			Assert.assertTrue(targets2.contains(board.getCell(3, 0)));
			Assert.assertTrue(targets2.contains(board.getCell(2, 1)));
			Assert.assertTrue(targets2.contains(board.getCell(0, 1)));
			Assert.assertTrue(targets2.contains(board.getCell(1, 2)));
			Assert.assertTrue(targets2.contains(board.getCell(0, 3)));
			Assert.assertTrue(targets2.contains(board.getCell(1, 0)));

			//test targets for cell (0,1) with a dice roll of 2
			//initializing the starting cell
			TestBoardCell cell3 = board.getCell(0, 1);
			//passing in start cell and path length
			board.calcTargets(cell3, 2);
			//creating a set to pass all targets in
			Set<TestBoardCell> targets3 = board.getTargets();
			//checking to see if the number of targets is equal to the size of the set
			Assert.assertEquals(4, targets3.size());
			//checking if targets3 contains the valid target locations
			Assert.assertTrue(targets3.contains(board.getCell(2, 1)));
			Assert.assertTrue(targets3.contains(board.getCell(1, 2)));
			Assert.assertTrue(targets3.contains(board.getCell(0, 3)));
			Assert.assertTrue(targets3.contains(board.getCell(1, 0)));

		}
		
		@Test
		public void testTargetsMixed() {
			//testing cell (0,3) and making both setOccupied and setIsRoom true
			TestBoardCell cell = board.getCell(0, 3);
			board.getCell(0, 2).setIsOccupied(true); //there is a person there, cannot walk on that cell
			board.getCell(1, 2).setRoom(true); //there is a room there, can walk in there if have enough dice rolls.
			board.calcTargets(cell, 3);
			Set<TestBoardCell> targets = board.getTargets();
			//there should be 3 total 
			Assert.assertEquals(3, targets.size());
			//these are all the targets the player can move with the following bools set to true
			Assert.assertTrue(targets.contains(board.getCell(1, 2)));
			Assert.assertTrue(targets.contains(board.getCell(2, 2)));
			Assert.assertTrue(targets.contains(board.getCell(3, 3)));
			
			//going to test with starting location at (1,0) and there will be a person at (0,1) so setOccupied true and setRoom false
			TestBoardCell cell1 = board.getCell(1, 0);
			board.getCell(0, 1).setIsOccupied(true); //there is a person at cell (0,1)
			//passing in cell and path length of 2
			board.calcTargets(cell1, 2);
			Set<TestBoardCell> targets1 = board.getTargets();
			//there should be 3 total 
			Assert.assertEquals(3, targets.size());
			//these are all the targets the player can move with setOccupied being set to true
			Assert.assertTrue(targets1.contains(board.getCell(1, 2)));
			Assert.assertTrue(targets1.contains(board.getCell(2, 1)));
			Assert.assertTrue(targets1.contains(board.getCell(3, 0)));
		}

	}
