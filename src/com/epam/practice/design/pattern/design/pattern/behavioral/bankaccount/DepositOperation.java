package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;
class DepositOperation implements BankingOperation {

    @Override
    public void execute(BankAccount account, double amount) {

        account.deposit(amount);

        System.out.println("Deposited: " + amount);
    }
}