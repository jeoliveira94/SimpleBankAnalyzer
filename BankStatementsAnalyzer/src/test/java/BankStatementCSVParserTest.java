import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class BankStatementCSVParserTest {
    private final BankStatementParser statementParser = new BankStatementCSVParser();

    @Test
    public void shouldParseOneCorrectLine() throws Exception {
        final String line = "18-07-2023,-50,Tesco";
        final BankTransaction result = statementParser.parseFrom(line);
        final BankTransaction expected = new BankTransaction(
                LocalDate.of(2023, Month.JULY, 18),
                -50,
                "Tesco");

        final double tolerance = 0.0d;
        Assert.assertEquals(expected.getDate(), result.getDate());
        Assert.assertEquals(expected.getAmount(), result.getAmount(), tolerance);
        Assert.assertEquals(expected.getDescription(), result.getDescription());
    }

    @Test
    public void souldParserMutiplesLines() {
        final List<String> lines = List.of("18-07-2023,-50,Tesco", "15-06-2023,150,Salary", "01-02-2023,50,Freelance");
        final List<BankTransaction> result = statementParser.parseLinesFrom(lines);
        final List<BankTransaction> expected = List.of(
                new BankTransaction(
                        LocalDate.of(2023, Month.JULY, 18),
                        -50,
                        "Tesco"),
                new BankTransaction(
                        LocalDate.of(2023, Month.JUNE, 15),
                        150,
                        "Salary"),
                new BankTransaction(
                        LocalDate.of(2023, Month.FEBRUARY, 1),
                        50,
                        "Freelance")
        );
        Assert.assertEquals(expected, result);
    }
}
