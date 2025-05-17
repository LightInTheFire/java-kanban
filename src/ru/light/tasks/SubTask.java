package ru.light.tasks;

public final class SubTask extends BaseTask {
    Integer epicTaskId;

    public SubTask(String title,
                   String description,
                   Integer id,
                   TaskStatus status,
                   Integer epicTaskId) {
        super(title, description, id, status);
        this.epicTaskId = epicTaskId;
    }

    public Integer getEpicTaskId() {
        return epicTaskId;
    }

    public void setEpicTaskId(Integer epicTaskId) {
        this.epicTaskId = epicTaskId;
    }
}
