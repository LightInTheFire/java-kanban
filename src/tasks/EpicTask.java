package tasks;

public record EpicTask(String title,
                       String description,
                       int id,
                       TaskStatus status)
        implements BaseTask {
}
