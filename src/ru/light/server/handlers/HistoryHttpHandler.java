package ru.light.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.light.managers.task.TaskManager;
import ru.light.task.BaseTask;

import java.util.List;

public class HistoryHttpHandler extends BaseHttpHandler {
    public HistoryHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) {
        if (exchange.getRequestMethod().equals("GET")) {
            handleGet(exchange);
        } else {
            sendResponseBadRequest(exchange);
        }
    }

    private void handleGet(HttpExchange exchange) {
        List<BaseTask> history = taskManager.getHistory();
        String historyJson = gson.toJson(history);
        sendResponse(exchange, historyJson, 200);
    }
}
