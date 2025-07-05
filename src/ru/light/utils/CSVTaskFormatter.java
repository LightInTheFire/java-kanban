package ru.light.utils;

import ru.light.task.*;

public class CSVTaskFormatter {
    private CSVTaskFormatter() {
    }

    public static String getHeader() {
        return "id,type,name,status,description,epicId";
    }

    public static String toCSVString(BaseTask baseTask) {
        String formatPattern = "%s,%s,%s,%s,%s,%s";
        StringBuilder csvStringBuilder = new StringBuilder();
        return switch (baseTask) {
            case EpicTask epicTask -> formatPattern.formatted(epicTask.getId(),
                    TaskType.EPICTASK,
                    epicTask.getTitle(),
                    epicTask.getStatus(),
                    epicTask.getDescription(),
                    " ");
            case SubTask subTask -> formatPattern.formatted(subTask.getId(),
                    TaskType.SUBTASK,
                    subTask.getTitle(),
                    subTask.getStatus(),
                    subTask.getDescription(),
                    subTask.getEpicTaskId());
            case Task task -> formatPattern.formatted(task.getId(),
                    TaskType.TASK,
                    task.getTitle(),
                    task.getStatus(),
                    task.getDescription(),
                    " ");
        };
    }

    public static BaseTask fromCSVString(String str) {
        String[] taskFields = str.split(",");
        if (taskFields.length != 6) {
            throw new IllegalArgumentException("Incorrect csv pattern");
        }
        int id = Integer.parseInt(taskFields[0]);
        TaskType type = TaskType.valueOf(taskFields[1]);
        String title = taskFields[2];
        TaskStatus status = TaskStatus.valueOf(taskFields[3]);
        String description = taskFields[4];
        return switch (type) {
            case TASK -> new Task(title, description, id, status);
            case SUBTASK -> new SubTask(title, description, id, status, Integer.parseInt(taskFields[5]));
            case EPICTASK -> new EpicTask(title, description, id);
        };
    }
}
