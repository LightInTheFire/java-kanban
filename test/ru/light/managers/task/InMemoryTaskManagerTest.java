package ru.light.managers.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.light.managers.history.InMemoryHistoryManager;
import ru.light.task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

class InMemoryTaskManagerTest {
    private static TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    @Test
    public void testCantAddSubTaskWithEpicIdAsSubtaskId() {
        SubTask subTask = new SubTask("Подзадача 1", "описание",
                null, TaskStatus.NEW, 0, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                taskManager.addTask(subTask)
        );
    }

    @Test
    public void testCantAddSubTaskWithEpicIdEqualsNull() {
        SubTask subTask = new SubTask("Подзадача 1", "описание",
                null, TaskStatus.NEW, null, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                taskManager.addTask(subTask)
        );
    }

    @Test
    public void testTaskNotChangingWhenAdding() {
        Task task = new Task("Задача 1", "описание", null, TaskStatus.NEW,
                Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(task);
        Assertions.assertEquals(TaskStatus.NEW, task.getStatus());
        Assertions.assertEquals("Задача 1", task.getTitle());
        Assertions.assertEquals("описание", task.getDescription());
        Assertions.assertEquals(Duration.ofHours(1), task.getDuration());
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), task.getStartTime());
        Assertions.assertNotNull(task.getId());
    }

    @Test
    public void testTaskManagerAddingTasks() {
        Task task = new Task("Задача 1", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
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
        Assertions.assertEquals(epic, taskManager.getById(0).get());
    }

    @Test
    public void testAddingTaskWithId() {
        Task task = new Task("Задача 1", "описание", 123, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(task);

        Assertions.assertEquals(0, task.getId());
    }

    @Test
    public void testGetTaskWithId() {
        Task task1 = new Task("Задача 1", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        Task task2 = new Task("Задача 2", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Assertions.assertEquals(task2, taskManager.getById(1).get());
    }

    @Test
    public void getThrowsWithIncorrectIdPassed() {
        Task task1 = new Task("Задача 1", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(task1);

        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> taskManager.removeById(123));
        Assertions.assertEquals(1, taskManager.getAllStandardTasks().size());
    }

    @Test
    public void testShouldAddTasksToHistory() {
        Task task1 = new Task("Задача 1", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        Task task2 = new Task("Задача 2", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.getById(task1.getId());
        taskManager.getById(task2.getId());
        List<BaseTask> history = taskManager.getHistory();

        Assertions.assertEquals(2, history.size());
    }
    @Test
    public void testShouldRemoveTasksFromHistory() {
        Task task1 = new Task("Задача 1", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        Task task2 = new Task("Задача 2", "описание", null, TaskStatus.NEW, Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.getById(task1.getId());
        taskManager.getById(task2.getId());
        taskManager.removeById(task2.getId());
        List<BaseTask> history = taskManager.getHistory();
        Assertions.assertEquals(1, history.size());
    }
}