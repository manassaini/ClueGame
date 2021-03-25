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

class GameSolutionTest {

	Card melissaCard = new Card("Melissa");

	Card sophieCard = new Card("Sophie");

	Card griffinCard = new Card("Griffin");

	Card vikCard = new Card("Vik");

	Card eddieCard = new Card("Eddie");


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

}
