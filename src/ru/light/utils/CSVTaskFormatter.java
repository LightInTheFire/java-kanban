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
                    "");
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
                    "");
        };
    }

    public static Task fromCSVString(String str) {
        return null;
    }
}
