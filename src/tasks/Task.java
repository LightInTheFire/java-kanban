package tasks;

public record Task(String title,
                   String description,
                   int id,
                   TaskStatus status)
        implements  BaseTask{

}
