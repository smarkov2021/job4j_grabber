package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import ru.job4j.ood.srp.store.Store;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class ReportJSON  implements Report {
    private final Store store;
    private final Gson library;

    public ReportJSON(Store store) {
        this.store = store;
        this.library = new GsonBuilder().create();
    }

    public static class ParsedEmployee {
        private String name;
        private String hired;
        private String fired;
        private double salary;

        public ParsedEmployee() {
        }
        public ParsedEmployee(String name, Calendar hired, Calendar fired, double salary) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String hiredTime = dateFormat.format(hired.getTime());
            String firedTime = dateFormat.format(fired.getTime());
            this.name = name;
            this.hired = hiredTime;
            this.fired = firedTime;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHired() {
            return hired;
        }

        public void setHired(String hired) {
            this.hired = hired;
        }

        public String getFired() {
            return fired;
        }

        public void setFired(String fired) {
            this.fired = fired;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        List<ParsedEmployee> newEmpl = new ArrayList<>();
        for (Employee em : store.findBy(filter)) {
            newEmpl.add(new ParsedEmployee(em.getName(), em.getHired(), em.getFired(), em.getSalary()));
        }
        return library.toJson(newEmpl);
    }

    public static void main(String[] args) {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee firstWorker = new Employee("Ivan", now, now, 100);
        Employee secondWorker = new Employee("Sergey", now, now, 300);
        Employee thirdWorker = new Employee("Pavel", now, now, 200);
        store.add(firstWorker);
        store.add(secondWorker);
        store.add(thirdWorker);
        Report report = new ReportJSON(store);
        report.generate(em -> true);
    }
}
