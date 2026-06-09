package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;
class OperationFactory {

    public static BankingOperation getOperation(OperationType type) {

        return switch (type) {

            case DEPOSIT -> new DepositOperation();

            case WITHDRAW -> new WithdrawOperation();

            case CHECK_BALANCE -> new CheckBalanceOperation();
        };
    }
}