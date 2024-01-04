package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import javax.xml.bind.JAXBException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReportJSONTest {
    @Test
    public void whenGenerate() throws JAXBException {
        MemStore store = new MemStore();
        Calendar newTime = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = dateFormat.format(newTime.getTime());
        Employee firstWorker = new Employee("Ivan", newTime, newTime, 100);
        Employee secondWorker = new Employee("Sergey", newTime, newTime, 300);
        store.add(firstWorker);
        store.add(secondWorker);
        Report engine = new ReportJSON(store);
        StringBuilder expect = new StringBuilder()
                .append("[{\"name\":\"Ivan\",\"hired\":\"")
                .append(formattedDate)
                .append("\",\"fired\":\"")
                .append(formattedDate)
                .append("\",\"salary\":100.0},{\"name\":\"Sergey\",\"hired\":\"")
                .append(formattedDate)
                .append("\",\"fired\":\"")
                .append(formattedDate)
                .append("\",\"salary\":300.0}]");
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}