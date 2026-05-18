package com.epam.practice.design.pattern.design.pattern.behavioral;

public class TemplateDesign {

	// Template Design Pattern is a behavioral design pattern that defines the skeleton of an algorithm in a method, 
	//called a template method, and allows subclasses to override specific steps of the algorithm without changing its structure.
	public static void main(String[] args) {
		DataProcessor csvProcessor = new CSVDataProcessor();
		csvProcessor.process();		
		System.out.println();		
		DataProcessor jsonProcessor = new JSONDataProcessor();
		jsonProcessor.process();
		
		System.out.println(("====").repeat(10));
		
		// Example of using the Game template
		Game chessGame = new Chess(2, 3);
		chessGame.playGame();
	}
}

abstract class Game{

	int numberOfPlayers;
	int currentPlayer;
	public Game(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		this.currentPlayer = 0;
	}
		
	 public void playGame() {
		start(); 
	 while(!haveWinner()) {
		 takeTurn();
		 System.out.println("winner of the game is "+gameWinningPlayer());
	 }
		
	}

	protected abstract void start();
	protected abstract String gameWinningPlayer();
	protected abstract boolean haveWinner();
	protected abstract void takeTurn();
}

class Chess extends Game{
	
	private int maxTurns;
	private int turnsTaken;
	
	public Chess(int numberOfPlayers, int maxTurns) {
		super(numberOfPlayers);
		this.maxTurns = maxTurns;
	}

	@Override
	protected void start() {
		System.out.println("Starting a game of chess");
		
	}

	@Override
	protected String gameWinningPlayer() {
		if(turnsTaken >= maxTurns)
			return "Player 1";
		return "No winner yet";
	}

	@Override
	protected boolean haveWinner() {
		return turnsTaken >= maxTurns;
	}

	@Override
	protected void takeTurn() {
		turnsTaken++;
		System.out.println("Player takes a turn in chess");
		
	}
}
/**
 * Abstract class defining the template method and the steps of the algorithm
 */

	abstract class DataProcessor {
	    // Template method
	    public final void process() {
	        readData();
	        processData();
	        writeData();
	    }
	    
	    protected abstract void readData();
	    protected abstract void processData();
	    protected abstract void writeData();
	}
	
	/**
	 * Concrete implementation of DataProcessor for processing CSV data
	 */
	class CSVDataProcessor extends DataProcessor {
		@Override
		protected void readData() {
			System.out.println("Reading data from CSV file");
		}

		@Override
		protected void processData() {
			System.out.println("Processing CSV data");
		}

		@Override
		protected void writeData() {
			System.out.println("Writing processed data to CSV file");
		}
	}
	
	class JSONDataProcessor extends DataProcessor {
		@Override
		protected void readData() {
			System.out.println("Reading data from JSON file");
		}

		@Override
		protected void processData() {
			System.out.println("Processing JSON data");
		}

		@Override
		protected void writeData() {
			System.out.println("Writing processed data to JSON file");
		}
	}
	  
