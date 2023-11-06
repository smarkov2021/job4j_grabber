package ru.job4j.ood.srp.task;

import ru.job4j.ood.srp.task.Auto;

import java.util.List;

public class FirstAuto implements Auto {
    @Override
    public int getMaxSpeed() {
        return 160;
    }

    @Override
    public void addNoteAboutAuto(List<Auto> base) {
        base.add(this);
    }
}
