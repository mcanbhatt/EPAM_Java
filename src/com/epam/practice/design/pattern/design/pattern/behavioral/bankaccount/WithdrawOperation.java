package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;
class WithdrawOperation implements BankingOperation {

    @Override
    public void execute(BankAccount account, double amount) {

        account.withdraw(amount);

        System.out.println("Withdrawn: " + amount);
    }
}