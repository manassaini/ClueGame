package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Player;
import clueGame.Card;
import clueGame.CardType;

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
		
		assertEquals(board.getPeople().size(), 6);
		
	}
	
	@Test
	void testWeapons() {							// testing that weapons scanned in correctly AND deck is complete
		assertTrue(board.getWeapons().contains("Knife"));
		assertTrue(board.getWeapons().contains("Rope"));
		assertTrue(board.getWeapons().contains("Glock"));
		assertTrue(board.getWeapons().contains("Hammer"));
		assertTrue(board.getWeapons().contains("Sickle"));
		assertTrue(board.getWeapons().contains("Taser"));
		
		assertEquals(board.getWeapons().size(), 6);
		
	}
	
	
	@Test
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

		assertEquals(board.getRoomDeck().size(), 9);
	}
	
	
	@Test
	void testPlayers() {							// testing 5 computers one human
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
	void testDealCards() {						// test each player has 3 cards
		ArrayList<Player> thePlayers = new ArrayList<Player>();
		thePlayers = board.getPlayers();
		
		for (Player p: thePlayers) {
			assertEquals(p.getHand().size(), 3);
		}
	}
	
	
	
	@Test
	void testCompleteDeck() {					// right number of each card
		int weaponCount = 0;
		int roomCount = 0;
		int personCount = 0;
		
		ArrayList<Card> cardDeck = board.getCompleteDeck();
		
		for (Card c: cardDeck) {
			if (c.getCardType() == CardType.WEAPON) {
				weaponCount++;
			}
			else if (c.getCardType() == CardType.ROOM) {
				roomCount++;
			}
			else {
				personCount++;
			}
		}
		
		assertEquals(weaponCount, 6);
		assertEquals(roomCount, 9);
		assertEquals(personCount, 6);
	}
	
	
	
	
	
	
}

