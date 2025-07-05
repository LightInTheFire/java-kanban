package ru.light.utils;

import org.junit.jupiter.api.Test;
import ru.light.task.EpicTask;
import ru.light.task.SubTask;
import ru.light.task.Task;
import ru.light.task.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class CSVTaskFormatterTest {

    @Test
    void taskToCSVString() {
        Task task = new Task("Title", "Task Description", 3, TaskStatus.NEW);
        String csvString = CSVTaskFormatter.toCSVString(task);
        assertEquals("3,TASK,Title,NEW,Task Description,", csvString);
    }

    @Test
    void subTaskToCSVString() {
        SubTask task = new SubTask("Title", "Task Description", 3, TaskStatus.NEW, 11);
        String csvString = CSVTaskFormatter.toCSVString(task);
        assertEquals("3,SUBTASK,Title,NEW,Task Description,11", csvString);
    }

    @Test
    void epicTaskToCSVString() {
        EpicTask task = new EpicTask("Title", "Epic Task Description", 3);
        String csvString = CSVTaskFormatter.toCSVString(task);
        assertEquals("3,EPICTASK,Title,NEW,Epic Task Description,", csvString);
    }

    @Test
    void fromCSVString() {
    }
}