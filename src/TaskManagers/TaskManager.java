package TaskManagers;

import tasks.BaseTask;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

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

    List<Task> getAllStandartTasks();

    List<SubTask> getAllSubTasks();

    void removeAllEpicsTasks();

    void removeAllStandardTasks();

    void removeAllSubTasks();
}
