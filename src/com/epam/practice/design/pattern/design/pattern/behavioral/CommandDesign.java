package com.epam.practice.design.pattern.design.pattern.behavioral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command Design Pattern is a behavioral design pattern that turns a request into a stand-alone object that contains 
 * all information about the request. This transformation allows you to parameterize methods with different requests, 
 * delay or queue a request's execution, and support undoable operations.
 * 
 * In the Command Design Pattern, there are typically four main components: invoke, command, receiver, and client. 
 * The client creates a command object and sets its receiver. 
 * The invoker holds the command and at some point asks the command to carry out a request by calling its execute() method. 
 * The receiver is the object that performs the actual work when the command's execute() method is called.
 */
public class CommandDesign {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<BankAccountCommand> commands = new ArrayList<>();
		BankAccount account = new BankAccount(100);
		BankAccountCommand depositCommand = new BankAccountCommand(account, "deposit", 50);
		BankAccountCommand withdrawCommand = new BankAccountCommand(account, "withdraw", 30);
		BankAccountCommand invalidCommand = new BankAccountCommand(account, "invalid", 20);
		BankAccountCommand overdrawCommand = new BankAccountCommand(account, "withdraw", 200);
		commands.add(depositCommand);
		commands.add(withdrawCommand);
		commands.add(invalidCommand);
		commands.add(overdrawCommand);
		
		for(BankAccountCommand command : commands) {
			command.execute();
		}
		
	/*	depositCommand.execute(); // Deposited: 50, Current Balance: 150
		withdrawCommand.execute(); // Withdrew: 30, Current Balance: 120
		invalidCommand.execute(); // Invalid action
		overdrawCommand.execute(); // Insufficient funds. Current Balance: 120 */
		
		System.out.println("Undoing last command... \n");
		
		Collections.reverse(commands);		
		
		for(BankAccountCommand command : commands) {
			command.undo();
		}
	}
}


class BankAccount{	
	int balance;
	 
	public BankAccount(int balance) {
		this.balance = balance;
	}
	
	public void deposit(int amount) {
		balance += amount;
		System.out.println("Deposited: "+amount+", Current Balance: "+balance);
	}
	
	public boolean withdraw(int amount) {
		if(balance >= amount) {
			balance -= amount;
			System.out.println("Withdrew: "+amount+", Current Balance: "+balance);
			return true;
		} else {
			System.out.println("Insufficient funds. Current Balance: "+balance);
			return false;
		}
	}
}

interface command{	
	void execute();
	void undo(); // for undoable operations, not implemented in this example
}


class BankAccountCommand implements command{
	
	private BankAccount account;
	private String action;
	private int amount;
	
	private boolean succeeded;
	
	public BankAccountCommand(BankAccount account, String action, int amount) {
		this.account = account;
		this.action = action;
		this.amount = amount;
	}
	
	@Override
	public void execute() {
		switch(action.toLowerCase()) {
			case "deposit":
				account.deposit(amount);
				succeeded = true;
				break;
			case "withdraw":
				succeeded = account.withdraw(amount);
				break;
			default:
				System.out.println("Invalid action");
		}
	}

	@Override
	public void undo() {
		
		if(!succeeded) {
			System.out.println("Cannot undo failed operation");
			return;
		}		
		switch(action.toLowerCase()) {
		case "withdraw":
			account.deposit(amount);
			break;
		case "deposit":
			account.withdraw(amount);
			break;
		default:
			System.out.println("Invalid action");
	}
		
	}
}