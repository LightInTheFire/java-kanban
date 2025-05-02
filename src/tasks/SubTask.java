package tasks;

import java.util.Objects;

public record SubTask(String title,
                      String description,
                      int id,
                      TaskStatus status)
        implements BaseTask {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        SubTask subTask = (SubTask) o;
        return Objects.equals(title, subTask.title)
                && status == subTask.status
                && Objects.equals(description, subTask.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
}
