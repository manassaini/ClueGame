package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
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
	
	Solution solution = new Solution();

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

		solution.setPerson(eddieCard);
		solution.setWeapon(glockCard);
		solution.setRoom(trophyRoomCard);
	}

	@Test
	void checkAccusationTest() {
		assertTrue(board.checkAccusation(eddieCard, glockCard, trophyRoomCard, solution));
		assertFalse(board.checkAccusation(eddieCard, glockCard, hallCard, solution));
		assertFalse(board.checkAccusation(eddieCard, knifeCard, trophyRoomCard, solution));
		assertFalse(board.checkAccusation(sophieCard, glockCard, trophyRoomCard, solution));

	}
	
	@Test
	void disproveSuggestionTest() {
		Player player = new ComputerPlayer("Test Player");
		
		player.addToHand(armoryCard);
		player.addToHand(glockCard);
		player.addToHand(melissaCard);
		
		Card disprove = player.disproveSuggestion(melissaCard, knifeCard, hallCard);
		assertTrue(disprove.equals(melissaCard));
		
		disprove = player.disproveSuggestion(eddieCard, glockCard, hallCard);
		assertTrue(disprove.equals(glockCard));
		
		disprove = player.disproveSuggestion(eddieCard, knifeCard, armoryCard);
		assertTrue(disprove.equals(armoryCard));
		
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
		
		Card disprove = board.handleSuggestion(sophieCard, knifeCard, hallCard, player0, playerList);
		assertEquals(disprove, null);
		
		disprove = board.handleSuggestion(marioCard, glockCard, zooCard, player0, playerList);
		assertEquals(disprove, null);
		
		disprove = board.handleSuggestion(griffinCard, taserCard, trophyRoomCard, player0, playerList);
		assertTrue(disprove.equals(taserCard));
	
	}
	
	
	@Test
	void computerCreateSuggestion() {
		ComputerPlayer computer = new ComputerPlayer("Test computer player");
		computer.addToHand(eddieCard);
		computer.addToHand(glockCard);
		
		Solution solution = computer.createSuggestion(armoryCard);
		assertFalse(solution.person.getCardName().contentEquals(eddieCard.getCardName()) );
		assertFalse(solution.weapon.getCardName().contentEquals(glockCard.getCardName()) );
	}
	
	@Test
	void computerSelectTargetTest() {
		
	}

}
