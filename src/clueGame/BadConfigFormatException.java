package clueGame;

public class BadConfigFormatException extends Exception {
	
	private String message;

	public BadConfigFormatException() {
		super();
		this.message = "Bad Config Format Exception";
	}

	public BadConfigFormatException(String message) {
		super();
		this.message = message;
	}

}
