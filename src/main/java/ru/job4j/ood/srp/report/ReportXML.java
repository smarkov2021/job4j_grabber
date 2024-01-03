package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import ru.job4j.ood.srp.store.Store;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class ReportXML implements Report {
    private  Marshaller marshaller;
    private Store store;

    public ReportXML() {
    }

    public ReportXML(Store store) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Employees.class);
        this.store = store;
        this.marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        var emp = new ArrayList<>(store.findBy(filter));
        Employees employees = new Employees(emp);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(employees, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return xml;
    }

    @XmlRootElement(name = "employees")
    public static class Employees {
        private List<Employee> employees;

        public Employees() {
        }
        public Employees(List<Employee> employees) {
            this.employees = employees;
        }

        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }
    }

    public static void main(String[] args) throws JAXBException {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee firstWorker = new Employee("Ivan", now, now, 100);
        Employee secondWorker = new Employee("Sergey", now, now, 300);
        Employee thirdWorker = new Employee("Pavel", now, now, 200);
        store.add(firstWorker);
        store.add(secondWorker);
        store.add(thirdWorker);
        ReportXML reportXML = new ReportXML(store);
        reportXML.generate(em -> true);
    }
}
