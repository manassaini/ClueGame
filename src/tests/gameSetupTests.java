package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import experiment.TestBoard;

class gameSetupTests {

	Board board;

	@BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		try {
			board.initialize();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	
	
	@Test
	void testPeople() {
		assertTrue(board.getPeople().contains("Sophie"));
		assertTrue(board.getPeople().contains("Melissa"));
		assertTrue(board.getPeople().contains("Griffin"));
		assertTrue(board.getPeople().contains("Vik"));
		assertTrue(board.getPeople().contains("Eddie"));
		assertTrue(board.getPeople().contains("Mario"));
		
	}
	
	@Test
	void testWeapons() {
		assertTrue(board.getPeople().contains("Knife"));
		assertTrue(board.getPeople().contains("Rope"));
		assertTrue(board.getPeople().contains("Glock"));
		assertTrue(board.getPeople().contains("Hammer"));
		assertTrue(board.getPeople().contains("Sickle"));
		assertTrue(board.getPeople().contains("Taser"));
		
	}
	
	
	
	
}

