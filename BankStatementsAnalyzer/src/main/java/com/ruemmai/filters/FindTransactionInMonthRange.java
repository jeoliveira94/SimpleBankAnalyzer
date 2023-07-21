package com.ruemmai.filters;

import com.ruemmai.models.BankTransaction;

import java.time.Month;

public class FindTransactionInMonthRange implements BankTransactionFilter{
    private final Month startMonth;
    private final Month endMonth;

    public FindTransactionInMonthRange(Month startMonth, Month endMonth) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }

    @Override
    public boolean test(BankTransaction bankTransaction) {
        final Month transactionMonth = bankTransaction.getDate().getMonth();
        return transactionMonth.compareTo(this.startMonth) >= 0
                && transactionMonth.compareTo(this.endMonth) <= 0;
    }
}
