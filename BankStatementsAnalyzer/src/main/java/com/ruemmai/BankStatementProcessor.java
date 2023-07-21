package com.ruemmai;

import com.ruemmai.filters.BankTransactionFilter;
import com.ruemmai.filters.FindTransactionInMonthRange;
import com.ruemmai.models.BankTransaction;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double summarizeTransactions(final BankTransactionSummarizer bankTransactionSummarizer) {
        double result = 0;
        for(final BankTransaction bankTransaction: bankTransactions) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction);
        }
        return result;
    }

    public double calculateTotalAmount() {
        return summarizeTransactions((acc, bankTransaction) -> acc + bankTransaction.getAmount());
    }

    public double calculateAverageAmount() {
        return calculateTotalAmount() / bankTransactions.size();
    }

    public double calculateTotalInMonth(final Month month) {
        return summarizeTransactions((acc, bankTransaction) ->
                        bankTransaction.getDate().getMonth() == month ? acc + bankTransaction.getAmount() : acc);
    }

    public double calculateTotalForCategory(final String category) {
        double total = 0;
        for(final BankTransaction bankTransaction: bankTransactions) {
            if(bankTransaction.getDescription().equals(category)) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction: bankTransactions) {
            if(bankTransactionFilter.test(bankTransaction)) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public BankTransaction findMinTransaction(final BankTransactionFilter bankTransactionFilter) {
        BankTransaction minimumTransaction = this.bankTransactions.get(0);
        final List<BankTransaction> bankTransactionsInRange = findTransactions(bankTransactionFilter);
        for(final BankTransaction bankTransaction: bankTransactionsInRange) {
            if(bankTransaction.getAmount() < minimumTransaction.getAmount()) minimumTransaction = bankTransaction;
        }
        return minimumTransaction;
    }

    public BankTransaction findMaxTransaction(final BankTransactionFilter bankTransactionFilter) {
        BankTransaction maximumTransaction = this.bankTransactions.get(0);
        final List<BankTransaction> bankTransactionsInRange = findTransactions(bankTransactionFilter);
        for(final BankTransaction bankTransaction: bankTransactionsInRange) {
            if(bankTransaction.getAmount() > maximumTransaction.getAmount()) maximumTransaction = bankTransaction;
        }
        return maximumTransaction;
    }

    public BankTransaction findMaxTransactionInRange(final Month startMonth, final Month endMonth) {
        return findMaxTransaction(new FindTransactionInMonthRange(startMonth, endMonth));
    }

    public BankTransaction findMinTransactionInRange(final Month startMonth, final Month endMonth) {
        return findMinTransaction(new FindTransactionInMonthRange(startMonth, endMonth));
    }

    public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount) {
        return findTransactions(bankTransaction -> bankTransaction.getAmount() >= amount);
    }

    public List<BankTransaction> findTransactionsInMonth(final Month month) {
        return findTransactions(bankTransaction -> bankTransaction.getDate().getMonth() == month);
    }

    public List<BankTransaction> findTransactionsInMonthAndGreater(final Month month, final int amount) {
        return findTransactions(bankTransaction -> bankTransaction.getDate().getMonth() == month
                && bankTransaction.getAmount() >= amount);
    }
}
