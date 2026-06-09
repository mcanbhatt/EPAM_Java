package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;
class TransferOperation implements BankingOperation {

    private BankAccount targetAccount;

    public TransferOperation(BankAccount targetAccount) {
        this.targetAccount = targetAccount;
    }

    @Override
    public void execute(BankAccount sourceAccount, double amount) {

        sourceAccount.withdraw(amount);

        targetAccount.deposit(amount);

        System.out.println("Transferred: " + amount);
    }
}