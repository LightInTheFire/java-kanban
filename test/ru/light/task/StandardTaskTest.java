package ru.light.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StandardTaskTest {

    @Test
    public void testStandardTasksEqualsWhenIdEquals() {
        Task task1 = new Task("Задача 1", "описание ", 1, TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "описание 2123", 1, TaskStatus.DONE);

        Assertions.assertEquals(task1, task2);
    }

    @Test
    public void testStandardTasksNotEqualsWhenIdNotEqual() {
        Task task1 = new Task("Задача 1", "описание ", 1, TaskStatus.NEW);
        Task task2 = new Task("Задача 1", "описание ", 2, TaskStatus.NEW);

        Assertions.assertNotEquals(task1, task2);
    }


}
