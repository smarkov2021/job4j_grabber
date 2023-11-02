package ru.job4j.kiss.fool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FoolTest {
    @Test
    public void whenSixCorrectValue() {
        Fool fool = new Fool();
        fool.init();
        var startAt = 1;
        assertThat(fool.go(startAt++, "1")).isTrue();
        assertThat(fool.go(startAt++, "2")).isTrue();
        assertThat(fool.go(startAt++, "Fizz")).isTrue();
        assertThat(fool.go(startAt++, "4")).isTrue();
        assertThat(fool.go(startAt++, "Buzz")).isTrue();
        assertThat(fool.go(startAt++, "Fizz")).isTrue();
    }

    @Test
    public void whenTypeIncorrectValue() {
        Fool fool = new Fool();
        fool.init();
        var startAt = 1;
        assertThat(fool.go(startAt++, "eleven")).isFalse();
        assertThat(fool.go(startAt++, "3")).isFalse();
        assertThat(fool.go(startAt++, "Buzz")).isFalse();
        assertThat(fool.go(startAt++, "Buzz")).isFalse();
        assertThat(fool.go(startAt++, "Fizz")).isFalse();
        assertThat(fool.go(startAt++, "12342")).isFalse();
        assertThat(fool.go(startAt++, "0")).isFalse();
        assertThat(fool.go(startAt++, "test")).isFalse();
    }

    @Test
    public void whenResetStartPos() {
        Fool fool = new Fool();
        fool.init();
        var startAt = 1;
        fool.go(startAt++, null);
        startAt = fool.go(startAt++, "2") ? startAt : 1;
        assertThat(startAt).isEqualTo(3);
        fool.go(startAt++, null);
        startAt = fool.go(startAt++, "Fizz") ? startAt : 1;
        assertThat(startAt).isEqualTo(1);
    }
}