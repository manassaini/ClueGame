package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import experiment.TestBoardCell;

public class BoardCell {
	// board cell
	
	private int row;
	private int col;
	private char initial;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private boolean isSecretPassage = false;
	private Set<BoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway = false;
	private boolean isTarget = false;
	Set<BoardCell> targets;
	private DoorDirection direction = DoorDirection.NONE;
	
	public BoardCell() {
		super();
		this.adjList = new HashSet<BoardCell>();
		this.targets = new HashSet<BoardCell>();
	}
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void draw(int xScale, int yScale, Graphics g, Color color) {				// generic draw
		g.setColor(color);
		g.fillRect(this.col * xScale, this.row * yScale, xScale, yScale);
	}
	
	
	public void drawWalkway(int xScale, int yScale, Graphics g, Color color) {		// walkways
		g.setColor(Color.black);													// black outline
		g.fillRect(this.col * xScale, this.row * yScale, xScale, yScale);
		g.setColor(color);															// center
		g.fillRect(this.col * xScale+1, this.row * yScale+1, xScale-2, yScale-2);
	}
	
	
	public void drawDoorway(int xScale, int yScale, Graphics g, Color color) {		// doorway
		g.setColor(Color.gray);														// gray because walkway
		g.fillRect(this.col * xScale, this.row * yScale, xScale, yScale);	
		g.setColor(color.blue);														// blue door
		
		if (direction == DoorDirection.RIGHT) {
			g.fillRect((this.col+1) * xScale - (xScale)/5, this.row * yScale, xScale/5, yScale);
		}
		else if (direction == DoorDirection.LEFT) {
			g.fillRect(this.col * xScale, this.row * yScale, xScale/5, yScale);
		}
		else if (direction == DoorDirection.UP) {
			g.fillRect(this.col * xScale, this.row * yScale, xScale, yScale/5);
		}
		else {
			g.fillRect(this.col * xScale, (this.row+1) * yScale - (xScale)/5, xScale, yScale);
		}
	}
	
	
	public void writeLabel(String label, Graphics g, int xScale, int yScale) {		// room label
		g.setColor(Color.black);
		g.setFont((new Font("Monaco", Font.BOLD, 12)));
		g.drawString(label, this.col * xScale, this.row * yScale);
	}
	
	public boolean isTarget() {
		return this.isTarget;
	}
	
	public void setIsTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
	
	public void addAdj (BoardCell cell) {
		adjList.add(cell);
	}
	
	public boolean isRoomCenter() {
		return this.roomCenter;
	}
	
	public boolean isDoorway() {
		return this.isDoorway;
	}
	
	public DoorDirection getDoorDirection() {
		return this.direction;
	}
	
	public boolean isLabel() {
		return this.roomLabel;
	}
	
	public char getSecretPassage() {
		return this.secretPassage;
	}
	
	public void setInitial(char c) {
		this.initial = c;
		
	}
	
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	public void setDoorDirection(DoorDirection direction) {
		this.direction = direction;
	}
	
	public void setRoomLabel(boolean label) {
		this.roomLabel = label;
	}
	
	public void setRoomCenter(boolean center) {
		this.roomCenter = center;
	}
	
	public void setSecretPassage(char c) {
		this.isSecretPassage = true;
		this.secretPassage = c;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
	public boolean getIsSecretPassage() {
		return this.isSecretPassage;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public Set<BoardCell> getAdjList() {
		return this.adjList;
	}
	
	public char getInitial() {
		return this.initial;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public boolean getIsOccupied() {
		return this.isOccupied;
	}
	
	public boolean isUnused() {
		if (this.initial == 'X') {
			return true;
		}
		
		return false;
	}
	
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean getIsRoom() {
		return this.isRoom;
	}
	
	public void addTarget(BoardCell target) {
		this.targets.add(target);
	}
	
	public Set<BoardCell> getTargets() {
		return this.targets;
	}
	
	public void clearTargets() {
		this.targets.clear();
	}
	
	public boolean equals(BoardCell cell) {
		if (this.row == cell.getRow() && this.col == cell.getCol()) {
			return true;
		}
		return false;
	}
	
	
}
