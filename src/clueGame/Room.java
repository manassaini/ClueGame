package clueGame;

public class Room {
	// generic room
	
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public BoardCell getLabelCell() {
		return this.labelCell;
	}
	
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
	
	public void setCenter(BoardCell center) {
		this.centerCell = center;
	}
	
	public void setLabel(BoardCell label) {
		this.labelCell = label;
	}
	
	
}
