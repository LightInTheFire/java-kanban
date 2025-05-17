import HistoryManagers.HistoryManager;
import HistoryManagers.InMemoryHistoryManager;
import TaskManagers.InMemoryTaskManager;
import TaskManagers.TaskManager;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
}
