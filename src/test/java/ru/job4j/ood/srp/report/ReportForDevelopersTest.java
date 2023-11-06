package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.job4j.ood.srp.currency.Currency;
import ru.job4j.ood.srp.currency.CurrencyConverter;
import ru.job4j.ood.srp.currency.InMemoryCurrencyConverter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReportForDevelopersTest {
    @Test
    public void whenOneUserGenerated(@TempDir Path folder) throws IOException {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(worker);
        File target = folder.resolve("target.csv").toFile();
        Report engine = new ReportForDevelopers(store, parser, target);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(worker.getName()).append(";")
                .append(parser.parse(worker.getHired())).append(";")
                .append(parser.parse(worker.getFired())).append(";")
                .append(worker.getSalary())
                .append(System.lineSeparator());
        engine.generate(em -> true);
        assertThat(Files.readString(target.toPath())).isEqualTo(expect.toString());
    }

    @Test
    public void whenTwoUserGenerated(@TempDir Path folder) throws IOException {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee firstWorker = new Employee("Ivan", now, now, 100);
        Employee secondWorker = new Employee("Sergey", now, now, 200);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(firstWorker);
        store.add(secondWorker);
        File target = folder.resolve("target.csv").toFile();
        Report engine = new ReportForDevelopers(store, parser, target);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(firstWorker.getName()).append(";")
                .append(parser.parse(firstWorker.getHired())).append(";")
                .append(parser.parse(firstWorker.getFired())).append(";")
                .append(firstWorker.getSalary())
                .append(System.lineSeparator())
                .append(secondWorker.getName()).append(";")
                .append(parser.parse(secondWorker.getHired())).append(";")
                .append(parser.parse(secondWorker.getFired())).append(";")
                .append(secondWorker.getSalary())
                .append(System.lineSeparator());
        engine.generate(em -> true);
        assertThat(Files.readString(target.toPath())).isEqualTo(expect.toString());
    }
}