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
    void testHistoryManagerUpdatesTasksWhenOverfilled() {
        Task task1 = new Task("Задача 1", "описание", 1, TaskStatus.DONE);
        Task task2 = new Task("Задача 2", "описание", 2, TaskStatus.DONE);
        Task task3 = new Task("Задача 3", "описание", 3, TaskStatus.DONE);
        Task task4 = new Task("Задача 4", "описание", 4, TaskStatus.DONE);
        Task task5 = new Task("Задача 5", "описание", 5, TaskStatus.DONE);
        Task task6 = new Task("Задача 6", "описание", 6, TaskStatus.DONE);
        Task task7 = new Task("Задача 7", "описание", 7, TaskStatus.DONE);
        Task task8 = new Task("Задача 8", "описание", 8, TaskStatus.DONE);
        Task task9 = new Task("Задача 9", "описание", 9, TaskStatus.DONE);
        Task task10 = new Task("Задача 10", "описание", 10, TaskStatus.DONE);
        Task task11 = new Task("Задача 11", "описание", 11, TaskStatus.DONE);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        historyManager.add(task5);
        historyManager.add(task6);
        historyManager.add(task7);
        historyManager.add(task8);
        historyManager.add(task9);
        historyManager.add(task10);
        historyManager.add(task11);

        List<BaseTask> history = historyManager.getHistory();
        Assertions.assertEquals(10, history.size());
        Assertions.assertEquals(task2, history.getFirst());
        Assertions.assertEquals(task11, history.getLast());
    }
}