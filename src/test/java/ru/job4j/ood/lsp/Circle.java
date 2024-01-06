package ru.job4j.ood.lsp;

public class Circle extends Polygon {
    public Circle() {
        super();
    }
    public Circle(double angle, double side) {
        throw new IllegalArgumentException("You can't use it!");
    }

    public Circle(double square) {
        super(square);
    }
}
