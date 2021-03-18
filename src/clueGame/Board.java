package clueGame;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board {

	private static final int MIN_DICE_ROLL = 1;
	// the one board

	private static Board theInstance = new Board();
	private int numRows;
	private int numColumns;
	private BoardCell[][] grid;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private boolean inRoom;

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
		
		theInstance.targets = new HashSet<BoardCell>();
		theInstance.visited = new HashSet<BoardCell>();
	}


	public void loadConfigFiles() throws BadConfigFormatException {
		loadSetupConfig();
		loadLayoutConfig();
	}



	public void loadSetupConfig() throws BadConfigFormatException {	// Creates room map
		theInstance.roomMap = new HashMap<Character, Room>();

		try {													// scan txt file and create room map
			Scanner scanRoom = new Scanner(new File(theInstance.setupConfigFile));
			while (scanRoom.hasNextLine()) {
				String lineS = scanRoom.nextLine();
				String[] line = lineS.split(", ");
				if (line[0].charAt(0) != '/') {											// exclude comments
					if (line[0].contentEquals("Room") || line[0].contentEquals("Space")) {
						theInstance.roomMap.put(line[2].charAt(0), new Room(line[1]));	// line[2] is room initial, cast to char; lineA[1] is room name
					} else {
						throw new BadConfigFormatException("Layout Config error");
					}

				}	
			}		
		} catch (FileNotFoundException e) {
			System.out.println("Room file not found");
		}

	}

	public void loadLayoutConfig() throws BadConfigFormatException {			// Calcs rows and columns, creates grid
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
			scan3 = new Scanner(new File(this.layoutConfigFile));	// scanner
			String[] chars = new String[numColumns];				// creates array for reading in each row, one at a time
			grid = new BoardCell[numRows][numColumns];				// create grid


			for (int r = 0; r < theInstance.numRows; r++ ) {			// gets line one row at a time
				chars = scan3.nextLine().split(",");
				for (int c = 0; c < theInstance.numColumns; c++) {

					if (c > chars.length - 1) {						// checks to make sure column number is within width
						throw new BadConfigFormatException();
					}

					grid[r][c] = new BoardCell();					// creates new board cell for each spot
					BoardCell thisCell = grid[r][c];
					
					thisCell.setRow(r);
					thisCell.setCol(c);

					char initial = chars[c].charAt(0);				// first character in item, initial of room

					if (!roomMap.containsKey(initial)) {			// checks for valid initial
						throw new BadConfigFormatException();
					}

					thisCell.setInitial(initial);					// gives BoardCell its initial

					if (chars[c].length() > 1) {
						char specialChar = chars[c].charAt(1);		// second character in item, "special"; either door, label, center, secret passage
						
						switch(specialChar) {
						case '#':										// room label
							if (roomMap.containsKey(initial)) {
								thisCell.setRoomLabel(true);
								roomMap.get(initial).setLabel(thisCell);
							} else {
								throw new BadConfigFormatException();
							}
							break;
							
						case '*':										// room center
							if (roomMap.containsKey(initial)) {
								thisCell.setRoomCenter(true);
								roomMap.get(initial).setCenter(thisCell);
							} else {
								throw new BadConfigFormatException();
							}

							roomMap.get(initial).setCenter(thisCell);
							break;
						
						case '^':										// up door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.UP);
							break;
							
						case 'v':										// down door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.DOWN);
							break;
						
						case '>':										// right door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.RIGHT);
							break;
							
						case '<':										// left door
							thisCell.setIsDoorway(true);
							thisCell.setDoorDirection(DoorDirection.LEFT);
							break;
						
						default:
							thisCell.setSecretPassage(specialChar);		// secret passage
							roomMap.get(initial).setSecretPassage(thisCell);
							break;
							
						}
						

				}

				}

			}
			
			for (int r = 0; r < theInstance.numRows; r++ ) {
				for (int c = 0; c < theInstance.numColumns; c++) {
					BoardCell cell = grid[r][c];
					if (cell.isDoorway()) {
						char initial;
						Room room;
						
						switch(cell.getDoorDirection()) {
						case DOWN:
							initial = grid[r+1][c].getInitial();
							room = roomMap.get(initial);
							room.addDoor(cell);
							break;
						
						case UP:
							initial = grid[r-1][c].getInitial();
							room = roomMap.get(initial);
							room.addDoor(cell);
							break;
						
						case LEFT:
							initial = grid[r][c-1].getInitial();
							room = roomMap.get(initial);
							room.addDoor(cell);
							break;
						
						case RIGHT:
							initial = grid[r][c+1].getInitial();
							room = roomMap.get(initial);
							room.addDoor(cell);
							break;
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void setConfigFiles(String csvFile, String txtFile) {		// takes in files to read
		this.layoutConfigFile = csvFile;
		this.setupConfigFile = txtFile;
	}

	public void calcTargets(BoardCell startCell, int pathlength) {
		
		theInstance.inRoom = false;
		theInstance.targets.clear();
		theInstance.visited.clear();
		
		recursivePart(startCell, pathlength);
		
		
	}
	
	
	public void recursivePart(BoardCell startCell, int pathlength) {
		calcAdjacencies(startCell);
		theInstance.visited.add(startCell);
		
		for (BoardCell adjCell: startCell.getAdjList()) {
			
			if (!visited.contains(adjCell)) {
				theInstance.visited.add(adjCell);
				
				if (adjCell.getIsOccupied() && !adjCell.isRoomCenter()) {
					continue;
				}
				else if (pathlength == MIN_DICE_ROLL || adjCell.isRoomCenter()) {
					theInstance.targets.add(adjCell);
				} else if (pathlength != MIN_DICE_ROLL){
					recursivePart(adjCell, pathlength - 1);
				}
				theInstance.visited.remove(adjCell);
			}	
			else if ((visited.contains(adjCell))) {
				continue;
			}
		}
	}
	
	public void calcAdjacencies(BoardCell cell) {
		
		if (cell.getInitial() == 'W' && !cell.isDoorway()) {
			if (cell.getRow() > 0) {
				if (grid[cell.getRow()-1][cell.getCol()].getInitial() == 'W') {
				cell.addAdj(grid[cell.getRow()-1][cell.getCol()]);
				}
			}
			if (cell.getCol() > 0) {
				if (grid[cell.getRow()][cell.getCol()-1].getInitial() == 'W') {
					cell.addAdj(grid[cell.getRow()][cell.getCol()-1]);
				}
			}
			if (cell.getRow() < numRows-1) {
				if (grid[cell.getRow()+1][cell.getCol()].getInitial() == 'W') {
				cell.addAdj(grid[cell.getRow()+1][cell.getCol()]);	
				}	
			}
			if (cell.getCol() < numColumns-1) {
				if (grid[cell.getRow()][cell.getCol()+1].getInitial() == 'W') {
				cell.addAdj(grid[cell.getRow()][cell.getCol()+1]);	
				}	
			}
		}
		
		else if (cell.getIsSecretPassage()) {					// secret passage
			
//			Room room = roomMap.get(cell.getInitial());		// add room center
//			BoardCell roomCenter = room.getCenterCell();
//			cell.addAdj(roomCenter);
//			
//			char passageTo = cell.getSecretPassage();		// add passage room center
//			room = roomMap.get(passageTo);
//			BoardCell passage = room.getCenterCell();
//			cell.addAdj(roomCenter);
			
		}
		else if (cell.isRoomCenter()) {
			
			Room room = roomMap.get(cell.getInitial());
			for (BoardCell door: room.getDoors()) {
				cell.addAdj(door);
			}
			
			if (room.hasSecret()) {
				char initial = cell.getInitial();
				Room current = roomMap.get(initial);
				BoardCell sp = current.getSecretPassage();
				char nextInitial = sp.getSecretPassage();
				Room next = roomMap.get(nextInitial);
				cell.addAdj(next.getCenterCell());
				
			}
			
		}	
		else if (cell.isLabel() || cell.getInitial()!= 'W') {
			// nothing
		}
		else {
			
			if (cell.isDoorway()) {
				char initial;
				Room room;
				
				if (cell.getDoorDirection() == DoorDirection.DOWN) {
					BoardCell downCell = grid[cell.getRow()+1][cell.getCol()];
					if (cell.getInitial() == downCell.getInitial()) {
						cell.addAdj(downCell);
					} 
					else {
						initial = downCell.getInitial();
						room = roomMap.get(initial);
						cell.addAdj(room.getCenterCell());
					}
					
					if (cell.getRow() > 0) {
						BoardCell upCell = grid[cell.getRow()-1][cell.getCol()];
						if (upCell.getInitial() == cell.getInitial()) {
							cell.addAdj(upCell);
						}
					}
					
					if (cell.getCol() < (theInstance.numColumns-1)) {
						BoardCell rightCell = grid[cell.getRow()][cell.getCol()+1];
						if (rightCell.getInitial() == cell.getInitial()) {
							cell.addAdj(rightCell);
						}
					}
					
					if (cell.getCol() > 0) {
						BoardCell leftCell = grid[cell.getRow()][cell.getCol()-1];
						if (leftCell.getInitial() == cell.getInitial()) {
							cell.addAdj(leftCell);
						}
					}
					
				} 
				
				
				
				else if (cell.getDoorDirection() == DoorDirection.UP) {
					
					BoardCell upCell = grid[cell.getRow()-1][cell.getCol()];
					if (cell.getInitial() == upCell.getInitial()) {
						cell.addAdj(upCell);
					} 
					else {
						initial = upCell.getInitial();
						room = roomMap.get(initial);
						cell.addAdj(room.getCenterCell());
					}
					
					if (cell.getCol() > 0) {
						BoardCell leftCell = grid[cell.getRow()][cell.getCol()-1];
						if (leftCell.getInitial() == cell.getInitial()) {
							cell.addAdj(leftCell);
						}
					}
					
					if (cell.getCol() < (theInstance.numColumns-1)) {
						BoardCell rightCell = grid[cell.getRow()][cell.getCol()+1];
						if (rightCell.getInitial() == cell.getInitial()) {
							cell.addAdj(rightCell);
						}
					}
					
					if (cell.getRow() < (theInstance.numRows-1)) {
						BoardCell downCell = grid[cell.getRow()+1][cell.getCol()];
						if (downCell.getInitial() == cell.getInitial()) {
							cell.addAdj(downCell);
						}
					}
					
				}
				
				
				else if (cell.getDoorDirection() == DoorDirection.LEFT) {
					BoardCell leftCell = grid[cell.getRow()][cell.getCol()-1];
					if (cell.getInitial() == leftCell.getInitial()) {
						cell.addAdj(leftCell);
					}
					else {
						initial = leftCell.getInitial();
						room = roomMap.get(initial);
						cell.addAdj(room.getCenterCell());
					}
					
					if (cell.getRow() > 0) {
						BoardCell upCell = grid[cell.getRow()-1][cell.getCol()];
						if (upCell.getInitial() == cell.getInitial()) {
							cell.addAdj(upCell);
						}
					}
					
					if (cell.getCol() < (theInstance.numColumns-1)) {
						BoardCell rightCell = grid[cell.getRow()][cell.getCol()+1];
						if (rightCell.getInitial() == cell.getInitial()) {
							cell.addAdj(rightCell);
						}
					}
					
					if (cell.getRow() < (theInstance.numRows-1)) {
						BoardCell downCell = grid[cell.getRow()+1][cell.getCol()];
						if (downCell.getInitial() == cell.getInitial()) {
							cell.addAdj(downCell);
						}
					}
					
					
				}
				else if (cell.getDoorDirection() == DoorDirection.RIGHT) {
					BoardCell rightCell = grid[cell.getRow()][cell.getCol()+1];
					if (cell.getInitial() == rightCell.getInitial()) {
						cell.addAdj(rightCell);
					}
					else {
						initial = rightCell.getInitial();
						room = roomMap.get(initial);
						cell.addAdj(room.getCenterCell());
					}
					
					if (cell.getCol() > 0) {
						BoardCell leftCell = grid[cell.getRow()][cell.getCol()-1];
						if (leftCell.getInitial() == cell.getInitial()) {
							cell.addAdj(leftCell);
						}
					}
					
					if (cell.getRow() > 0) {
						BoardCell upCell = grid[cell.getRow()-1][cell.getCol()];
						if (upCell.getInitial() == cell.getInitial()) {
							cell.addAdj(upCell);
						}
					}
					
					if (cell.getRow() < (theInstance.numRows-1)) {
						BoardCell downCell = grid[cell.getRow()+1][cell.getCol()];
						if (downCell.getInitial() == cell.getInitial()) {
							cell.addAdj(downCell);
						}
					}
					
					
				}
			}
			else {
				if (cell.getCol() > 0) {
					BoardCell leftCell = grid[cell.getRow()][cell.getCol()-1];
					if (leftCell.getInitial() == cell.getInitial()) {
						cell.addAdj(leftCell);
					}
				}
				if (cell.getRow() > 0) {
					BoardCell upCell = grid[cell.getRow()-1][cell.getCol()];
					if (upCell.getInitial() == cell.getInitial()) {
						cell.addAdj(upCell);
					}
				}
				if (cell.getCol() < (theInstance.numColumns-1)) {
					BoardCell rightCell = grid[cell.getRow()][cell.getCol()+1];
					if (rightCell.getInitial() == cell.getInitial()) {
						cell.addAdj(rightCell);
					}
				}
				if (cell.getRow() < (theInstance.numRows-1)) {
					BoardCell downCell = grid[cell.getRow()+1][cell.getCol()];
					if (downCell.getInitial() == cell.getInitial()) {
						cell.addAdj(downCell);
					}
				}	
			}
		}

	}


	public Set<BoardCell> getTargets() {
		return this.targets;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		calcAdjacencies(grid[row][col]);
		return grid[row][col].getAdjList();
	}


	public Room getRoom(char c) {								// getters and setters
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
