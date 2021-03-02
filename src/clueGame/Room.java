package clueGame;

public class Room {
	// generic room class
	
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
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
	
	
}
