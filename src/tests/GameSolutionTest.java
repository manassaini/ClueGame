package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Solution;
import clueGame.Player;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

class GameSolutionTest {

	Card melissaCard = new Card("Melissa");
	Card sophieCard = new Card("Sophie");
	Card griffinCard = new Card("Griffin");
	Card vikCard = new Card("Vik");
	Card eddieCard = new Card("Eddie");
	Card marioCard = new Card("Mario");

	Card knifeCard = new Card("Knife");
	Card ropeCard = new Card("Rope");
	Card glockCard = new Card("Glock");
	Card hammerCard = new Card("Hammer");
	Card sickleCard = new Card("Sickle");
	Card taserCard = new Card("Taser");

	Card dungeonCard = new Card("Dungeon");
	Card hallCard = new Card ("Hall");
	Card zooCard = new Card ("Zoo");
	Card sbCard = new Card ("Secret Base");
	Card balconyCard = new Card("Balcony");
	Card washroomCard = new Card("Washroom");
	Card trophyRoomCard = new Card("Trophy Room");
	Card museumCard = new Card("Museum");
	Card armoryCard = new Card("Armory");
	
	Board board;
	
	

	@BeforeEach
	void setUp() {
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

		melissaCard.setCardType(CardType.PERSON);
		sophieCard.setCardType(CardType.PERSON);
		griffinCard.setCardType(CardType.PERSON);
		vikCard.setCardType(CardType.PERSON);
		eddieCard.setCardType(CardType.PERSON);
		marioCard.setCardType(CardType.PERSON);

		knifeCard.setCardType(CardType.WEAPON);
		ropeCard.setCardType(CardType.WEAPON);
		glockCard.setCardType(CardType.WEAPON);
		hammerCard.setCardType(CardType.WEAPON);
		sickleCard.setCardType(CardType.WEAPON);
		taserCard.setCardType(CardType.WEAPON);


		dungeonCard.setCardType(CardType.ROOM);
		hallCard.setCardType(CardType.ROOM);		
		zooCard.setCardType(CardType.ROOM);		
		sbCard.setCardType(CardType.ROOM);		
		balconyCard.setCardType(CardType.ROOM);		
		washroomCard.setCardType(CardType.ROOM);		
		trophyRoomCard.setCardType(CardType.ROOM);		
		museumCard.setCardType(CardType.ROOM);		
		armoryCard.setCardType(CardType.ROOM);

		
	}

	
	
	
	
	
	@Test
	void checkAccusationTest() {
		Solution solution = new Solution();																// one solution to work from
		solution.setPerson(eddieCard);
		solution.setWeapon(glockCard);
		solution.setRoom(trophyRoomCard);
		
		
		assertTrue(board.checkAccusation(eddieCard, glockCard, trophyRoomCard, solution));				// correct
		assertFalse(board.checkAccusation(eddieCard, glockCard, hallCard, solution));					// wrong room
		assertFalse(board.checkAccusation(eddieCard, knifeCard, trophyRoomCard, solution));				// wrong weapon
		assertFalse(board.checkAccusation(sophieCard, glockCard, trophyRoomCard, solution));			// wrong person
	}
	
	
	
	
	
	
	@Test
	void disproveSuggestionTest() {
		Player player = new ComputerPlayer("Test Player");
		
		player.addToHand(armoryCard);											// create a hand of matchers
		player.addToHand(glockCard);
		player.addToHand(melissaCard);
		
		Card disprove = player.disproveSuggestion(melissaCard, knifeCard, hallCard);	// matching person
		assertTrue(disprove.equals(melissaCard));
		
		////
		disprove = player.disproveSuggestion(eddieCard, glockCard, hallCard);			// matching weapon
		assertTrue(disprove.equals(glockCard));
		
		////
		disprove = player.disproveSuggestion(eddieCard, knifeCard, armoryCard);			// matching room
		assertTrue(disprove.equals(armoryCard));
		
		////
		disprove = player.disproveSuggestion(eddieCard, knifeCard, zooCard);			// none matching
		assertTrue(disprove == null);
		
		
		////
		int melissaCount = 0;
		int armoryCount = 0;
		
		for (int i = 0; i < 50; i++) {													// matching person and room, random choice
			disprove = player.disproveSuggestion(melissaCard, knifeCard, armoryCard);
			if (disprove.equals(melissaCard)) {
				melissaCount++;
			}
			else {
				armoryCount++;
			}
		}
		
			// I picked a very safe number due to it being random, always a possibility that one could fail because it is random
		assertTrue(melissaCount >= 18);
		assertTrue(armoryCount >= 18);
		
	}
	
	
	
	
	
