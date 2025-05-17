package HistoryManagers;

import tasks.BaseTask;

import java.util.List;

public interface HistoryManager {
    void add(BaseTask task);
    List<BaseTask> getHistory();
}
