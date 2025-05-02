package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EpicTask extends BaseTask {
    private List<SubTask> subTasks;

    public EpicTask(String title, String description, Integer id, TaskStatus status) {
        super(title, description, id, status);
        this.subTasks = new ArrayList<>();
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public void  removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }
}
