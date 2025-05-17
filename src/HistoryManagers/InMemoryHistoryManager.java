package HistoryManagers;

import tasks.BaseTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_CAPACITY = 10;

    private final List<BaseTask> history;

    public InMemoryHistoryManager() {
        this.history = new ArrayList<>();
    }

    @Override
    public void add(BaseTask task) {
        if (task == null) {
            return;
        }
        if (history.size() <= MAX_CAPACITY) {
            history.addLast(task);
            return;
        }

        history.removeFirst();
        history.addLast(task);
    }

    @Override
    public List<BaseTask> getHistory() {
        return history;
    }
}
