package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.function.Predicate;

public class ReportForDevelopers implements Report {
    private final Store store;
    private final DateTimeParser<Calendar> dateTimeParser;

    private final File target;
//
    public ReportForDevelopers(Store store, DateTimeParser<Calendar> dateTimeParser, File target) {
        this.store = store;
        this.dateTimeParser = dateTimeParser;
        this.target = target;
    }



    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator());

        for (Employee employee : store.findBy(filter)) {

            text.append(employee.getName()).append(";")
                    .append(dateTimeParser.parse(employee.getHired())).append(";")
                    .append(dateTimeParser.parse(employee.getFired())).append(";")
                    .append(employee.getSalary())
                    .append(System.lineSeparator());
        }
        try {
            Files.writeString(target.toPath(), text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text.toString();
    }
}
