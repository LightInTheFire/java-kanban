package ru.light.tasks;

import java.util.ArrayList;
import java.util.List;

public final class EpicTask extends BaseTask {
    private final List<SubTask> subTasks;

    public EpicTask(String title, String description, Integer id) {
        super(title, description, id, TaskStatus.NEW);
        this.subTasks = new ArrayList<>();
        calculateEpicStatus();
    }

    public void addSubTask(SubTask subTask) {
        int i = subTasks.indexOf(subTask);
        if (i != -1) {
            subTasks.set(i, subTask);
        } else {
            subTasks.add(subTask);
        }


        calculateEpicStatus();
    }

    public void clearSubTasks() {
        subTasks.clear();
        calculateEpicStatus();
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);

        calculateEpicStatus();
    }

    public void removeSubTaskById(Integer id) {
        subTasks.removeIf(subTask -> subTask.getId().equals(id));

        calculateEpicStatus();
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public void setStatus(TaskStatus status) {
        calculateEpicStatus();
    }

    private void calculateEpicStatus() {
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
