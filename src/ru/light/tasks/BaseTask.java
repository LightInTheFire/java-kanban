package ru.light.tasks;

import java.util.Objects;

public sealed abstract class BaseTask
        permits Task, SubTask, EpicTask {
    private String title;
    private String description;
    private Integer id;
    private TaskStatus status;

    public BaseTask(String title, String description, Integer id, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public BaseTask(BaseTask baseTask) {
        this.title = baseTask.title;
        this.description = baseTask.description;
        this.id = baseTask.id;
        this.status = baseTask.status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        BaseTask task = (BaseTask) o;
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task[" +
                "title=" + title + ", " +
                "description=" + description + ", " +
                "id=" + id + ", " +
                "status=" + status + ']';
    }
}
