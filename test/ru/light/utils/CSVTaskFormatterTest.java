package ru.light.utils;

import org.junit.jupiter.api.Test;
import ru.light.task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVTaskFormatterTest {

    @Test
    void taskToCSVString() {
        Task task = new Task("Title", "Task Description", 3, TaskStatus.NEW);
        String csvString = CSVTaskFormatter.toCSVString(task);
        assertEquals("3,TASK,Title,NEW,Task Description, ", csvString);
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
        assertEquals("3,EPICTASK,Title,NEW,Epic Task Description, ", csvString);
    }

    @Test
    void taskFromCSVString() {
        Task task = new Task("Title", "Task Description", 3, TaskStatus.NEW);
        String csvString = CSVTaskFormatter.toCSVString(task);
        BaseTask taskFromCsv = CSVTaskFormatter.fromCSVString(csvString);
        assertEquals(task.getTitle(), taskFromCsv.getTitle());
        assertEquals(task.getDescription(), taskFromCsv.getDescription());
        assertEquals(task.getStatus(), taskFromCsv.getStatus());
        assertEquals(task.getId(), taskFromCsv.getId());
    }

    @Test
    void subTaskFromCSVString() {
        SubTask task = new SubTask("Title", "Task Description", 3, TaskStatus.NEW, 11);
        String csvString = CSVTaskFormatter.toCSVString(task);
        SubTask taskFromCsv = (SubTask) CSVTaskFormatter.fromCSVString(csvString);
        assertEquals(task.getTitle(), taskFromCsv.getTitle());
        assertEquals(task.getDescription(), taskFromCsv.getDescription());
        assertEquals(task.getStatus(), taskFromCsv.getStatus());
        assertEquals(task.getId(), taskFromCsv.getId());
        assertEquals(task.getEpicTaskId(), taskFromCsv.getEpicTaskId());
    }

    @Test
    void EpicTaskFromCSVString() {
        EpicTask task = new EpicTask("Title", "Epic Task Description", 3);
        String csvString = CSVTaskFormatter.toCSVString(task);
        BaseTask taskFromCsv = CSVTaskFormatter.fromCSVString(csvString);
        assertEquals(task.getTitle(), taskFromCsv.getTitle());
        assertEquals(task.getDescription(), taskFromCsv.getDescription());
        assertEquals(task.getStatus(), taskFromCsv.getStatus());
        assertEquals(task.getId(), taskFromCsv.getId());
        assertDoesNotThrow(() -> {
            EpicTask epic = (EpicTask) taskFromCsv;
        });
    }

    @Test
    void idStringToList() {
        String ids = "1,2,3,4";
        List<Integer> expectedIntegerList = List.of(1, 2, 3, 4);
        List<Integer> actualIntegerList = CSVTaskFormatter.idStringToList(ids);
        assertEquals(expectedIntegerList, actualIntegerList);
    }

    @Test
    void tasksToIdString() {
        Task task1 = new Task("Title", "Task Description", 2, TaskStatus.NEW);
        Task task2 = new Task("Title", "Task Description", 3, TaskStatus.NEW);
        Task task3 = new Task("Title", "Task Description", 4, TaskStatus.NEW);
        List<BaseTask> tasks = List.of(task1, task2, task3);

        String expectedString = "2,3,4";
        String actualString = CSVTaskFormatter.tasksToIdString(tasks);

        assertEquals(expectedString, actualString);
    }
}