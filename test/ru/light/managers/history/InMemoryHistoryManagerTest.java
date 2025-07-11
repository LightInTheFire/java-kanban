package ru.light.managers.history;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.light.managers.Managers;
import ru.light.task.BaseTask;
import ru.light.task.Task;
import ru.light.task.TaskStatus;

import java.util.List;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void testHistoryManagerSavesTasks() {
        Task task1 = new Task("Задача 1", "описание", 12, TaskStatus.DONE);
        Task task2 = new Task("Задача 2", "описание", 14, TaskStatus.DONE);
        historyManager.add(task1);
        historyManager.add(task2);
        int actualSize = historyManager.getHistory().size();
        Assertions.assertEquals(2, actualSize);
    }

    @Test
    void testHistoryNotSavingNullTasks() {
        Task task1 = new Task("Задача 1", "описание", 12, TaskStatus.DONE);
        historyManager.add(task1);
        historyManager.add(null);
        int actualSize = historyManager.getHistory().size();
        Assertions.assertEquals(1, actualSize);
    }

    @Test
    void historyContainOnlyUniqueTasks() {
        Task task1 = new Task("Задача 1", "описание", 12, TaskStatus.DONE);
        Task task2 = new Task("Задача 2", "описание", 14, TaskStatus.DONE);
        Task updatedTask1 = new Task("Задача 1", "новое описание", 12, TaskStatus.IN_PROGRESS);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(updatedTask1);

        List<BaseTask> history = historyManager.getHistory();
        Task taskFromHistoryManager = (Task) history.get(1);
        Assertions.assertEquals(2, history.size());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskFromHistoryManager.getStatus());
        Assertions.assertEquals("новое описание", taskFromHistoryManager.getDescription());
        Assertions.assertEquals("Задача 1", taskFromHistoryManager.getTitle());
    }

    @Test
    void testHistoryRemovesTasks() {
        Task task1 = new Task("Задача 1", "описание", 12, TaskStatus.DONE);
        Task task2 = new Task("Задача 2", "описание", 14, TaskStatus.DONE);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(14);
        int actualSize = historyManager.getHistory().size();
        Assertions.assertEquals(1, actualSize);
    }
}