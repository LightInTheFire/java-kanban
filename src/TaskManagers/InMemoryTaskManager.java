package TaskManagers;

import HistoryManagers.HistoryManager;
import tasks.BaseTask;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskManager implements TaskManager {
    private static int idCounter = 0;
    private final Map<Integer, BaseTask> tasks;
    HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.tasks = new HashMap<>();
        this.historyManager = historyManager;
    }

    public Map<Integer, BaseTask> getTasks() {
        return tasks;
    }

    @Override
    public List<BaseTask> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public BaseTask getById(int id) {
        BaseTask task = tasks.get(id);
        historyManager.add(task);
        
        return task;
    }

    @Override
    public void removeById(int id) {
        BaseTask removedTask = tasks.remove(id);
        switch (removedTask) {
            case EpicTask epicTask -> {
                for (SubTask subTask : epicTask.getSubTasks()) {
                    tasks.remove(subTask.getId());
                }
            }
            case SubTask subTask -> {
                if (!(tasks.get(subTask.getEpicTaskId()) instanceof EpicTask epicTask)) {
                    throw new IllegalStateException("subtask with no epic in map");
                }
                epicTask.removeSubTask(subTask);
            }
            case Task ignored -> {}
        }
    }

    @Override
    public void addTask(BaseTask task) {
        switch (task) {
            case EpicTask ignored -> {}
            case SubTask subTask -> {
                if (!(tasks.get(subTask.getEpicTaskId()) instanceof EpicTask epicTask)) {
                    throw new IllegalArgumentException("no epic for subtask");
                }
                epicTask.addSubTask(subTask);
            }
            case Task ignored -> {}
        }
        int id = getNextId();

        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void updateTask(BaseTask task) {
        switch (task) {
            case EpicTask epicTask -> tasks.put(epicTask.getId(), epicTask);
            case SubTask subTask -> {
                if (!(tasks.get(subTask.getEpicTaskId()) instanceof EpicTask epicTaskOfSubtask)) {
                    throw new IllegalArgumentException();
                }
                epicTaskOfSubtask.addSubTask(subTask);
                tasks.put(subTask.getId(), subTask);
            }
            case Task standardTask -> tasks.put(standardTask.getId(), standardTask);
        }
    }

    @Override
    public Optional<List<SubTask>> getAllSubtasksOfEpic(int id) {
        return switch (tasks.get(id)) {
            case EpicTask epicTask -> Optional.of(epicTask.getSubTasks());
            case SubTask ignored -> Optional.empty();
            case Task ignored -> Optional.empty();
            case null -> Optional.empty();
        };
    }

    @Override
    public Map<Integer, EpicTask> getAllEpicsTasks() {
        return getTasksOfCertainType(EpicTask.class);
    }

    @Override
    public Map<Integer, Task> getAllStandartTasks() {
        return getTasksOfCertainType(Task.class);
    }

    @Override
    public Map<Integer, SubTask> getAllSubTasks() {
        return getTasksOfCertainType(SubTask.class);
    }

    @Override
    public void removeAllEpicsTasks() {
        removeAllTasksOfCertainType(EpicTask.class);
        removeAllTasksOfCertainType(SubTask.class);
    }

    @Override
    public void removeAllStandardTasks() {
        removeAllTasksOfCertainType(Task.class);
    }

    @Override
    public void removeAllSubTasks() {
        for (BaseTask value : tasks.values()) {
            switch (value) {
                case EpicTask epicTask -> epicTask.clearSubTasks();
                case SubTask ignored -> {}
                case Task ignored -> {}
            }
        }
        removeAllTasksOfCertainType(SubTask.class);
    }

    private int getNextId() {
        return idCounter++;
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
