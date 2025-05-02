package tasks;

import java.util.Objects;

public record Task(String title,
                   String description,
                   int id,
                   TaskStatus status)
        implements  BaseTask{

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;

        return Objects.equals(title, task.title)
                && status == task.status
                && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
}
