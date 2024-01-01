package ru.job4j.ood.ocp;

public class Laptop {
    private String name;
    private String system;

    public Laptop(String name, String system) {
        this.name = name;
        this.system = system;
    }

    public void changeSystem(String newSystem) {
        this.system = newSystem;
    }
}
