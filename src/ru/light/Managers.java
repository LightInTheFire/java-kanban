package ru.light;

import ru.light.historymanagers.HistoryManager;
import ru.light.historymanagers.InMemoryHistoryManager;
import ru.light.taskmanagers.InMemoryTaskManager;
import ru.light.taskmanagers.TaskManager;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
}
