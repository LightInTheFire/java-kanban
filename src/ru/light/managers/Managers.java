package ru.light.managers;

import ru.light.managers.history.HistoryManager;
import ru.light.managers.history.InMemoryHistoryManager;
import ru.light.managers.task.FileBackedTaskManager;
import ru.light.managers.task.TaskManager;

import java.io.File;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new FileBackedTaskManager(getDefaultHistory(),
                new File("resources%sdata.csv".formatted(File.separator)));
    }
}
