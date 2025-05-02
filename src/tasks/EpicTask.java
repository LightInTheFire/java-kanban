package tasks;

import java.util.ArrayList;
import java.util.List;

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

    public void calculateEpicStatus() {
        if (subTasks.size() == 0) {
            setStatus(TaskStatus.NEW);
            return;
        }
        boolean isAllSubTasksDone = true;
        for (SubTask subTask : subTasks) {
            if (subTask.getStatus() != TaskStatus.DONE) {
                isAllSubTasksDone = false;
                break;
            }
        }
        if (isAllSubTasksDone) {
            setStatus(TaskStatus.DONE);
        } else {
            setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
