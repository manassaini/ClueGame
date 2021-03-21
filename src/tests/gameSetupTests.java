package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Player;
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
		assertTrue(board.getPeople().contains("Luigi"));
		
	}
	
	@Test
	void testWeapons() {
		assertTrue(board.getWeapons().contains("Knife"));
		assertTrue(board.getWeapons().contains("Rope"));
		assertTrue(board.getWeapons().contains("Glock"));
		assertTrue(board.getWeapons().contains("Hammer"));
		assertTrue(board.getWeapons().contains("Sickle"));
		assertTrue(board.getWeapons().contains("Taser"));
		assertTrue(board.getWeapons().contains("Saw"));
		
	}
	
	
	@Test
	void testPlayers() {
		int compCount = 0;
		int humanCount = 0;
		ArrayList<Player> thePlayers = board.getPlayers();
		
		for (Player p: thePlayers) {
			if (p.isComputer()) {
				compCount ++;
			} else {
				humanCount++;
			}
		}
		
		assertEquals(compCount, 5);
		assertEquals(humanCount, 1);
	}
	
	
	
	
	
}

