package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import experiment.TestBoardCell;

public class Board {
	
	// the one board

    private static Board theInstance = new Board();
    private int numRows;
    private int numColumns;
    private BoardCell[][] grid;
    private String LayoutConfigFile;
    private Map<Character, Room> roomMap;
    
    /*
    * COPIED AND PASTED FROM ASSIGNMENT DESCRIPTION
    */
    
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
    }
    
    // this method returns the instance
    public static Board getInstance() {
           return theInstance;
    }
    
    // initialize the instance.
    public void initialize(){
    	String inputFile = "ClueLayout.csv";
    	

    	//getting number of rows
		Scanner scan = new Scanner(inputFile);
		int rows = 0;
		while (scan.hasNextLine()) {
			rows++;
			scan.nextLine();
		}
		
		//getting number of cols
		Scanner scan2 = new Scanner(inputFile);
		int cols = 0;
		String line = scan2.nextLine();
		String[] colNum = line.split(",");
		cols = colNum.length;
		
		
		
		
		
		/*
		String str = "hello, my, name, is, manas";
		String[] stringWithoutCommas = str.split(",", 20);
		*/
		
		
				
    	theInstance.numRows = rows;
    	theInstance.numColumns = cols;
    	theInstance.grid = new BoardCell[numRows][numColumns];
    	theInstance.LayoutConfigFile = "";
    	theInstance.roomMap = new HashMap<Character, Room>();
    	
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				BoardCell cell = new BoardCell();
				this.grid[i][j] = cell;
			}
		}
    	
    	Room Balcony = new Room("Balcony", grid[3][1], grid[2][1]);
    	Room Washroom = new Room("Washroom", grid[16][12], grid[14][12]);
    	Room TrophyRoom = new Room("Trophy Room", grid[23][16], grid[21][16]);
    	Room Museum = new Room("Museum", grid[13][2], grid[11][1]);
    	Room Armory = new Room("Armory", grid[13][19], grid[12][19]);
    	Room Dungeon = new Room("Dungeon", grid[2][9], grid[1][9]);
    	Room Hall = new Room("Hall", grid[24][8], grid[22][8]);
    	Room Zoo = new Room("Zoo", grid[3][18], grid[2][18]);
    	Room SecretBase = new Room("Secret Base", grid[22][1], grid[21][1]);
    	Room Unused = new Room("Unused", grid[13][8], grid[12][8]);
    	Room Walkway = new Room("Walkway", grid[13][10], grid[12][10]);
    	
    	
    	theInstance.roomMap.put('B', Balcony);
    	theInstance.roomMap.put('R', Washroom);
    	theInstance.roomMap.put('T', TrophyRoom);
    	theInstance.roomMap.put('M', Museum);
    	theInstance.roomMap.put('A', Armory);
    	theInstance.roomMap.put('D', Dungeon);
    	theInstance.roomMap.put('H', Hall);
    	theInstance.roomMap.put('Z', Zoo);
    	theInstance.roomMap.put('S', SecretBase);
    	
    	theInstance.roomMap.put('X', Unused);
    	theInstance.roomMap.put('W', Walkway);
    }
    
    
   public void loadConfigFiles() {
	   
   }
   
   public void loadSetupConfig() {
	   
   }
    
   public void loadLayoutConfig() {
	   
   }
   
   public void setConfigFiles(String csvFile, String xlFile) {
	   
   }

   public Room getRoom(char c) {
	   return roomMap.get(c);
   }
   
   public BoardCell getCell(int row, int col) {
	   return theInstance.grid[row][col];
   }
   
   public int getNumRows() {
	   return theInstance.numRows;
   }
   
   public int getNumColumns() {
	   return theInstance.numColumns;
   }
   
   public static void main(String[] args) {
	   theInstance.initialize();
	   System.out.println(theInstance.numRows + " " + theInstance.numColumns);
   }

}
