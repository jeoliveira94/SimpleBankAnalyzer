package com.ruemmai;

import com.ruemmai.exporters.HtmlExporter;
import com.ruemmai.models.BankTransaction;
import com.ruemmai.models.SummaryStatistics;
import com.ruemmai.parsers.BankStatementCSVParser;
import com.ruemmai.parsers.BankStatementParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;

public class BankStatementAnalyzer {
    private static final String RESOURCES = "src/main/resources/";
    final HtmlExporter htmlExporter = new HtmlExporter();

    public static void main(final String... args) throws IOException {
        final BankStatementAnalyzer bankStatementAnalyzer = new BankStatementAnalyzer();
        final BankStatementParser bankStatementParser = new BankStatementCSVParser();

        bankStatementAnalyzer.analyze(args[0], bankStatementParser);
    }

    public void analyze(final String fileName, final BankStatementParser bankStatementParser) throws IOException {
        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);
        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);
        collectSummary(bankStatementProcessor);
    }

    private void collectSummary(final BankStatementProcessor bankStatementProcessor) {
        System.out.println("The total for all transactions is "
                + bankStatementProcessor.calculateTotalAmount());

        System.out.println("The total for transactions in January is "
                + bankStatementProcessor.calculateTotalInMonth(Month.JANUARY));

        System.out.println("The total for transactions in February is "
                + bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY));

        System.out.println("The total salary received is "
                + bankStatementProcessor.calculateTotalForCategory("Salary"));

        System.out.println("The maximum transaction between January and June is "
                + bankStatementProcessor.findMaxTransactionInRange(Month.JANUARY, Month.JUNE).getAmount());

        System.out.println("The minimum transaction between January and June is "
                + bankStatementProcessor.findMinTransactionInRange(Month.JANUARY, Month.JUNE).getAmount());

        System.out.println("The statistics exported to HTML \n"
                + htmlExporter.export(new SummaryStatistics(
                    bankStatementProcessor.calculateTotalAmount(),
                    bankStatementProcessor.calculateAverageAmount(),
                    bankStatementProcessor.findMinTransaction(b -> true).getAmount(),
                    bankStatementProcessor.findMaxTransaction(b -> true).getAmount())));
    }
}
