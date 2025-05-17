package ru.light.tasksTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.light.tasks.EpicTask;

public class EpicTaskTest {

    @Test
    public void testEpicTasksEqualsWhenIdEquals() {
        EpicTask epicTask1 = new EpicTask("Эпик 1", "Описание 1", 1);
        EpicTask epicTask2 = new EpicTask("Эпик 2", "Описание 2", 1);

        Assertions.assertEquals(epicTask1, epicTask2);
    }

    @Test
    public void testEpicTasksNotEqualsWhenIdNotEqual() {
        EpicTask epicTask1 = new EpicTask("Эпик 1", "Описание 1", 1);
        EpicTask epicTask2 = new EpicTask("Эпик 1", "Описание 1", 2);

        Assertions.assertNotEquals(epicTask1, epicTask2);
    }
}
