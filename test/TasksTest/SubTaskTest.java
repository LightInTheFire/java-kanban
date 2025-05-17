package TasksTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.light.tasks.SubTask;
import ru.light.tasks.TaskStatus;

public class SubTaskTest {

    @Test
    public void testSubTasksEqualsWhenIdEquals() {
        SubTask subTask1 = new SubTask("Эпик 1", "Описание 1", 1, TaskStatus.NEW, 2);
        SubTask subTask2 = new SubTask("Эпик 2", "Описание 2", 1, TaskStatus.DONE, 5);

        Assertions.assertEquals(subTask1, subTask2);
    }

    @Test
    public void testSubTasksNotEqualsWhenIdNotEqual() {
        SubTask subTask1 = new SubTask("Эпик 1", "Описание 1", 1, TaskStatus.NEW, 2);
        SubTask subTask2 = new SubTask("Эпик 1", "Описание 1", 2, TaskStatus.NEW, 2);

        Assertions.assertNotEquals(subTask1, subTask2);
    }
}
