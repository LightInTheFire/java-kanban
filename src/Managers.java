import HistoryManagers.InMemoryHistoryManager;
import TaskManagers.InMemoryTaskManager;
import TaskManagers.TaskManager;

public class Managers {
    TaskManager getDefault() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }
}
