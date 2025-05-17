package ru.light.managers.task;

import ru.light.managers.history.HistoryManager;
import ru.light.task.BaseTask;
import ru.light.task.EpicTask;
import ru.light.task.SubTask;
import ru.light.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskManager implements TaskManager {
    private int idCounter = 0;
    private final Map<Integer, BaseTask> tasks;
    HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.tasks = new HashMap<>();
        this.historyManager = historyManager;
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
    public List<EpicTask> getAllEpicsTasks() {
        return getTasksOfCertainType(EpicTask.class);
    }

    @Override
    public List<Task> getAllStandardTasks() {
        return getTasksOfCertainType(Task.class);
    }

    @Override
    public List<SubTask> getAllSubTasks() {
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

    private <T extends BaseTask> List<T> getTasksOfCertainType(Class<T> taskClass) {
        return tasks.values().stream()
                .filter(taskClass::isInstance)
                .map(e -> (T) e)
                .toList();
    }

}
