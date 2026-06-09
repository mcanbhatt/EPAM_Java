package com.epam.practice.design.pattern.design.pattern.behavioral.bankaccount;

import java.util.HashMap;
import java.util.Map;

class OperationRegistry {

    private static final Map<String, BankingOperation> operations
            = new HashMap<>();

    static {

        operations.put("DEPOSIT",
                new DepositOperation());

        operations.put("WITHDRAW",
                new WithdrawOperation());

        operations.put("BALANCE",
                new CheckBalanceOperation());
    }

    public static BankingOperation get(String type) {
        return operations.get(type);
    }

    public static void register(
            String type,
            BankingOperation operation) {

        operations.put(type, operation);
    }
}