package ru.light.managers.task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.light.managers.history.InMemoryHistoryManager;
import ru.light.task.EpicTask;
import ru.light.task.SubTask;
import ru.light.task.Task;
import ru.light.task.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest {

    static TaskManager taskManager;
    static File file;

    @BeforeAll
    static void setUp() throws IOException {
        file = File.createTempFile("data", "csv");
        taskManager = new FileBackedTaskManager(new InMemoryHistoryManager(), file);

        Task newTask = new Task("Title", "Task Description", null, TaskStatus.NEW,
                Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(newTask);
        EpicTask epic = new EpicTask("Epic Title", "Epic desc", null);
        taskManager.addTask(epic);
        SubTask subTask = new SubTask("Sub Title", "Sub desc", null,
                TaskStatus.DONE, epic.getId(), Duration.ofHours(1),
                LocalDateTime.of(2020, 1, 1, 1, 1));
        taskManager.addTask(subTask);

        taskManager.getById(1);
        taskManager.getById(2);
    }

    @Test
    void testLoadFromFile() {
        FileBackedTaskManager taskManagerFromFile = FileBackedTaskManager.loadFromFile(file);
        assertEquals(taskManager.getAllStandardTasks(), taskManagerFromFile.getAllStandardTasks());
        assertEquals(taskManager.getAllEpicsTasks(), taskManagerFromFile.getAllEpicsTasks());
        assertEquals(taskManager.getAllSubTasks(), taskManagerFromFile.getAllSubTasks());
        assertEquals(taskManager.getHistory(), taskManagerFromFile.getHistory());
    }
}