package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class ReportForHRTest {
    @Test
    public void whenThreeUsersGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee firstWorker = new Employee("Ivan", now, now, 100);
        Employee secondWorker = new Employee("Sergey", now, now, 300);
        Employee thirdWorker = new Employee("Pavel", now, now, 200);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(firstWorker);
        store.add(secondWorker);
        store.add(thirdWorker);
        Report engine = new ReportForHR(store, parser);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(secondWorker.getName()).append(" ")
                .append(secondWorker.getSalary())
                .append(System.lineSeparator())
                .append(thirdWorker.getName()).append(" ")
                .append(thirdWorker.getSalary())
                .append(System.lineSeparator())
                .append(firstWorker.getName()).append(" ")
                .append(firstWorker.getSalary())
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}