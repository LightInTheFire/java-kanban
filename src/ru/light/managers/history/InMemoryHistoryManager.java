package ru.light.managers.history;

import ru.light.task.BaseTask;

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
        BaseTask copy = task.clone();
        if (history.size() < MAX_CAPACITY) {
            history.addLast(copy);
            return;
        }

        history.removeFirst();
        history.addLast(copy);
    }

    @Override
    public List<BaseTask> getHistory() {
        return List.copyOf(history);
    }
}
