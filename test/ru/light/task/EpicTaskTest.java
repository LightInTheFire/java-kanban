package ru.light.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class EpicTaskTest {

    @Test
    void testEpicTasksEqualsWhenIdEquals() {
        EpicTask epicTask1 = new EpicTask("Эпик 1", "Описание 1", 1);
        EpicTask epicTask2 = new EpicTask("Эпик 2", "Описание 2", 1);

        Assertions.assertEquals(epicTask1, epicTask2);
    }

    @Test
    void testEpicTasksNotEqualsWhenIdNotEqual() {
        EpicTask epicTask1 = new EpicTask("Эпик 1", "Описание 1", 1);
        EpicTask epicTask2 = new EpicTask("Эпик 1", "Описание 1", 2);

        Assertions.assertNotEquals(epicTask1, epicTask2);
    }

    @Test
    public void checkCalculationOfInnerState() {
        EpicTask epicTask = new EpicTask("Эпик 1", "Описание 1", 1);
        SubTask subTask1 = new SubTask("Подзадача 1",
                "Описание 1",
                2,
                TaskStatus.DONE,
                1,
                Duration.ofHours(2),
                LocalDateTime.of(2020, 1,1,1, 1, 1));
        SubTask subTask2 = new SubTask("Подзадача 1",
                "Описание 1",
                3,
                TaskStatus.DONE,
                1,
                Duration.ofHours(3),
                LocalDateTime.of(2015, 1,1,1, 1, 1));
        epicTask.addSubTask(subTask1);
        epicTask.addSubTask(subTask2);
        Duration expectedDuration = Duration.ofHours(2).plus(Duration.ofHours(3));
        LocalDateTime expectedStartTime = LocalDateTime.of(2015, 1,1,1, 1, 1);
        LocalDateTime expectedEndTime = LocalDateTime.of(2020, 1,1,1, 1, 1)
                .plus(Duration.ofHours(2));
        Assertions.assertEquals(expectedDuration, epicTask.getDuration());
        Assertions.assertEquals(expectedEndTime, epicTask.getEndTime());
        Assertions.assertEquals(expectedStartTime, epicTask.getStartTime());
        Assertions.assertEquals(TaskStatus.DONE, epicTask.getStatus());
    }
}
