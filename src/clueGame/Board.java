package clueGame;


import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Board extends JPanel{

	private static Board theInstance = new Board();
	private int numRows;
	private int numColumns;
	private int xScale;
	private int yScale;
	private int counter = 0;
	private BoardCell[][] grid;
	private GameControlPanel controlPanel;
	private Player currentPlayer;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	
	private Map<Character, Room> roomMap;
	
	private ArrayList<Card> roomCards;
	private ArrayList<Card> personCards;
	private ArrayList<Card> weaponCards;
	private ArrayList<Card> allCards;
	private ArrayList<Player> players;
	
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private Solution solution;
	
	private static final int MIN_DICE_ROLL = 1;
	private static final int MAX_ROLL = 6;
	
	private JLabel jLabel;
	
	
	/////				 ***** CONSTRUCTORS AND INITIALIZING *****
	
	// constructor, private so only one can be made
	private Board() {
		super();
	}

	public void setControlPanel(GameControlPanel gameControl) {
		controlPanel = gameControl;
	}
	// returns the instance
	public static Board getInstance() {
		return theInstance;
	}
		
	// draw the board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		xScale = this.getWidth()/numColumns;			// width of each cell
		yScale = this.getHeight()/numRows;
		
		super.paintComponent(g);										// each cell type drawn differently
		for (int i = 0; i < numRows; ++i) {
			for (int j = 0; j < numColumns; ++j) {
				if (grid[i][j].isDoorway()) {							// doorway
					grid[i][j].drawDoorway(xScale, yScale, g, Color.DARK_GRAY);
				}
				else if (grid[i][j].getIsSecretPassage()) {				// secret passage
					grid[i][j].draw(xScale, yScale, g, Color.green);
				}
				else if (grid[i][j].getInitial() == 'W') {				// walkway
					grid[i][j].drawWalkway(xScale, yScale, g, Color.gray);
				}
				else if (grid[i][j].getInitial() == 'X') {				// unused
					grid[i][j].draw(xScale, yScale, g, Color.black);
				}
				else {													// room
					grid[i][j].draw(xScale, yScale, g, Color.yellow);
				}
				
			}
		}
		
		for (int i = 0; i < numRows; ++i) {								// reiterate for room labels
			for (int j = 0; j < numColumns; ++j) {
				if (grid[i][j].isLabel()) {
					char initial = grid[i][j].getInitial();
					Room room = roomMap.get(initial);
					String label = room.getName();
					grid[i][j].writeLabel(label, g, xScale, yScale);
				}
			}
		}
		
		for (Player p: theInstance.players) {							// players
			p.draw(g, yScale, xScale);
		}
	}
	
	public void displayStartMessage() {
		JOptionPane.showMessageDialog(null, "You are " + players.get(0).getName() + "\nCan you find the solution before the other computer players?");
	}
	
	public void nextClicked() {
		Random rand = new Random();
		int roll = rand.nextInt(MAX_ROLL) + MIN_DICE_ROLL;
		System.out.println(roll);
		currentPlayer = new ComputerPlayer(players.get(counter).getName(), players.get(counter).getRow(), players.get(counter).getCol(), players.get(counter).getColor());
		controlPanel.setTurn(currentPlayer, roll);
		counter++;
		if (counter > players.size()-1) {
			counter = 0;
		}
	}
	
	
	// initialize the instance, read in files, allocate space to array list, deal cards
	public void initialize() throws BadConfigFormatException {
		theInstance.loadConfigFiles();
		
		theInstance.targets = new HashSet<BoardCell>();
		theInstance.visited = new HashSet<BoardCell>();
		
		theInstance.dealCards();
	}

	
	// calls to 2 load files methods
	public void loadConfigFiles() throws BadConfigFormatException {
		loadSetupConfig();
		loadLayoutConfig();
	}


	// Parameters: text files to be read in
	public void setConfigFiles(String csvFile, String txtFile) {		// takes in files to read
		this.layoutConfigFile = csvFile;
		this.setupConfigFile = txtFile;
	}
	
	/////						 ***** READ IN FILES *****

	// create room map and initialize cards
	public void loadSetupConfig() throws BadConfigFormatException {
		theInstance.roomMap = new HashMap<Character, Room>();
		theInstance.roomCards = new ArrayList<Card>();
		theInstance.personCards = new ArrayList<Card>();
		theInstance.weaponCards = new ArrayList<Card>();
		

		try {																				
			Scanner scanRoom = new Scanner(new File(theInstance.setupConfigFile));			
			while (scanRoom.hasNextLine()) {
				String lineS = scanRoom.nextLine();													// line, split by commas
				String[] line = lineS.split(", ");
				loadSetupHelper(line);
			}
			scanRoom.close();
		} catch (FileNotFoundException e) {
			System.out.println("Room file not found");
		}
	}
		
	// Helper method to above, sorts file input
	public void loadSetupHelper(String[] line) throws BadConfigFormatException {
		String firstWord = line[0];
		if (firstWord.charAt(0) != '/') {
			if (firstWord.contentEquals("Room") || firstWord.contentEquals("Space")) {		// room or space, need put in room map
				Room room = new Room(line[1]);
				theInstance.roomMap.put(line[2].charAt(0), room);							
				if (firstWord.contentEquals("Room")) {										// rooms need card, spaces do not
					Card newCard = new Card(line[1]);
					theInstance.roomCards.add(newCard);
					newCard.setCardType(CardType.ROOM);
				}
			}
			else if (firstWord.contentEquals("Person")) {									// person
				Card newCard = new Card(line[1]);
				theInstance.personCards.add(newCard);
				newCard.setCardType(CardType.PERSON);
			} 
			else if (firstWord.contentEquals("Weapon")) {									// weapon
				Card newCard = new Card(line[1]);
				theInstance.weaponCards.add(newCard);
				newCard.setCardType(CardType.WEAPON);
			}
			else {
				throw new BadConfigFormatException("Layout Config error");					// typo
			}
		}
	}
	
	
	
	
	
	// Reads file, counts rows and columns, creates board array and board cells, assigns rooms door cells 
	public void loadLayoutConfig() throws BadConfigFormatException {	
		rowsAndColumnsHelper();

		Scanner scan3;
		try {
			scan3 = new Scanner(new File(this.layoutConfigFile));	
			String[] chars = new String[numColumns];				// creates array for reading in each row, one at a time
			grid = new BoardCell[numRows][numColumns];				// create grid


			for (int r = 0; r < theInstance.numRows; r++ ) {			// gets line one row at a time
				chars = scan3.nextLine().split(",");					// splits by columns
				for (int c = 0; c < theInstance.numColumns; c++) {

					if (c > chars.length - 1) {						// checks to make sure column number is within width
						throw new BadConfigFormatException();
					}

					grid[r][c] = new BoardCell();					// creates new board cell
					BoardCell thisCell = grid[r][c];
					
					thisCell.setRow(r);								// set row and column
					thisCell.setCol(c);

					char initial = chars[c].charAt(0);				// first character in item, initial of room

					if (!roomMap.containsKey(initial)) {			// checks for valid initial
						throw new BadConfigFormatException();
					}

					thisCell.setInitial(initial);					
					
					if (initial!= 'W' && initial != 'X') {
						thisCell.setIsRoom(true);
					}
					else {
						thisCell.setIsRoom(false);
					}

					if (chars[c].length() > 1) {
						char specialChar = chars[c].charAt(1);		// second character in item, "special"; either door, label, center, secret passage
						
						switch(specialChar) {
						case '#':										// room label
							if (roomMap.containsKey(initial)) {
								thisCell.setRoomLabel(true);
								roomMap.get(initial).setLabel(thisCell);
							}
							else {
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
			doorAssignmentHelper();
			scan3.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	
	
	// Helper to above, using files, scans for number of rows and columns
	public void rowsAndColumnsHelper() {
		Scanner scan;															// rows
		try {
			scan = new Scanner(new File(this.layoutConfigFile));				// scan for number of rows by counting lines
			int rows = 0;										
			while (scan.hasNextLine()) {
				scan.nextLine();
				rows++;
			}
			theInstance.numRows = rows;	
			scan.close();

			
			Scanner scan2 = new Scanner(new File(this.layoutConfigFile));		//getting number of cols by counting length of line array
			String line = scan2.nextLine();
			String[] colNum = line.split(",");
			theInstance.numColumns = colNum.length;
			scan2.close();
		} catch (FileNotFoundException e) {
			System.out.println("Board file not found");
		}
	}
		
	
	
	// Go through each cell, check if door, if it is, find adjacent room and set that cell as room's door
	public void doorAssignmentHelper() {
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
	}
		
	
	
	
	
	///// 						***** MAKE PLAYERS, DECK *****
	
	// Create players and set starting locations, assigns random colors and deals cards
	public void dealCards() {
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.red);
		colors.add(Color.orange);
		colors.add(Color.green);
		colors.add(Color.blue);
		colors.add(Color.yellow);
		colors.add(Color.cyan);
		colors.add(Color.magenta);
		
		theInstance.players = new ArrayList<Player>();
		
		ArrayList<Card> personDuplicate = new ArrayList<Card>();
		personDuplicate.addAll(0, theInstance.personCards);
		
		HumanPlayer human = new HumanPlayer("human");
		human.setLoc(25, 12);
		human.setPerson(getRandomCard(personDuplicate));
		theInstance.players.add(human);
		
		ComputerPlayer comp1 = new ComputerPlayer("comp1");
		comp1.setLoc(25, 4);
		comp1.setPerson(getRandomCard(personDuplicate));
		theInstance.players.add(comp1);
		
		ComputerPlayer comp2 = new ComputerPlayer("comp2");
		comp2.setLoc(0, 13);
		comp2.setPerson(getRandomCard(personDuplicate));
		theInstance.players.add(comp2);
		
		ComputerPlayer comp3 = new ComputerPlayer("comp3");
		comp3.setLoc(0, 14);
		comp3.setPerson(getRandomCard(personDuplicate));
		theInstance.players.add(comp3);
		
		ComputerPlayer comp4 = new ComputerPlayer("comp4");
		comp4.setLoc(9, 20);
		comp4.setPerson(getRandomCard(personDuplicate));
		theInstance.players.add(comp4);
		
		ComputerPlayer comp5 = new ComputerPlayer("comp5");
		comp5.setLoc(7, 0);
		comp5.setPerson(getRandomCard(personDuplicate));
		theInstance.players.add(comp5);
		
	
		
		theInstance.solution = new Solution();
		
		theInstance.allCards = new ArrayList<Card>();						// all cards, including solution
		theInstance.allCards = makeCompleteDeck();
		
		theInstance.solution.setPerson(getRandomCard(theInstance.personCards));			// create random solution
		theInstance.solution.setWeapon(getRandomCard(theInstance.weaponCards));
		theInstance.solution.setRoom(getRandomCard(theInstance.roomCards));
		
		ArrayList<Card> cards = new ArrayList<Card>();						// all cards, not including solution; to be dealt
		cards.addAll(theInstance.personCards);
		cards.addAll(theInstance.weaponCards);
		cards.addAll(theInstance.roomCards);
		
		for (Player p: players) {	
			Random random = new Random();									// based off code from geeks for geeks
			Color color = colors.get(random.nextInt(colors.size()));
			p.setColor(color);
			colors.remove(color);
			p.addToHand(getRandomCard(cards));
			p.addToHand(getRandomCard(cards));
			p.addToHand(getRandomCard(cards));
		}		
	}
	
	
	
	// Combine all cards into comprehensive deck (used in test)
	public ArrayList<Card> makeCompleteDeck() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.addAll(theInstance.personCards);
		cards.addAll(theInstance.weaponCards);
		cards.addAll(theInstance.roomCards);
		return cards;
	}
	
	
	// Helper method to above
	public Card getRandomCard(ArrayList<Card> list) {		// based off code from geeks for geeks
		Random random = new Random();
		Card card = list.get(random.nextInt(list.size()));
		list.remove(card);
		return card;
	}
	
	
	
	
	
	//							 ***** SUGGESTIONS AND SOLUTION *****
	
	// Handle suggestion in each player
	public Card handleSuggestion(Card person, Card weapon, Card room, Player player, ArrayList<Player> players) {
		ArrayList<Card> options = new ArrayList<Card>();
		for (Player p: players) {
			if (!p.getName().contentEquals(player.getName())) {
				Card c = p.disproveSuggestion(person, weapon, room);
				if (c != null) {
					options.add(c);
				}	
			}

		}

		if (options.size() == 0) {
			return null;
		} else {
			return options.get(0);
		}
	}
	
	
	// Check accusation compared to solution
	public boolean checkAccusation(Card person, Card weapon, Card room, Solution solution) {
		if (person == solution.person && weapon == solution.weapon && room == solution.room) {
			return true;
		}
		return false;	
	}
		
		
		
	
	
	
	// 							***** CALC ADJACENCIES AND TARGETS *****
	
	// Calc targets
	public void calcTargets(BoardCell startCell, int pathlength) {
		theInstance.visited.clear();
		theInstance.targets.clear();
		
		recursivePart(startCell, pathlength);	
	}
	
	
	
	// Recursive portion
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
					startCell.addTarget(adjCell);
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
	
	
	
	
	
	
	// Calc Adjacencies
	public void calcAdjacencies(BoardCell cell) {
		
		int row = cell.getRow();	
		int column = cell.getCol();
		
		BoardCell upCell = null;
		BoardCell downCell = null;
		BoardCell leftCell = null;
		BoardCell rightCell = null;
		
		
		ArrayList<BoardCell> neighbors = new ArrayList<BoardCell>();		//allows for iteration at each criteria
		if (row > 0) {														// only added if in spot within boundaries, so no need to check
			upCell = theInstance.grid[row - 1][column];
			neighbors.add(upCell);
		}
		if (column > 0) {
			leftCell = theInstance.grid[row][column - 1];
			neighbors.add(leftCell);
		}
		if (row < (theInstance.numRows-1)) {
			downCell = theInstance.grid[row + 1][column];
			neighbors.add(downCell);
		}
		if (column < (theInstance.numColumns-1)) {
			rightCell = theInstance.grid[row][column + 1];
			neighbors.add(rightCell);
		}
		
		
		
		if (cell.getInitial() == 'W' && !cell.isDoorway()) {	// non-door walkways, check if neighbors are walkways
			for (BoardCell neighbor: neighbors) {
				if (neighbor.getInitial() == 'W') {
					cell.addAdj(neighbor);
				}
			}
		}
		else if (cell.isRoomCenter()) {							// room center
			Room room = roomMap.get(cell.getInitial());
			for (BoardCell door: room.getDoors()) {
				cell.addAdj(door);
			}
			if (room.hasSecret()) {								// room with secret passage
				char initial = cell.getInitial();
				Room current = roomMap.get(initial);
				BoardCell sp = current.getSecretPassage();
				char nextInitial = sp.getSecretPassage();
				Room next = roomMap.get(nextInitial);
				cell.addAdj(next.getCenterCell());
			}
		}	
		else if (cell.isLabel() || cell.getInitial()!= 'W' || cell.getIsSecretPassage()) {	// room labels, walkways, secret passage
			// nothing
		}
		else {													// doorway
			
//			if (cell.isDoorway()) {
				if (cell.getDoorDirection() == DoorDirection.DOWN) {
					calcAdjDoorHelper(cell, downCell, neighbors);
				} 
				else if (cell.getDoorDirection() == DoorDirection.UP) {
					calcAdjDoorHelper(cell, upCell, neighbors);
				}
				else if (cell.getDoorDirection() == DoorDirection.LEFT) {
					calcAdjDoorHelper(cell, leftCell, neighbors);
				}
				else if (cell.getDoorDirection() == DoorDirection.RIGHT) {
					calcAdjDoorHelper(cell, rightCell, neighbors);
				}
//			}
		}
		neighbors.clear();
	}

	
	
	// Helper for door directions above
	public void calcAdjDoorHelper(BoardCell original, BoardCell directional, ArrayList<BoardCell> neighbors) {
		for (BoardCell neighbor: neighbors) {
			if (!neighbor.equals(directional)) {
				if (neighbor.getInitial() == original.getInitial()) {
					original.addAdj(neighbor);
				}
			} else {
				char initial = directional.getInitial();
				Room room = roomMap.get(initial);
				original.addAdj(room.getCenterCell());
			}
		}
	}
	
	
	
	
	
	
	//					 ***** GETTERS AND SETTERS *****
		
	public ArrayList<Card> getPersonCards() {
		return this.personCards;
	}
	
	
	public ArrayList<Card> getWeaponCards() {
		return this.weaponCards;
	}
	
	
	public ArrayList<Card> getRoomCards() {
		return this.roomCards;
	}
	
	public Map<Character, Room> getRoomMap() {
		return theInstance.roomMap;
	}
	

	
	public ArrayList<Card> getCompleteDeck() {
		return theInstance.allCards;
	}
	public Set<BoardCell> getTargets() {
		return this.targets;
	}

	
	public Set<BoardCell> getAdjList(int row, int col) {
		calcAdjacencies(grid[row][col]);
		return grid[row][col].getAdjList();
	}


	public Room getRoom(char c) {	
		return roomMap.get(c);
	}

	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	
	public ArrayList<String> getPeople() {
		ArrayList<String> names = new ArrayList<String>();
		for (Card c: theInstance.personCards) {
			names.add(c.getCardName());
		}
		return names;
	}
	
	
	public ArrayList<String> getWeapons() {
		ArrayList<String> weaponNames = new ArrayList<String>();
		for (Card c: theInstance.weaponCards) {
			weaponNames.add(c.getCardName());
		}
		return weaponNames;
	}
	
	
	public ArrayList<Player> getPlayers() {
		return theInstance.players;
	}
	
	
	public ArrayList<String> getRoomDeck() {
		ArrayList<String> rooms = new ArrayList<String>();
		for (Card c: theInstance.roomCards) {
			rooms.add(c.getCardName());
		}
		return rooms;
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
	

	public BoardCell[][] getGrid() {
		return this.grid;
	}

}
