package TasksTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

public class StandardTaskTest {

    @Test
    public void StandardTasksEqualsWhenIdEquals() {
        Task task1 = new Task("Задача 1", "описание ", 1, TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "описание 2123", 1, TaskStatus.DONE);

        Assertions.assertEquals(task1, task2);
    }

    @Test
    public void StandardTasksNotEqualsWhenIdNotEqual() {
        Task task1 = new Task("Задача 1", "описание ", 1, TaskStatus.NEW);
        Task task2 = new Task("Задача 1", "описание ", 2, TaskStatus.NEW);

        Assertions.assertNotEquals(task1, task2);
    }


}
