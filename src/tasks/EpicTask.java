package tasks;

import java.util.List;
import java.util.Objects;

public record EpicTask(String title,
                       String description,
                       int id,
                       TaskStatus status,
                       List<SubTask> subTasks)
        implements BaseTask {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EpicTask epicTask)) return false;

        return Objects.equals(title, epicTask.title)
                && status == epicTask.status
                && Objects.equals(description, epicTask.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
}
