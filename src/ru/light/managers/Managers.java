package ru.light.managers;

import ru.light.managers.history.HistoryManager;
import ru.light.managers.history.InMemoryHistoryManager;
import ru.light.managers.task.InMemoryTaskManager;
import ru.light.managers.task.TaskManager;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
}
