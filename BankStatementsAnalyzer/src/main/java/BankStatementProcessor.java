import java.time.LocalDate;
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

    public double findMaxTransactionInRange(final Month startMonth, final Month endMonth) {
        double maximumTransacion = Double.MIN_VALUE;
        for(final BankTransaction bankTransaction: bankTransactions) {
            final Month transactionMonth = bankTransaction.getDate().getMonth();
            if(transactionMonth.compareTo(startMonth) >= 0 && transactionMonth.compareTo(endMonth) <= 0) {
                if(bankTransaction.getAmount() > maximumTransacion) maximumTransacion = bankTransaction.getAmount();
            }
        }

        return maximumTransacion;
    }

    public double findMinTransactionInRange(final Month startMonth, final Month endMonth) {
        double minimumTransacion = Double.MAX_VALUE;
        for(final BankTransaction bankTransaction: bankTransactions) {
            final Month transactionMonth = bankTransaction.getDate().getMonth();
            if(transactionMonth.compareTo(startMonth) >= 0 && transactionMonth.compareTo(endMonth) <= 0) {
                if(bankTransaction.getAmount() < minimumTransacion) minimumTransacion = bankTransaction.getAmount();
            }
        }

        return minimumTransacion;
    }

    public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount) {
        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction: bankTransactions) {
            if(bankTransaction.getAmount() >= amount) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public List<BankTransaction> findTransactionsInMonth(final Month month) {
        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction: bankTransactions) {
            if(bankTransaction.getDate().getMonth() == month) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public List<BankTransaction> findTransactionsInMonthAndGreater(final Month month,
                                                                   final int amount) {
        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction: bankTransactions) {
            if(bankTransaction.getDate().getMonth() == month && bankTransaction.getAmount() >= amount) {
                result.add(bankTransaction);
            }
        }
        return result;
    }
}
