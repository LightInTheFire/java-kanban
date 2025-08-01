package ru.light.server.handlers;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.light.managers.history.InMemoryHistoryManager;
import ru.light.managers.task.InMemoryTaskManager;
import ru.light.managers.task.TaskManager;
import ru.light.server.HttpTaskServer;
import ru.light.task.EpicTask;
import ru.light.task.SubTask;
import ru.light.task.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SubtaskHttpHandlerTest {
    private HttpTaskServer httpTaskServer;
    private TaskManager taskManager;
    private SubTask subtask;

    @BeforeEach
    public void setup() {
        subtask = new SubTask("Task",
                "task desc",
                null,
                TaskStatus.NEW,
                0,
                Duration.ofMinutes(10),
                LocalDateTime.of(2021, 1, 1, 1, 1));
        taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
        taskManager.addTask(new EpicTask("epic", "epic desc", null));
        httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();
    }

    @AfterEach
    public void teardown() {
        httpTaskServer.stop();
    }

    @Test
    public void testPostSubTaskWithOverlap() throws IOException, InterruptedException {
        taskManager.addTask(subtask);
        SubTask newTask = (SubTask) subtask.clone();
        newTask.setId(null);
        String taskJson = httpTaskServer.getGson().toJson(newTask);
        URI uri = URI.create("http://localhost:8080/subtasks");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(406, response.statusCode());
            List<SubTask> tasksFromTaskManager = taskManager.getAllSubTasks();
            assertEquals(1, tasksFromTaskManager.size());
        }
    }

    @Test
    public void testUpdateSubTask() throws IOException, InterruptedException {
        taskManager.addTask(subtask);
        SubTask newTask = (SubTask) subtask.clone();
        newTask.setTitle("new title");
        String taskJson = httpTaskServer.getGson().toJson(newTask);
        URI uri = URI.create("http://localhost:8080/subtasks");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            List<SubTask> tasksFromTaskManager = taskManager.getAllSubTasks();
            assertEquals(1, tasksFromTaskManager.size());
            assertNotEquals(subtask.getTitle(), tasksFromTaskManager.getFirst().getTitle());
        }
    }

    @Test
    public void testPostSubTask() throws IOException, InterruptedException {
        String taskJson = httpTaskServer.getGson().toJson(subtask);
        URI uri = URI.create("http://localhost:8080/subtasks");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, response.statusCode());
            List<SubTask> tasksFromTaskManager = taskManager.getAllSubTasks();
            assertEquals(1, tasksFromTaskManager.size());
            assertEquals(subtask.getTitle(), tasksFromTaskManager.getFirst().getTitle());
        }
    }

    @Test
    public void testGetSubTasks() throws IOException, InterruptedException {
        taskManager.addTask(subtask);
        URI uri = URI.create("http://localhost:8080/subtasks");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            List<SubTask> tasks = httpTaskServer.getGson().fromJson(response.body(),
                    new TypeToken<List<SubTask>>() {
                    }.getType());
            assertEquals(taskManager.getAllSubTasks(), tasks);
        }
    }

    @Test
    public void testGetSubTaskWithCorrectId() throws IOException, InterruptedException {
        taskManager.addTask(subtask);
        URI uri = URI.create("http://localhost:8080/subtasks/1");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            SubTask taskFromResponse = httpTaskServer.getGson().fromJson(response.body(), SubTask.class);
            assertEquals(subtask.getTitle(), taskFromResponse.getTitle());
        }
    }

    @Test
    public void testGetSubTaskWithInvalidId() throws IOException, InterruptedException {
        taskManager.addTask(subtask);
        URI uri = URI.create("http://localhost:8080/subtasks/2");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(404, response.statusCode());
        }
    }

    @Test
    public void testDeleteSubTaskWithCorrectId() throws IOException, InterruptedException {
        taskManager.addTask(subtask);
        URI uri = URI.create("http://localhost:8080/subtasks/1");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .DELETE()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertEquals(0, taskManager.getAllSubTasks().size());
        }
    }

    @Test
    public void testDeleteSubTaskWithInvalidId() throws IOException, InterruptedException {
        taskManager.addTask(subtask);
        URI uri = URI.create("http://localhost:8080/subtasks/2");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .DELETE()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(404, response.statusCode());
            assertEquals(1, taskManager.getAllSubTasks().size());
        }
    }
}
