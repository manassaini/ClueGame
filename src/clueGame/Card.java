package clueGame;

public class Card {
	
	public Card(String cardName) {
		super();
		this.cardName = cardName;
	}

	private String cardName;
	
	public boolean equals(Card target) {
		return false;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	

}
