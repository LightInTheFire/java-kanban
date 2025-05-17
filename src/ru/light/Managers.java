package ru.light;

import ru.light.HistoryManagers.HistoryManager;
import ru.light.HistoryManagers.InMemoryHistoryManager;
import ru.light.TaskManagers.InMemoryTaskManager;
import ru.light.TaskManagers.TaskManager;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
}
