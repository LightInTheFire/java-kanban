package ru.light.taskmanagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.light.Managers;
import ru.light.tasks.EpicTask;
import ru.light.tasks.SubTask;
import ru.light.tasks.Task;
import ru.light.tasks.TaskStatus;

import java.util.List;

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

    @Test
    public void testTaskManagerAddingTasks() {
        Task task = new Task("Задача 1", "описание", null, TaskStatus.NEW);
        taskManager.addTask(task);

        List<Task> standardTasks = taskManager.getAllStandardTasks();
        Assertions.assertEquals(1, standardTasks.size());
    }

    @Test
    public void testTaskManagerAddingEpicTasks() {
        EpicTask epic = new EpicTask("Задача 1", "описание", null);
        taskManager.addTask(epic);

        List<EpicTask> standardTasks = taskManager.getAllEpicsTasks();
        Assertions.assertEquals(1, standardTasks.size());
        Assertions.assertEquals(epic, taskManager.getById(0));
    }

    @Test
    public void testAddingTaskWithId() {
        Task task = new Task("Задача 1", "описание", 123, TaskStatus.NEW);
        taskManager.addTask(task);

        Assertions.assertEquals(0, task.getId());
    }

    @Test
    public void testGetTaskWithId() {
        Task task1 = new Task("Задача 1", "описание", null, TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "описание", null, TaskStatus.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Assertions.assertEquals(task2, taskManager.getById(1));
    }
}