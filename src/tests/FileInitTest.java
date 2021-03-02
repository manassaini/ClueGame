package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTest {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 24;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Balcony", board.getRoom('B').getName() );
		assertEquals("Washroom", board.getRoom('R').getName() );
		assertEquals("Trophy Room", board.getRoom('T').getName() );
		assertEquals("Museum", board.getRoom('M').getName() );
		assertEquals("Armory", board.getRoom('A').getName() );
		assertEquals("Dungeon", board.getRoom('D').getName() );
		assertEquals("Hall", board.getRoom('H').getName() );
		assertEquals("Zoo", board.getRoom('Z').getName() );
		assertEquals("Secret Base", board.getRoom('S').getName() );
		assertEquals("Unused", board.getRoom('X').getName() );
		assertEquals("Walkway", board.getRoom('W').getName() );
	}
	

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(4, 3);
		assertTrue(cell.isDoorway());					
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(2, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(5, 10);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(22, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		// Test that walkways are not doors
		cell = board.getCell(12, 14);
		assertFalse(cell.isDoorway());
	}
	

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())				
					numDoors++;
			}
		Assert.assertEquals(17, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board.getCell(0, 0);
		Room room = board.getRoom('B') ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Balcony" ) ;
		assertFalse( cell.isLabel() );				
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// this is a label cell to test
		cell = board.getCell(22, 8);
		room = board.getRoom('A') ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Washroom" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );
		
		// this is a room center cell to test
		cell = board.getCell(23, 16);
		room = board.getRoom('T') ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Trophy Room" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// this is a secret passage test
		cell = board.getCell(6, 20);
		room = board.getRoom('Z') ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Zoo" ) ;
		assertTrue( cell.getSecretPassage() == 'Z' );
		
		// test a walkway
		cell = board.getCell(7, 0);
		room = board.getRoom('W') ;
		// Note for our purposes, walkways and closets are rooms
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test a closet
		cell = board.getCell(0, 5);
		room = board.getRoom('X') ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
	}

}
