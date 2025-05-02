package tasks;

public sealed interface BaseTask permits Task, SubTask, EpicTask{
    String title();
    String description();
    int id();
    TaskStatus status();
}
