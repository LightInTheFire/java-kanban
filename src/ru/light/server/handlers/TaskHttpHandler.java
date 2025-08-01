package ru.light.server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import ru.light.managers.exceptions.TaskIntersectException;
import ru.light.managers.task.TaskManager;
import ru.light.task.BaseTask;
import ru.light.task.Task;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TaskHttpHandler extends BaseHttpHandler {
    public TaskHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET" -> handleGet(exchange);
            case "POST" -> handlePost(exchange);
            case "DELETE" -> handleDelete(exchange);
            default -> sendResponseBadRequest(exchange);
        }
    }

    private void handleGet(HttpExchange exchange) {
        String[] pathParts = splitURI(exchange);
        switch (pathParts.length) {
            case 2 -> {
                List<Task> allTasks = taskManager.getAllStandardTasks();
                String allTasksJson = gson.toJson(allTasks);
                sendResponse(exchange, allTasksJson, 200);
            }
            case 3 -> {
                int taskId;
                try {
                    taskId = Integer.parseInt(pathParts[2]);
                } catch (NumberFormatException e) {
                    sendResponseNotFound(exchange, "Not a number");
                    return;
                }

                Optional<BaseTask> taskById = taskManager.getById(taskId);
                taskById.ifPresentOrElse(baseTask ->
                                sendResponse(exchange, gson.toJson(baseTask), 200),
                        () -> sendResponseNotFound(exchange, "Not found"));
            }
            default -> sendResponseNotFound(exchange, "Bad Request");
        }
    }

    private void handlePost(HttpExchange exchange) {
        String[] pathParts = splitURI(exchange);
        if (pathParts.length != 2) {
            sendResponseNotFound(exchange, "Bad Request");
            return;
        }
        String body = getStringFromRequestBody(exchange);
        Task taskFromRequest;
        try {
            taskFromRequest = gson.fromJson(body, Task.class);
        } catch (JsonSyntaxException e) {
            sendResponseNotFound(exchange, "No request body provided");
            return;
        }

        if (taskFromRequest.getId() != null) {
            try {
                taskManager.updateTask(taskFromRequest);
            } catch (TaskIntersectException e) {
                sendResponseHasOverlaps(exchange, "Task intersect failed");
            }
            sendResponse(exchange,
                    "Task with id %d successfully updated".formatted(taskFromRequest.getId()),
                    200);
        } else {
            try {
                taskManager.addTask(taskFromRequest);
            } catch (TaskIntersectException e) {
                sendResponseHasOverlaps(exchange, e.getMessage());
                return;
            }
            sendResponse(exchange,
                    "Task successfully added with id %d".formatted(taskFromRequest.getId()),
                    201);
        }

    }

    private void handleDelete(HttpExchange exchange) {
        String[] pathParts = splitURI(exchange);
        if (pathParts.length != 3) {
            sendResponseNotFound(exchange, "Bad Request");
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(pathParts[2]);
        } catch (NumberFormatException e) {
            sendResponseNotFound(exchange, "Not a number");
            return;
        }

        try {
            taskManager.removeById(taskId);
        } catch (IllegalArgumentException e) {
            sendResponseNotFound(exchange, "No task with such id");
        }

        sendResponse(exchange, "task with id: %d successfully deleted".formatted(taskId), 200);
    }
}
