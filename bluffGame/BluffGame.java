package bluffGame;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import java.util.Scanner;

public class BluffGame {

	private final List<Player> players;

	private final List<Card> deck;

	private int playerIndex;

	public BluffGame(List<String> playerNames) {

		this.players = new ArrayList<>();

		for (String name : playerNames) {

			players.add(new Player(name));

		}

		this.deck = initialize();

		Collections.shuffle(deck);

		this.playerIndex = startingPlayer();

	}

	private List<Card> initialize() {

		List<Card> deck = new ArrayList<>();

		String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

		for (String rank : ranks) {

			for (int i = 0; i < players.size(); i++) {

				deck.add(new Card(rank));

			}

		}

		return deck;

	}

	private int startingPlayer() {

		for (int i = 0; i < players.size(); i++) {

			if (players.get(i).getHand().contains(new Card("Ace"))) {

				return i;

			}

		}

		return 0;

	}

	public void simulateRound() {

		Player currentPlayer = players.get(playerIndex);

		int k = 2;
		List<Card> claimedCards = new ArrayList<>();

		for (int i = 0; i < k; i++) {

			claimedCards.add(deck.remove(0));

		}

		String claim = k + " " + claimedCards.get(0).getRank() + "s";

		System.out.println(currentPlayer.getName() + " claims: " + claim);

		int nextPlayerIndex = (playerIndex + 1) % players.size();

		Player nextPlayer = players.get(nextPlayerIndex);

		Scanner scanner = new Scanner(System.in);

		System.out.print(nextPlayer.getName() + ", enter your choice (pass/bluff/add): ");

		String choice = scanner.nextLine();

		if (choice.equalsIgnoreCase("pass")) {

			System.out.println(nextPlayer.getName() + " passes.");

		} else if (choice.equalsIgnoreCase("bluff")) {

			if (checkBluff(claimedCards)) {

				System.out.println(nextPlayer.getName() + " called bluff and was wrong!");

				nextPlayer.addToHand(claimedCards);

			} else {

				System.out.println(nextPlayer.getName() + " called bluff and was right!");

				currentPlayer.addToHand(claimedCards);

			}

		} else if (choice.equalsIgnoreCase("add")) {

			System.out.print("Enter the number of additional cards to add: ");

			int q = scanner.nextInt();

			scanner.nextLine();
			if (q > 0 && q <= deck.size() && claimedCards.size() == q) {

				for (int i = 0; i < q; i++) {

					claimedCards.add(deck.remove(0));

				}

				System.out.println(nextPlayer.getName() + " adds " + q + " more cards of rank "
						+ claimedCards.get(0).getRank() + "s.");

			} else {

				System.out.println("Invalid input. Please try again.");

				simulateRound();

				return;

			}

		}

		playerIndex = (playerIndex + 1) % players.size();

	}

	private boolean checkBluff(List<Card> claimedCards) {

		String rank = claimedCards.get(0).getRank();

		for (Card card : claimedCards) {

			if (!card.getRank().equals(rank)) {

				return true; 

			}

		}

		return false; 

	}

	public static void main(String[] args) {

		List<String> playerNames = List.of("Player 1", "Player 2", "Player 3");

		BluffGame bluffGame = new BluffGame(playerNames);

		for (int round = 1; round <= 2; round++) { // Simulate 2 rounds

			System.out.println("\nRound " + round);

			bluffGame.simulateRound();

		}

	}

}