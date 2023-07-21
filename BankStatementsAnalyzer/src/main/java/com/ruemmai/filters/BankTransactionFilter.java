package com.ruemmai.filters;

import com.ruemmai.models.BankTransaction;

@FunctionalInterface
public interface BankTransactionFilter {
    boolean test(BankTransaction bankTransaction);
}
