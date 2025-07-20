package ru.light.managers.task;

import ru.light.task.BaseTask;
import ru.light.task.EpicTask;
import ru.light.task.SubTask;
import ru.light.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskManager {
    List<BaseTask> getHistory();

    Optional<BaseTask> getById(int id);

    void removeById(int id);

    void addTask(BaseTask task);

    void updateTask(BaseTask task);

    Optional<List<SubTask>> getAllSubtasksOfEpic(int id);

    List<EpicTask> getAllEpicsTasks();

    List<Task> getAllStandardTasks();

    List<SubTask> getAllSubTasks();

    void removeAllEpicsTasks();

    void removeAllStandardTasks();

    void removeAllSubTasks();
}
