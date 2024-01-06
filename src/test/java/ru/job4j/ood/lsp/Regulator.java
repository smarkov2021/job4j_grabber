package ru.job4j.ood.lsp;

class Regulator {
    private int value;
    public  Regulator() {
    }

    public Regulator(int value) {
        this.value = value;
    }

    public void change(int change) {
        this.value = this.value + change;
    }
}

class SoundRegulator extends Regulator {
    private int value;

    public SoundRegulator() {
        super();
    }

    public SoundRegulator(int value) {
        super(value);

    }

    public void change(int change) {
        int newValue = this.value + change;
        if (value < 0) {
        throw new IllegalArgumentException("Value of sound can't be less than zero!");
        }
        this.value = newValue;
    }
}