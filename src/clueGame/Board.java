package clueGame;

import java.io.File;
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
    private String layoutConfigFile;
    private String setupConfigFile;
    private Map<Character, Room> roomMap;
    
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
    }
    
    // this method returns the instance
    public static Board getInstance() {
           return theInstance;
    }
    
    // initialize the instance.
    public void initialize() throws BadConfigFormatException {
    	theInstance.loadConfigFiles();
    	
    }
    
    
   public void loadConfigFiles() throws BadConfigFormatException {
	  loadSetupConfig();
	  loadLayoutConfig();
   }
   
   
   
   public void loadSetupConfig() throws BadConfigFormatException {
	   	theInstance.roomMap = new HashMap<Character, Room>();

	   	try {													// scan txt file and create room map
				Scanner scanRoom = new Scanner(new File(theInstance.setupConfigFile));
				while (scanRoom.hasNextLine()) {
					String line = scanRoom.nextLine();
					String[] lineA = line.split(", ");
					if (lineA[0].charAt(0) != '/') {											// exclude comments
						if (lineA[0].contentEquals("Room") || lineA[0].contentEquals("Space")) {
							theInstance.roomMap.put(lineA[2].charAt(0), new Room(lineA[1]));	// lineA[2] is room initial, cast to char; lineA[1] is room name
						} else {
							throw new BadConfigFormatException("Layout Config error");
						}
								
					}	
				}		
		} catch (FileNotFoundException e) {
				System.out.println("Room file not found");
		}
	   
   }
    
   public void loadLayoutConfig() throws BadConfigFormatException {
		Scanner scan;											
		try {
			scan = new Scanner(new File(this.layoutConfigFile));				// scan for number of rows by counting lines
			
			int rows = 0;										
			while (scan.hasNextLine()) {
				scan.nextLine();
				rows++;
			}
			theInstance.numRows = rows;	
			
			Scanner scan2 = new Scanner(new File(this.layoutConfigFile));		//getting number of cols by counting length of line array
			String line = scan2.nextLine();
			String[] colNum = line.split(",");
			theInstance.numColumns = colNum.length;
		
		} catch (FileNotFoundException e) {
			System.out.println("Board file not found");
		}
	   
		Scanner scan3;
		try {
			scan3 = new Scanner(new File(this.layoutConfigFile));
			
			String[] chars = new String[numColumns];
			
			
			grid = new BoardCell[numRows][numColumns];				// create grid
		   
		   
		   for (int r = 0; r < theInstance.numRows; r++ ) {		// gets line one row at a time
				chars = scan3.nextLine().split(",");
				for (int c = 0; c < theInstance.numColumns; c++) {
					
					if (c > chars.length - 1) {						// checks to make sure column number is within width
						throw new BadConfigFormatException();
					}
					
					grid[r][c] = new BoardCell();					// creates new board cell for each spot
					BoardCell thisCell = grid[r][c];
					
					char initial = chars[c].charAt(0);				// first character in item, initial of room
					
					if (!roomMap.containsKey(initial)) {			// checks for valid initial
						throw new BadConfigFormatException();
					}
					
					thisCell.setInitial(initial);	
					
					
					if (chars[c].length() > 1) {
						char specialChar = chars[c].charAt(1);		// second character in item, "special"
						if (specialChar == '#') {					// room label
							if (roomMap.containsKey(initial)) {
								thisCell.setRoomLabel(true);
								roomMap.get(initial).setLabel(thisCell);
							} else {
								throw new BadConfigFormatException();
							}
							
						}
						else if (specialChar == '*') { 				// room center
							if (roomMap.containsKey(initial)) {
								thisCell.setRoomCenter(true);
								roomMap.get(initial).setCenter(thisCell);
							} else {
								throw new BadConfigFormatException();
							}
							
							roomMap.get(initial).setCenter(thisCell);
						}
						else if (specialChar == '^') {				// up door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.UP);
						}
						else if (specialChar == 'v') {				// down door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.DOWN);
							
						}
						else if (specialChar == '>') {				// right door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.RIGHT);
							
						}
						else if (specialChar == '<') {				// left door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.LEFT); 
						} 
						else {										// secret passage
							thisCell.setSecretPassage(specialChar);
						}
					}
					
				}
			
		   }
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
   }
   
   
   public void setConfigFiles(String csvFile, String txtFile) {
	   this.layoutConfigFile = csvFile;
	   this.setupConfigFile = txtFile;
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

}
