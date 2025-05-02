import tasks.BaseTask;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private static int idCounter = 0;
    private final Map<Integer, BaseTask> tasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    public Map<Integer, BaseTask> getTasks() {
        return tasks;
    }

    public BaseTask getById(int id) {
        return tasks.get(id);
    }

    public void removeById(int id) {
        tasks.remove(id);
    }

    public void addTask(BaseTask task) {
        task.setId(idCounter);
        tasks.put(idCounter, task);

        idCounter++;
    }

    public void updateTask(BaseTask task) {
        switch (task) {
            case EpicTask epicTask -> {
                epicTask.calculateEpicStatus();
                tasks.put(epicTask.getId(), epicTask);
            }
            case SubTask subTask -> tasks.put(subTask.getId(), subTask);
            case Task standardTask -> tasks.put(task.getId(), standardTask);
        }
    }

    public List<SubTask> getAllSubtasksOfEpic(int id) {
        return switch (tasks.get(id)) {
            case EpicTask epicTask -> epicTask.getSubTasks();
            case SubTask subTask -> null;
            case Task task -> null;
        };
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

    public void removeAllStandardTasks() {
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
                map.put(task.getId(), (T) task);
            }
        }

        return map;
    }

}
