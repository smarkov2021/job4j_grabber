package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import ru.job4j.ood.srp.store.Store;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
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
        List<ParsedEmployee> newEmpl = new ArrayList<>();
        for (Employee em : store.findBy(filter)) {
            newEmpl.add(new ParsedEmployee(em.getName(), em.getHired(), em.getFired(), em.getSalary()));
        }
        Employees employees = new Employees(newEmpl);
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

    public static class ParsedEmployee {
        private String name;
        private Calendar hired;
        private Calendar fired;
        private double salary;

        public ParsedEmployee() {
        }
        public ParsedEmployee(String name, Calendar hired, Calendar fired, double salary) {
            this.name = name;
            this.hired = hired;
            this.fired = fired;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Calendar getHired() {
            return hired;
        }

        public void setHired(Calendar hired) {
            this.hired = hired;
        }

        public Calendar getFired() {
            return fired;
        }

        public void setFired(Calendar fired) {
            this.fired = fired;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }
    }

    @XmlRootElement(name = "employees")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Employees {
        @XmlElement(name = "employee")
        private List<ParsedEmployee> employees;

        public Employees() {
        }
        public Employees(List<ParsedEmployee> employees1) {
            this.employees = employees1;
        }

        public List<ParsedEmployee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<ParsedEmployee> employees) {
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
