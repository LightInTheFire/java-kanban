package ru.light.managers.task;

import ru.light.managers.Managers;
import ru.light.managers.exceptions.ManagerLoadException;
import ru.light.managers.exceptions.ManagerSaveException;
import ru.light.managers.history.HistoryManager;
import ru.light.task.BaseTask;
import ru.light.utils.CSVTaskFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);

        String[] lines;
        try {
            lines = Files.readString(file.toPath()).split(System.lineSeparator());

        } catch (IOException e) {
            throw new ManagerLoadException("Error while loading from file");
        }

        String history = lines[lines.length - 1];

        for (int i = 1, linesLength = lines.length - 2; i < linesLength; i++) {
            String line = lines[i];

            BaseTask baseTask = CSVTaskFormatter.fromCSVString(line);

            if (baseTask.getId() > taskManager.idCounter) {
                taskManager.idCounter = baseTask.getId();
            }
            taskManager.addTask(baseTask);
        }

        for (Integer id : CSVTaskFormatter.idStringToList(history)) {
            taskManager.getById(id);
        }

        return taskManager;
    }

    @Override
    public BaseTask getById(int id) {
        BaseTask task = super.getById(id);
        save();
        return task;
    }

    @Override
    public void removeById(int id) {
        super.removeById(id);
        save();
    }

    @Override
    public void addTask(BaseTask task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(BaseTask task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void removeAllEpicsTasks() {
        super.removeAllEpicsTasks();
        save();
    }

    @Override
    public void removeAllStandardTasks() {
        super.removeAllStandardTasks();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(CSVTaskFormatter.getHeader());
            writer.newLine();

            for (BaseTask task : tasks.values()) {
                writer.write(CSVTaskFormatter.toCSVString(task));
                writer.newLine();
            }

            writer.newLine();

            writer.write(CSVTaskFormatter.tasksToIdString(historyManager.getHistory()));
            writer.newLine();
        } catch (IOException ignored) {
           throw new ManagerSaveException("Cant save to file");
        }
    }
}
