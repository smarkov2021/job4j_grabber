package ru.job4j.ood.ocp;

public class File {
    private String name;
    private File file;

    public File(String name, File file) {
        this.name = name;
        this.file = this;
    }

    public void sendOnPrinter() {
        System.out.println("Содержимое файла отправлено на принтер.");
    };
}
