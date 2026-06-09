package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;

class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {

        if (amount > balance) {
            throw new RuntimeException("Insufficient balance");
        }

        balance -= amount;
    }
}
