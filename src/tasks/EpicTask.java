package tasks;

import java.util.ArrayList;
import java.util.List;

public final class EpicTask extends BaseTask {
    private final List<SubTask> subTasks;

    public EpicTask(String title, String description, Integer id, TaskStatus status) {
        super(title, description, id, status);
        this.subTasks = new ArrayList<>();
    }

    public void addSubTask(SubTask subTask) {
        if (subTasks.contains(subTask)) return;
        subTask.setEpicTaskId(this.getId());
        subTasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
    }

    public void removeSubTaskById(Integer id) {
        subTasks.removeIf(subTask -> subTask.getId().equals(id));
    }
    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public void setStatus(TaskStatus status) {
        calculateEpicStatus();
    }

    public void calculateEpicStatus() {
        if (subTasks.isEmpty()) {
            super.setStatus(TaskStatus.NEW);
            return;
        }
        boolean isAllSubTasksDone = true;
        boolean isAllSubTasksNew = true;
        for (SubTask subTask : subTasks) {
            if (subTask.getStatus() != TaskStatus.DONE) {
                isAllSubTasksDone = false;
            }
            if (subTask.getStatus() != TaskStatus.NEW) {
                isAllSubTasksNew = false;
            }
        }
        if (isAllSubTasksNew) {
            super.setStatus(TaskStatus.NEW);
        } else if (isAllSubTasksDone) {
            super.setStatus(TaskStatus.DONE);
        } else {
            super.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
