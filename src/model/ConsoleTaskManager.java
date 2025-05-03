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
                case GET_ALL_TASKS_BY_TYPE -> printAllTasksByType();
                case GET_TASK_BY_ID -> getTaskById();
                case GET_ALL_SUBTASKS_OF_EPIC_BY_ID -> getAllSubtasksOfEpicById();
                case DELETE_ALL_TASKS_BY_TYPE -> deleteAllTasksByType();
                case CREATE_NEW_TASK -> createNewTask();
                case UPDATE_TASK -> updateTask();
                case DELETE_TASK_BY_ID -> deleteTaskById();
                case EXIT_APP -> {
                    System.out.println("Выход из приложения");
                    return;
                }
            }
        }
    }

    private void getAllSubtasksOfEpicById() {
    }

    private void getTaskById() {
    }

    private void deleteAllTasksByType() {
        System.out.println("Какой тип задач вы хотите удалить?");
        System.out.println("""
                1. Обычные задачи
                2. Подзадачи
                3. Эпики""");
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> taskManager.removeAllStandardTasks();
            case "2" -> taskManager.removeAllSubTasks();
            case "3" -> taskManager.removeAllEpicsTasks();
            default -> System.out.println("Неверный ввод.");
        }
    }

    private void deleteTaskById() {
    }

    private void updateTask() {
    }

    private void createNewTask() {

    }

    private void printAllTasksByType() {
        System.out.println("Какой тип задач вы хотите увидеть?");
        System.out.println("""
                1. Обычные задачи
                2. Подзадачи
                3. Эпики""");
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> taskManager.getAllStandartTasks()
                    .forEach((id, task)
                            -> System.out.printf("id = %d , %s\n", id, task));
            case "2" -> taskManager.getAllSubTasks()
                    .forEach((id , subTask)
                            -> System.out.printf("id = %d, epicId = %d , %s\n", id, subTask.getEpicTaskId(), subTask));
            case "3" -> taskManager.getAllEpicsTasks()
                    .forEach((id, epicTask)
                            -> System.out.printf("%s \n id = %d , %s\n",epicTask.getSubTasks(), id, epicTask));
            default -> System.out.println("Неверный ввод.");
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
                case Task task -> System.out.printf("id = %d \n%s", taskEntry.getKey(), task);
            }
        }
    }

    private void printMenu() {
        for (MenuOption option : MenuOption.values()) {
            System.out.printf("%d - %s\n", option.ordinal() + 1, option.getDescription());
        }
    }
}