	@Test
	void handleSuggestionTest() {
		Player player0 = new HumanPlayer("Player 0");
		player0.addToHand(armoryCard);
		player0.addToHand(glockCard);
		
		Player player1 = new ComputerPlayer("Player 1");
		player1.addToHand(dungeonCard);
		player1.addToHand(taserCard);
		
		Player player2 = new ComputerPlayer("Player 2");
		player2.addToHand(sickleCard);
		player2.addToHand(griffinCard);
		
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(player0);
		playerList.add(player1);
		playerList.add(player2);
		
		board.setPlayers(playerList);
		
		Card disprove = board.handleSuggestion(sophieCard, knifeCard, hallCard, player0, playerList);			// no one can disprove, null
		assertEquals(disprove, null);
		
		////
		disprove = board.handleSuggestion(marioCard, glockCard, zooCard, player0, playerList);					// accusing player can disprove, null
		assertEquals(disprove, null);
		
		////
		disprove = board.handleSuggestion(griffinCard, taserCard, trophyRoomCard, player0, playerList);			// player first in cycle disproves
		assertTrue(disprove.equals(taserCard));
	}
	
	
	
	
	
	
	@Test
	void computerCreateSuggestion() {
		ComputerPlayer computer = new ComputerPlayer("Test computer player");	// computer player with unseen people and weapon decks
		
		ArrayList<Card> unseenPeople = new ArrayList<Card>();
		unseenPeople.add(eddieCard);
		unseenPeople.add(marioCard);
		computer.setUnseenPeople(unseenPeople);
		
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		unseenWeapons.add(hammerCard);
		unseenWeapons.add(ropeCard);
		computer.setUnseenWeapons(unseenWeapons);
		
		
		
		Solution solution = computer.createSuggestion(armoryCard);				// solution includes current room
		assertTrue(solution.getRoom().equals(armoryCard));
		
		///////
		computer.addToHand(hammerCard); 										// only one unseen weapon, the rope
		computer.addToHand(eddieCard); 											// only one unseen person, mario
		
		solution = computer.createSuggestion(dungeonCard);
		
		assertTrue(solution.getWeapon().equals(ropeCard));
		assertTrue(solution.getPerson().equals(marioCard));
		
		computer.addToHand(ropeCard);											// reset to empty unseen
		computer.addToHand(knifeCard);
		
		
		/////
		computer.addUnseenWeapon(glockCard);									// 3 unseen weapons, one chosen at random
		computer.addUnseenWeapon(taserCard);
		computer.addUnseenWeapon(knifeCard);
		
		int glockCount = 0;
		int taserCount = 0;
		int knifeCount = 0;
		
		for (int i = 0; i <100; i++) {
			solution = computer.createSuggestion(dungeonCard);
			Card weapon = solution.getWeapon();
			if (weapon.equals(glockCard)) {
				glockCount++;
			}
			else if (weapon.equals(taserCard)) {
				taserCount++;
			}
			else if (weapon.equals(knifeCard)) {
				knifeCount++;
			}
		}
		
		
			// I picked a very safe number due to it being random, however always a possibility that one could fail because it is random
		assertTrue(glockCount >= 15);								
		assertTrue(taserCount >= 15);
		assertTrue(knifeCount >= 15);
		
		
		///////
		computer.addUnseenPerson(sophieCard);								// 3 unseen people, one chosen at random
		computer.addUnseenPerson(griffinCard);
		computer.addUnseenPerson(vikCard);
		
		int sophieCount = 0;
		int griffinCount = 0;
		int vikCount = 0;
		
		for (int i = 0; i <100; i++) {
			solution = computer.createSuggestion(dungeonCard);
			Card person = solution.getPerson();
			if (person.equals(sophieCard)) {
				sophieCount++;
			}
			else if (person.equals(griffinCard)) {
				griffinCount++;
			}
			else if (person.equals(vikCard)) {
				vikCount++;
			}
		}
		
			// I picked a safe number due to it being random, always a possibility that one could fail because random
		assertTrue(sophieCount >= 15);
		assertTrue(griffinCount >= 15);
		assertTrue(vikCount >= 15);
	}
	
	
	
	
	
	
	@Test
	void computerSelectTargetTest() {
		ComputerPlayer computer = new ComputerPlayer("Test computer player");
		ArrayList<Card> testUnseen = new ArrayList<Card>();
		
		testUnseen.add(dungeonCard);
		testUnseen.add(armoryCard);
		testUnseen.add(museumCard);
		
		computer.setUnseenRooms(testUnseen);
		BoardCell[][] grid = board.getGrid();
		
		
		//////
		BoardCell testCell = grid[15][4];				// door, hasn't seen adjacent room (museum)
		board.calcTargets(testCell, 1);
		
		Set<BoardCell> testTargets = testCell.getTargets();
		
		BoardCell nextCell = computer.selectTarget(testTargets);
		assertEquals(nextCell, grid[13][2]);
		
		
		//////
		testCell = grid[9][16];							// walkway, surrounded by 4 walkways
		board.calcTargets(testCell, 1);
		
		assertTrue(randomBoardCellHelper(testCell, grid[8][16], grid[10][16], grid[9][15], grid[9][17], computer));
		
		
		//////
		testCell = grid[4][13];							// room that has been seen (dungeon)
		computer.addToHand(dungeonCard);
		board.calcTargets(testCell, 1);
		
		assertTrue(randomBoardCellHelper(testCell, grid[2][9], grid[3][13], grid[4][14], grid[5][13], computer));
	}

	
	
	
	// counts times of random selection for select target based on start cell, used above
	public boolean randomBoardCellHelper(BoardCell startCell, BoardCell cell1, BoardCell cell2, BoardCell cell3, BoardCell cell4, ComputerPlayer computer) {
		int cell1Count = 0;
		int cell2Count = 0;
		int cell3Count = 0;
		int cell4Count = 0;
		
		for (int i = 0; i <100; i++) {
			Set<BoardCell> testTargets = startCell.getTargets();
			BoardCell nextCell = computer.selectTarget(testTargets);
			if (nextCell.equals(cell1)) {
				cell1Count++;
			}
			else if (nextCell.equals(cell2)) {
				cell2Count++;
			}
			else if (nextCell.equals(cell3)) {
				cell3Count++;
			}
			else if (nextCell.equals(cell4)) {
				cell4Count++;
			}
		}
		
		if (cell1Count >= 15 && cell2Count >=15 && cell3Count >= 15 && cell4Count >= 15) {
			return true;
		}
		return false;
	}
	
	
 
}
