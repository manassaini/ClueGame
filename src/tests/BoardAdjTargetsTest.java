package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetsTest {
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
			// Initialize will load config files 
			try {
				board.initialize();
			} catch (BadConfigFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Ensure that player does not move around within room
		// These cells are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testAdjacenciesRooms()
		{
			// room with multiple doors
			Set<BoardCell> testList = board.getAdjList(13, 2);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(15, 4)));
			assertTrue(testList.contains(board.getCell(9, 4)));
			
			// test the armory
			testList = board.getAdjList(13, 19);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(16, 16)));
			
			// zoo w/ secret passage
			testList = board.getAdjList(3, 18);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(6, 20)));
			assertTrue(testList.contains(board.getCell(4, 16)));
			assertTrue(testList.contains(board.getCell(8, 17)));
		}

		
		// Ensure door locations include their rooms and also additional walkways
		// These cells are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testAdjacencyDoor()
		{
			
			Set<BoardCell> testList = board.getAdjList(8, 8);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(2, 9)));
			assertTrue(testList.contains(board.getCell(9, 8)));
			assertTrue(testList.contains(board.getCell(8, 7)));
			assertTrue(testList.contains(board.getCell(8, 9)));

			testList = board.getAdjList(21, 3);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(22, 1)));
			assertTrue(testList.contains(board.getCell(20, 3)));
			assertTrue(testList.contains(board.getCell(21, 4)));
			
			testList = board.getAdjList(2, 6);					// colored pink for door direction test
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(1, 6)));
			assertTrue(testList.contains(board.getCell(3, 6)));
			assertTrue(testList.contains(board.getCell(2, 9)));
			assertTrue(testList.contains(board.getCell(2, 5)));
		}
		
		// Test a variety of walkway scenarios
		// These tests are NEON GREEN on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on bottom edge of board, just one walkway piece
			Set<BoardCell> testList = board.getAdjList(24, 12);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(23, 12)));
			
			// Test near a door but not adjacent
			testList = board.getAdjList(5, 4);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(18, 3)));
			assertTrue(testList.contains(board.getCell(17, 4)));
			assertTrue(testList.contains(board.getCell(18, 5)));

			// Test adjacent to walkways
			testList = board.getAdjList(18, 5);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(17, 5)));
			assertTrue(testList.contains(board.getCell(19, 5)));
			assertTrue(testList.contains(board.getCell(18, 4)));
			assertTrue(testList.contains(board.getCell(18, 6)));

			// Test next to closet
			testList = board.getAdjList(18,19);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(17, 19)));
			assertTrue(testList.contains(board.getCell(18, 18)));
			assertTrue(testList.contains(board.getCell(19, 19)));
		
		}
		
		
		// Tests out of room center, 1, 3 and 4
		// These are RED on the planning spreadsheet
		@Test
		public void testTargetsInBalcony() {
			// test a roll of 1
			board.calcTargets(board.getCell(3, 1), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(6, 0)));			// secret passage
			assertTrue(targets.contains(board.getCell(4, 3)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(3, 1), 3);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(6, 3)));
			assertTrue(targets.contains(board.getCell(5, 4)));	
			assertTrue(targets.contains(board.getCell(4, 5)));
			assertTrue(targets.contains(board.getCell(23, 16)));			// secret passage
			
			// test a roll of 4
			board.calcTargets(board.getCell(3, 1), 4);
			targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCell(7, 3)));
			assertTrue(targets.contains(board.getCell(4, 6)));	
			assertTrue(targets.contains(board.getCell(6, 4)));
			assertTrue(targets.contains(board.getCell(6, 2)));	
			assertTrue(targets.contains(board.getCell(3, 5)));
			assertTrue(targets.contains(board.getCell(22, 13)));			// secret passage
			assertTrue(targets.contains(board.getCell(20, 15)));			// secret passage
			
			
		}
		
		@Test
		public void testTargetsInArmory() {
			// test a roll of 1
			board.calcTargets(board.getCell(13, 19), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCell(16, 16)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(13, 19), 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(14, 16)));
			assertTrue(targets.contains(board.getCell(18, 16)));	
			assertTrue(targets.contains(board.getCell(17, 17)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(13, 19), 4);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(19, 16)));
			assertTrue(targets.contains(board.getCell(13, 16)));	
			assertTrue(targets.contains(board.getCell(17, 18)));
			assertTrue(targets.contains(board.getCell(14, 15)));
			assertTrue(targets.contains(board.getCell(14, 17)));
			assertTrue(targets.contains(board.getCell(18, 17)));
			assertTrue(targets.contains(board.getCell(18, 15)));
			
		}

		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsAtDoor() {
			// test a roll of 1, at door
			board.calcTargets(board.getCell(16, 16), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(13, 19)));
			assertTrue(targets.contains(board.getCell(15, 16)));	
			assertTrue(targets.contains(board.getCell(17, 16)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(16, 16), 3);
			targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCell(19, 16)));
			assertTrue(targets.contains(board.getCell(13, 16)));	
			assertTrue(targets.contains(board.getCell(17, 18)));
			assertTrue(targets.contains(board.getCell(14, 15)));
			assertTrue(targets.contains(board.getCell(14, 17)));
			assertTrue(targets.contains(board.getCell(18, 17)));
			assertTrue(targets.contains(board.getCell(18, 15)));

			
			// test a roll of 4
			board.calcTargets(board.getCell(16, 16), 4);
			targets= board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCell(12, 16)));
			assertTrue(targets.contains(board.getCell(14, 14)));
			assertTrue(targets.contains(board.getCell(13, 15)));	
			assertTrue(targets.contains(board.getCell(13, 17)));
			assertTrue(targets.contains(board.getCell(18, 14)));
			assertTrue(targets.contains(board.getCell(19, 15)));	
			assertTrue(targets.contains(board.getCell(19, 17)));	
			assertTrue(targets.contains(board.getCell(18, 18)));	
			assertTrue(targets.contains(board.getCell(17, 19)));	
		}

		@Test
		public void testTargetsInWalkway1() {
			// test a roll of 1
			board.calcTargets(board.getCell(18, 11), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(18, 10)));
			assertTrue(targets.contains(board.getCell(19, 11)));
			assertTrue(targets.contains(board.getCell(18, 12)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(18, 11), 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(16, 10)));
			assertTrue(targets.contains(board.getCell(18, 8)));
			assertTrue(targets.contains(board.getCell(19, 9)));	
			assertTrue(targets.contains(board.getCell(21, 11)));
			assertTrue(targets.contains(board.getCell(20, 12)));
			assertTrue(targets.contains(board.getCell(19, 13)));
			assertTrue(targets.contains(board.getCell(18, 14)));
			assertTrue(targets.contains(board.getCell(13, 14)));			// door/center/door
			
			// test a roll of 4
			board.calcTargets(board.getCell(18, 11), 4);
			targets= board.getTargets();
			assertEquals(3, targets.size());	
			assertTrue(targets.contains(board.getCell(13, 15)));		// door/center/door
			assertTrue(targets.contains(board.getCell(14, 14)));		// door/center/door
		}

		@Test
		public void testTargetsInWalkway2() {
			// test a roll of 1
			board.calcTargets(board.getCell(8, 12), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(8, 11)));
			assertTrue(targets.contains(board.getCell(9, 12)));	
			assertTrue(targets.contains(board.getCell(8,13)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(8, 12), 3);
			targets= board.getTargets();
			assertEquals(8, targets.size());
			assertTrue(targets.contains(board.getCell(7, 10)));
			assertTrue(targets.contains(board.getCell(8, 9)));
			assertTrue(targets.contains(board.getCell(9, 10)));	
			assertTrue(targets.contains(board.getCell(9, 12)));	
			assertTrue(targets.contains(board.getCell(6, 13)));
			assertTrue(targets.contains(board.getCell(7, 14)));	
			assertTrue(targets.contains(board.getCell(8, 15)));	
			assertTrue(targets.contains(board.getCell(9, 14)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(8, 12), 4);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(6, 10)));
			assertTrue(targets.contains(board.getCell(7, 9)));
			assertTrue(targets.contains(board.getCell(8, 8)));	
			assertTrue(targets.contains(board.getCell(9, 9)));	
			assertTrue(targets.contains(board.getCell(10, 10)));
			assertTrue(targets.contains(board.getCell(9, 11)));	
			assertTrue(targets.contains(board.getCell(9, 13)));	
			assertTrue(targets.contains(board.getCell(5, 13)));	
			assertTrue(targets.contains(board.getCell(6, 14)));
			assertTrue(targets.contains(board.getCell(8, 14)));	
			assertTrue(targets.contains(board.getCell(8, 16)));	
			assertTrue(targets.contains(board.getCell(9, 15)));	
		}

		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 4 blocked 2 down
			board.getCell(11, 10).setOccupied(true);
			board.calcTargets(board.getCell(10, 10), 4);
			board.getCell(9, 10).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(13, targets.size());
			assertTrue(targets.contains(board.getCell(6, 10)));
			assertTrue(targets.contains(board.getCell(7, 9)));
			assertTrue(targets.contains(board.getCell(8, 10)));	
			assertFalse( targets.contains( board.getCell(8, 12))) ;
			assertFalse( targets.contains( board.getCell(9, 11))) ;
			assertTrue(targets.contains(board.getCell(9, 7)));
			assertTrue(targets.contains(board.getCell(9, 13)));
		
			// we want to make sure we can get into a room, even if flagged as occupied
			board.getCell(3, 18).setOccupied(true);
			board.getCell(8, 18).setOccupied(true);
			board.calcTargets(board.getCell(8, 17), 1);
			board.getCell(3, 18).setOccupied(false);
			board.getCell(8, 18).setOccupied(false);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(9, 17)));	
			assertTrue(targets.contains(board.getCell(8, 16)));	
			assertTrue(targets.contains(board.getCell(3, 18)));	
			
			// check leaving a room with a blocked doorway
			board.getCell(13, 14).setOccupied(true);
			board.calcTargets(board.getCell(13, 12), 3);
			board.getCell(13, 14).setOccupied(false);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(18, 10)));
			assertTrue(targets.contains(board.getCell(19, 11)));	
			assertTrue(targets.contains(board.getCell(20, 12)));
			assertTrue(targets.contains(board.getCell(19, 13)));
			assertTrue(targets.contains(board.getCell(18, 15)));	

		}
}
