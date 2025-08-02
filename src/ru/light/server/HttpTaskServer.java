package ru.light.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import ru.light.managers.Managers;
import ru.light.managers.task.TaskManager;
import ru.light.server.adapters.DurationAdapter;
import ru.light.server.adapters.LocalDateTimeAdapter;
import ru.light.server.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private final Gson gson;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
        try {
            this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        } catch (IOException e) {
            throw new RuntimeException("An exception occurred while starting the server", e);
        }
        this.gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
        initializeContext();
    }

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();
    }

    public void start() {
        httpServer.start();
        System.out.println("Server started on port " + PORT);
    }

    public void stop() {
        System.out.println("Stopping server...");
        httpServer.stop(2);
    }

    public Gson getGson() {
        return gson;
    }

    private void initializeContext() {
        httpServer.createContext("/tasks", new TaskHttpHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubtaskHttpHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicHttpHandler(taskManager, gson));
        httpServer.createContext("/history", new HistoryHttpHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new PrioritizedHttpHandler(taskManager, gson));
    }
}
