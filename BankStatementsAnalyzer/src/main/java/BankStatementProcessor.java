import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double calculateTotalAmount() {
        double total = 0;
        for(final BankTransaction bankTransaction: bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }

    public double calculateTotalInMonth(final Month month) {
        double total = 0;
        for(final BankTransaction bankTransaction: bankTransactions) {
            if(bankTransaction.getDate().getMonth() == month) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
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
