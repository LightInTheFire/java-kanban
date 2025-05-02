import tasks.BaseTask;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private static int idCounter = 0;
    private final Map<Integer, BaseTask> tasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    public Map<Integer, EpicTask> getAllEpicsTasks() {
        return getTasksOfCertainType(EpicTask.class);
    }

    public Map<Integer, Task> getAllStandartTasks() {
        return getTasksOfCertainType(Task.class);
    }

    public Map<Integer, SubTask> getAllSubTasks() {
        return getTasksOfCertainType(SubTask.class);
    }

    public void removeAllEpicsTasks() {
        removeAllTasksOfCertainType(EpicTask.class);
    }

    public void removeAllStandartTasks() {
        removeAllTasksOfCertainType(Task.class);
    }

    public void removeAllSubTasks() {
        removeAllTasksOfCertainType(SubTask.class);
    }

    private <T extends BaseTask> void removeAllTasksOfCertainType(Class<T> taskClass) {
        tasks.entrySet()
                .removeIf(pair -> taskClass.isInstance(pair.getValue()));
    }

    private <T extends BaseTask> Map<Integer, T> getTasksOfCertainType(Class<T> taskClass) {
        Map<Integer, T> map = new HashMap<>();

        for (BaseTask task : tasks.values()) {
            if (taskClass.isInstance(task)) {
                map.put(task.id(), (T) task);
            }
        }

        return map;
    }

}
