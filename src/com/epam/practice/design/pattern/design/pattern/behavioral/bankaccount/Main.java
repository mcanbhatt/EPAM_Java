package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;
public class Main {

    public static void main(String[] args) {

        BankAccount account = new BankAccount(1000);

        BankingOperation deposit =
                OperationFactory.getOperation(OperationType.DEPOSIT);

        deposit.execute(account, 500);

        BankingOperation withdraw =
                OperationFactory.getOperation(OperationType.WITHDRAW);

        withdraw.execute(account, 300);

        BankingOperation balance =
                OperationFactory.getOperation(OperationType.CHECK_BALANCE);

        balance.execute(account, 0);
    }
}