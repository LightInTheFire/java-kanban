import tasks.BaseTask;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private static int idCounter = 0;
    private Map<Integer, BaseTask> tasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    public Map<Integer, EpicTask> getAllEpicsTasks() {
        return getTasks(EpicTask.class);
    }

    public Map<Integer, Task> getAllStandartTasks() {
        return getTasks(Task.class);
    }

    public Map<Integer, SubTask> getAllSubTasks() {
        return getTasks(SubTask.class);
    }

    private <T extends BaseTask> Map<Integer, T> getTasks(Class<T> taskClass) {
        Map<Integer, T> map = new HashMap<>();

        for (BaseTask task : tasks.values()) {
            if (taskClass.isInstance(task)) {
                map.put(task.id(), (T) task);
            }
        }

        return map;
    }
}
