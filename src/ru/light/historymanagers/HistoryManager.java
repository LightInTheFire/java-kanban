package ru.light.historymanagers;

import ru.light.tasks.BaseTask;

import java.util.List;

public interface HistoryManager {
    void add(BaseTask task);
    List<BaseTask> getHistory();
}
