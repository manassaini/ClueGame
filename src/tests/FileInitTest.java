package tests;

import java.io.FileNotFoundException;

import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

public class FileInitTest {
	
	// Test that an exception is thrown for file with different num of columns in rows
	@Test(expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayoutBadColumns306.csv", "ClueSetup306.txt");
		// Instead of initialize, we call the two load functions directly.
		// This is necessary because initialize contains a try-catch.
		board.loadSetupConfig();
		// This one should throw an exception
		board.loadLayoutConfig();
	}

}
