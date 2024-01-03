package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import ru.job4j.ood.srp.store.Store;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
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

    @Override
    public String generate(Predicate<Employee> filter) {
        List<Employee> emp = store.findBy(filter);
        System.out.print(library.toJson(emp));
        return library.toJson(emp);
    }

    public static void main(String[] args) {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        String formattedDate = dateFormat.format(now.getTime());
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
