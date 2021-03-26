package clueGame;

public class Card {
	private CardType type;
	private String cardName;
	
	
	public Card(String cardName) {
		super();
		this.cardName = cardName;
	}

	
	public boolean equals(Card target) {
		if (target.getCardName().contentEquals(this.cardName)) {
			return true;
		}
		return false;
	}
	
	public void setCardType(CardType type) {
		this.type = type;
	}

	public CardType getCardType() {
		return this.type;
	}
	
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	

}
