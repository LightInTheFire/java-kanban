package tasks;

public record SubTask(String title,
                      String description,
                      int id,
                      TaskStatus status)
        implements BaseTask {
}
