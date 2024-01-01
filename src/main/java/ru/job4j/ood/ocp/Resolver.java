package ru.job4j.ood.ocp;

public class Resolver {
    private double firstCoef;
    private double secondCoef;
    private double thirdCoef;

    public Resolver(double firstCoef, double secondCoef, double thirdCoef) {
        this.firstCoef = firstCoef;
        this.secondCoef = secondCoef;
        this.thirdCoef = thirdCoef;
    }

    public void resolve() {
        double d = secondCoef * secondCoef - 4 * firstCoef * thirdCoef;
        if (d > 0) {
            double x1, x2;
            x1 = (-secondCoef - Math.sqrt(d)) / (2 * firstCoef);
            x2 = (-secondCoef + Math.sqrt(d)) / (2 * firstCoef);
            System.out.println("Корни уравнения: x1 = " + x1 + ", x2 = " + x2);
        } else if (d == 0) {
            double x;
            x = -secondCoef / (2 * firstCoef);
            System.out.println("Уравнение имеет единственный корень: x = " + x);
        } else {
            System.out.println("Уравнение не имеет действительных корней!");
        }
    }
}
