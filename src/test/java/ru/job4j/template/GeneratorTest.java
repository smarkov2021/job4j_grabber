package ru.job4j.template;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.tdd.Ticket3D;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Disabled("Тесты отключены. Удалить аннотацию после реализации всех методов по заданию.")
class GeneratorTest {
    @Test
    public void whenTwoValuesInSentence() {
        Generator generator = new FirstFenerator();
        Map<String, String> args = new HashMap<>();
        args.put("name", "Sergey");
        args.put("subject", "you");
        String template = "I am a ${name}, Who are ${subject}? ";
        String expected = "I am a Sergey, Who are you? ";
        assertThat(generator.produce(template, args)).isEqualTo(expected);
    }

    @Test
    public void whenOneValuesInSentence() {
        Generator generator = new FirstFenerator();
        Map<String, String> args = new HashMap<>();
        args.put("name", "Sergey");
        args.put("subject", "you");
        String template = "I am a ${name}, I am a ${name}";
        String expected = "I am a Sergey, I am a Sergey";
        assertThat(generator.produce(template, args)).isEqualTo(expected);
    }

    @Test
    public void whenCanFindOneValue() {
        Generator generator = new FirstFenerator();
        Map<String, String> args = new HashMap<>();
        args.put("name", "Sergey");
        args.put("subject", "you");
        String template = "I am a ${name}, Who are ${second}?";
        String expected = "I am a Sergey, Who are ?";
        assertThat(generator.produce(template, args)).isEqualTo(expected);
    }

    @Test
    public void whenArgsIsNotUsing() {
        Generator generator = new FirstFenerator();
        Map<String, String> args = new HashMap<>();
        String template = "Test test test";
        String expected = "Test test test";
        assertThat(generator.produce(template, args)).isEqualTo(expected);
    }

    @Test
    public void whenArgsIsNull() {
        Generator generator = new FirstFenerator();
        assertThatThrownBy(() -> generator.produce("test ${name}", null)).
                isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenTempIsNull() {
        Generator generator = new FirstFenerator();
        Map<String, String> args = new HashMap<>();
        args.put("name", "Sergey");
        args.put("subject", "you");
        assertThatThrownBy(() -> generator.produce(null, args)).
                isInstanceOf(IllegalArgumentException.class);
    }

}