package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.currency.Currency;
import ru.job4j.ood.srp.currency.CurrencyConverter;
import ru.job4j.ood.srp.currency.InMemoryCurrencyConverter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
class ReportForAccountingTest {
    @Test
    public void whenGenerateUSDSalary() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        CurrencyConverter currencyConverter = new InMemoryCurrencyConverter();
        Currency currency = Currency.USD;
        Employee firstWorker = new Employee("Ivan", now, now, 100);
        Employee secondWorker = new Employee("Sergey", now, now, 300);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(firstWorker);
        store.add(secondWorker);
        Report engine = new ReportForAccounting(store, parser, currencyConverter, currency);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(firstWorker.getName()).append(" ")
                .append(parser.parse(firstWorker.getHired())).append(" ")
                .append(parser.parse(firstWorker.getFired())).append(" ")
                .append(currencyConverter.convert(Currency.RUB, firstWorker.getSalary(), Currency.USD))
                .append(System.lineSeparator())
                .append(secondWorker.getName()).append(" ")
                .append(parser.parse(secondWorker.getHired())).append(" ")
                .append(parser.parse(secondWorker.getFired())).append(" ")
                .append(currencyConverter.convert(Currency.RUB, secondWorker.getSalary(), Currency.USD))
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

    @Test
    public void whenGenerateEURSalary() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        CurrencyConverter currencyConverter = new InMemoryCurrencyConverter();
        Currency currency = Currency.EUR;
        Employee firstWorker = new Employee("Ivan", now, now, 100);
        Employee secondWorker = new Employee("Sergey", now, now, 300);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(firstWorker);
        store.add(secondWorker);
        Report engine = new ReportForAccounting(store, parser, currencyConverter, currency);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(firstWorker.getName()).append(" ")
                .append(parser.parse(firstWorker.getHired())).append(" ")
                .append(parser.parse(firstWorker.getFired())).append(" ")
                .append(currencyConverter.convert(Currency.RUB, firstWorker.getSalary(), Currency.EUR))
                .append(System.lineSeparator())
                .append(secondWorker.getName()).append(" ")
                .append(parser.parse(secondWorker.getHired())).append(" ")
                .append(parser.parse(secondWorker.getFired())).append(" ")
                .append(currencyConverter.convert(Currency.RUB, secondWorker.getSalary(), Currency.EUR))
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

    @Test
    public void whenGenerateRUBSalary() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        CurrencyConverter currencyConverter = new InMemoryCurrencyConverter();
        Currency currency = Currency.RUB;
        Employee firstWorker = new Employee("Ivan", now, now, 100);
        Employee secondWorker = new Employee("Sergey", now, now, 300);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(firstWorker);
        store.add(secondWorker);
        Report engine = new ReportForAccounting(store, parser, currencyConverter, currency);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(firstWorker.getName()).append(" ")
                .append(parser.parse(firstWorker.getHired())).append(" ")
                .append(parser.parse(firstWorker.getFired())).append(" ")
                .append(currencyConverter.convert(Currency.RUB, firstWorker.getSalary(), Currency.RUB))
                .append(System.lineSeparator())
                .append(secondWorker.getName()).append(" ")
                .append(parser.parse(secondWorker.getHired())).append(" ")
                .append(parser.parse(secondWorker.getFired())).append(" ")
                .append(currencyConverter.convert(Currency.RUB, secondWorker.getSalary(), Currency.RUB))
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

}