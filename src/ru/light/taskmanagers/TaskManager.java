package ru.light.taskmanagers;

import ru.light.tasks.BaseTask;
import ru.light.tasks.EpicTask;
import ru.light.tasks.SubTask;
import ru.light.tasks.Task;

import java.util.List;
import java.util.Optional;

public interface TaskManager {
    List<BaseTask> getHistory();

    BaseTask getById(int id);

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
