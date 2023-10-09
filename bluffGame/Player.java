package bluffGame;

import java.util.ArrayList;

import java.util.List;

public class Player {

	private final String name;

	private final List<Card> hand;

	public Player(String name) {

		this.name = name;

		this.hand = new ArrayList<>();

	}

	public void addToHand(List<Card> claimedCards) {

		hand.add((Card) claimedCards);

	}

	public List<Card> getHand() {

		return hand;

	}

	public String getName() {

		return name;

	}

	public void removeCards(List<Card> cardsToRemove) {

		hand.removeAll(cardsToRemove);

	}

	@Override

	public String toString() {

		return name + "'s hand: " + hand;

	}

}