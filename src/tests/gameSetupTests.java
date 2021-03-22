package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Player;

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
	void testPeople() {								// testing that people scanned in correctly AND deck is complete
		assertTrue(board.getPeople().contains("Sophie"));
		assertTrue(board.getPeople().contains("Melissa"));
		assertTrue(board.getPeople().contains("Griffin"));
		assertTrue(board.getPeople().contains("Vik"));
		assertTrue(board.getPeople().contains("Eddie"));
		assertTrue(board.getPeople().contains("Mario"));
		assertTrue(board.getPeople().contains("Luigi"));
		
		assertEquals(board.getPeople().size(), 7);
		
	}
	
	@Test
	void testWeapons() {							// testing that weapons scanned in correctly AND deck is complete
		assertTrue(board.getWeapons().contains("Knife"));
		assertTrue(board.getWeapons().contains("Rope"));
		assertTrue(board.getWeapons().contains("Glock"));
		assertTrue(board.getWeapons().contains("Hammer"));
		assertTrue(board.getWeapons().contains("Sickle"));
		assertTrue(board.getWeapons().contains("Taser"));
		assertTrue(board.getWeapons().contains("Saw"));
		
		assertEquals(board.getWeapons().size(), 7);
		
	}
	
	void testRooms() {								// testing room deck, rooms scanned in and size of deck
		assertTrue(board.getRoomDeck().contains("Balcony"));
		assertTrue(board.getRoomDeck().contains("Washroom"));
		assertTrue(board.getRoomDeck().contains("Trophy Room"));
		assertTrue(board.getRoomDeck().contains("Museum"));
		assertTrue(board.getRoomDeck().contains("Armory"));
		assertTrue(board.getRoomDeck().contains("Dungeon"));
		assertTrue(board.getRoomDeck().contains("Hall"));
		assertTrue(board.getRoomDeck().contains("Zoo"));
		assertTrue(board.getRoomDeck().contains("Secret Base"));
		assertTrue(board.getRoomDeck().contains("Unused"));
		assertTrue(board.getRoomDeck().contains("Walkway"));

		assertEquals(board.getRoomDeck().size(), 11);
	}
	
	
	@Test
	void testPlayers() {							// testing 5 computers one human
		board.dealCards();
		
		int compCount = 0;
		int humanCount = 0;
		ArrayList<Player> thePlayers = new ArrayList<Player>();
		thePlayers = board.getPlayers();
		
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
	
	
	@Test
	void testDealCards() {
		
	}
	
	
}

