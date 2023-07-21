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

    public double findMaxTransactionInRange(final Month startMonth, final Month endMonth) {
        double maximumTransaction = Double.MIN_VALUE;

        final List<BankTransaction> bankTransactionsInRange = findTransactions(new FindTransactionInMonthRange(startMonth, endMonth));
        for(final BankTransaction bankTransaction: bankTransactionsInRange) {
            if(bankTransaction.getAmount() > maximumTransaction) maximumTransaction = bankTransaction.getAmount();
        }
        return maximumTransaction;
    }

    public double findMinTransactionInRange(final Month startMonth, final Month endMonth) {
        double minimumTransaction = Double.MAX_VALUE;
        final List<BankTransaction> bankTransactionsInRange = findTransactions(new FindTransactionInMonthRange(startMonth, endMonth));
        for(final BankTransaction bankTransaction: bankTransactionsInRange) {
            if(bankTransaction.getAmount() < minimumTransaction) minimumTransaction = bankTransaction.getAmount();
        }
        return minimumTransaction;
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
