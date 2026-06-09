package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;
class CheckBalanceOperation implements BankingOperation {

    @Override
    public void execute(BankAccount account, double amount) {

        System.out.println("Balance: " + account.getBalance());
    }
}