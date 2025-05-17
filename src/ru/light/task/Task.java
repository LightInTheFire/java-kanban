package ru.light.task;

public final class Task extends BaseTask {

    public Task(String title,
                String description,
                Integer id,
                TaskStatus status) {
        super(title, description, id, status);
    }

}
