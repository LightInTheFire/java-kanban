package model;

import tasks.BaseTask;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.Map;
import java.util.Scanner;

public class ConsoleTaskManager {
    private final TaskManager taskManager;
    private final Scanner scanner;

    public ConsoleTaskManager(TaskManager taskManager, Scanner scanner) {
        this.taskManager = taskManager;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            printMenu();
            String command = scanner.nextLine().toUpperCase();
            MenuOption option;

            try {
                option = MenuOption.parseValue(Integer.parseInt(command));
            } catch (Exception e) {
                System.out.println("Такого пункта меню нет!");
                continue;
            }

            switch (option) {
                case GET_ALL_TASKS -> printAllTasks();
                case GET_ALL_TASKS_BY_TYPE -> {
                }
                case GET_TASK_BY_ID -> {
                }
                case GET_ALL_SUBTASKS_OF_EPIC -> {
                }
                case DELETE_ALL_TASKS_BY_TYPE -> {
                }
                case CREATE_NEW_TASK -> {
                }
                case UPDATE_TASK -> {
                }
                case DELETE_TASK_BY_ID -> {
                }
                case EXIT_APP -> {
                    System.out.println("Выход из приложения");
                    return;
                }
            }
        }
    }

    private void printAllTasks() {
        for (Map.Entry<Integer, BaseTask> taskEntry : taskManager.getTasks().entrySet()) {
            switch (taskEntry.getValue()) {
                case EpicTask epicTask -> {
                    System.out.println(taskEntry.getKey());
                    System.out.println(epicTask.getSubTasks());
                    System.out.println(epicTask);
                }
                case SubTask subTask -> {
                    System.out.println(taskEntry.getKey());
                    System.out.printf("Epic id %d  + %s", subTask.getEpicTaskId(), subTask);
                }
                case Task task -> System.out.printf("%d \n%s", taskEntry.getKey(), task);
            }
        }
    }

    private void printMenu() {
        for (MenuOption option : MenuOption.values()) {
            System.out.printf("%d - %s\n", option.ordinal() + 1, option.getDescription());
        }
    }
}
