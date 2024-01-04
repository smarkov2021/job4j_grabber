package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import javax.xml.bind.JAXBException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class ReportXMLTest {
    @Test
    public void whenGenerate() throws JAXBException {
        MemStore store = new MemStore();
        Calendar newTime = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String formattedDate = dateFormat.format(newTime.getTime());
        Employee firstWorker = new Employee("Ivan", newTime, newTime, 100);
        Employee secondWorker = new Employee("Sergey", newTime, newTime, 300);
        store.add(firstWorker);
        store.add(secondWorker);
        Report engine = new ReportXML(store);
        StringBuilder expect = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n")
                .append("<employees>\n")
                .append("    <employee>\n")
                .append("        <fired>")
                .append(formattedDate)
                .append("</fired>\n")
                .append("        <hired>")
                .append(formattedDate)
                .append("</hired>\n")
                .append("        <name>Ivan</name>\n")
                .append("        <salary>100.0</salary>\n")
                .append("    </employee>\n")
                .append("    <employee>\n")
                .append("        <fired>")
                .append(formattedDate)
                .append("</fired>\n")
                .append("        <hired>")
                .append(formattedDate)
                .append("</hired>\n")
                .append("        <name>Sergey</name>\n")
                .append("        <salary>300.0</salary>\n")
                .append("    </employee>\n")
                .append("</employees>\n");
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}