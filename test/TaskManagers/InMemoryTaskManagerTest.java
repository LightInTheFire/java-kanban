package TaskManagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.light.Managers;
import ru.light.TaskManagers.TaskManager;
import ru.light.tasks.SubTask;
import ru.light.tasks.Task;
import ru.light.tasks.TaskStatus;

class InMemoryTaskManagerTest {
    private static TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void testCantAddSubTaskWithEpicIdAsSubtaskId() {
        SubTask subTask = new SubTask("Подзадача 1", "описание", null, TaskStatus.NEW, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                taskManager.addTask(subTask)
        );
    }

    @Test
    public void testCantAddSubTaskWithEpicIdEqualsNull() {
        SubTask subTask = new SubTask("Подзадача 1", "описание", null, TaskStatus.NEW, null);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                taskManager.addTask(subTask)
        );
    }

    @Test
    public void testTaskNotChangingWhenAdding() {
        Task task = new Task("Задача 1", "описание", null, TaskStatus.NEW);
        taskManager.addTask(task);
        Assertions.assertEquals(TaskStatus.NEW, task.getStatus());
        Assertions.assertEquals("Задача 1", task.getTitle());
        Assertions.assertEquals("описание", task.getDescription());
        Assertions.assertNotNull(task.getId());
    }
}