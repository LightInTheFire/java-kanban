package view;

import model.MenuOption;
import model.TaskManager;
import tasks.*;

import java.util.List;
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
        System.out.println("Введите id Эпика");
        Integer id = getNumberFromUser();
        if (id == null) return;

        List<SubTask> allSubtasksOfEpic = taskManager.getAllSubtasksOfEpic(id);
        if (allSubtasksOfEpic == null) {
            System.out.println("Это не эпик.");
            return;
        }
        allSubtasksOfEpic.forEach(System.out::println);
    }

    private void getTaskById() {
        System.out.println("Введите id задачи:");
        Integer id = getNumberFromUser();
        if (id == null) return;
        BaseTask task = taskManager.getById(id);
        if (task == null) {
            System.out.println("Задачи с таким id нет.");
            return;
        }
        System.out.println(task);
    }

    private void deleteAllTasksByType() {
        System.out.println("Какой тип задач вы хотите удалить?");
        printTaskTypes();
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> taskManager.removeAllStandardTasks();
            case "2" -> taskManager.removeAllSubTasks();
            case "3" -> taskManager.removeAllEpicsTasks();
            default -> System.out.println("Неверный ввод.");
        }
    }

    private void deleteTaskById() {
        System.out.println("Введите id задачи:");
        Integer id = getNumberFromUser();
        if (id == null) return;

        taskManager.removeById(id);
    }

    private void updateTask() {
        System.out.println("Введите id задачи которую хотите обновить");
        Integer id = getNumberFromUser();
        if (id == null) return;

        BaseTask task = taskManager.getById(id);
        if (task == null) {
            System.out.println("Задачи с таким id нет.");
            return;
        }

        System.out.println("Что вы хотите обновить?");
        printTaskFields(task);
        Integer fieldNumberToUpdate = getNumberFromUser();
        if (fieldNumberToUpdate == null) return;


        BaseTask updatedTask = switch (task) {
            case EpicTask epicTask -> handleEpicTaskUpdate(epicTask, fieldNumberToUpdate);
            case SubTask subTask -> handleSubTaskUpdate(subTask, fieldNumberToUpdate);
            case Task standardTask -> handleStandardTaskUpdate(standardTask, fieldNumberToUpdate);
        };
        taskManager.updateTask(updatedTask);
    }

    private BaseTask handleStandardTaskUpdate(Task standardTask, Integer fieldNumberToUpdate) {
        return switch (fieldNumberToUpdate) {
            case 1 -> updateTitle(standardTask);
            case 2 -> updateDescription(standardTask);
            case 3 -> updateStatus(standardTask);
            default -> throw new IllegalStateException("Unexpected value: " + fieldNumberToUpdate);
        };
    }

    private BaseTask handleSubTaskUpdate(SubTask subTask, Integer fieldNumberToUpdate) {
        return switch (fieldNumberToUpdate) {
            case 1 -> updateTitle(subTask);
            case 2 -> updateDescription(subTask);
            case 3 -> updateStatus(subTask);
            case 4 -> updateEpicId(subTask);
            default -> throw new IllegalStateException("Unexpected value: " + fieldNumberToUpdate);
        };
    }

    private BaseTask updateEpicId(SubTask subTask) {
        System.out.println("Введите новый ID эпика:");
        Integer newEpicId = getNumberFromUser();
        if (newEpicId == null) return subTask;
        BaseTask task = taskManager.getById(newEpicId);
        if (task instanceof EpicTask epicTask) {
            epicTask.setId(newEpicId);
        } else {
            System.out.println("Нет эпика с таким id.");
        }
        return subTask;
    }

    private BaseTask handleEpicTaskUpdate(EpicTask epicTask, Integer fieldNumberToUpdate) {
        return switch (fieldNumberToUpdate) {
            case 1 -> updateTitle(epicTask);
            case 2 -> updateDescription(epicTask);
            case 3 -> updateStatus(epicTask);
            case 4 -> addSubTaskToEpic(epicTask);
            case 5 -> removeSubTaskFromEpic(epicTask);
            default -> throw new IllegalStateException("Unexpected value: " + fieldNumberToUpdate);
        };
    }

    private BaseTask removeSubTaskFromEpic(EpicTask epicTask) {
        System.out.println("Введите id подзадачи");
        Integer id = getNumberFromUser();
        if (id == null) return epicTask;

        BaseTask task = taskManager.getById(id);
        if (task == null) return epicTask;

        if (task instanceof SubTask subTask) {
            subTask.setId(null);
            epicTask.removeSubTaskById(id);
        }
        return epicTask;
    }

    private BaseTask addSubTaskToEpic(EpicTask epicTask) {
        System.out.println("Введите id подзадачи");
        Integer id = getNumberFromUser();
        if (id == null) return epicTask;

        BaseTask task = taskManager.getById(id);
        if (task == null) return epicTask;

        if (task instanceof SubTask subTask) {
            subTask.setId(epicTask.getId());
            epicTask.addSubTask(subTask);
        }
        return epicTask;
    }

    private BaseTask updateTitle(BaseTask task) {
        System.out.println("Введите новое название:");
        String newTitle = scanner.nextLine();
        task.setTitle(newTitle);
        return task;
    }

    private BaseTask updateDescription(BaseTask task) {
        System.out.println("Введите новое описание:");
        String newDescription = scanner.nextLine();
        task.setDescription(newDescription);
        return task;
    }

    private BaseTask updateStatus(BaseTask task) {
        System.out.println("Выберите новый статус (1-NEW, 2-IN_PROGRESS, 3-DONE):");
        Integer statusChoice = getNumberFromUser();
        if (statusChoice == null) return task;

        TaskStatus newStatus = switch (statusChoice) {
            case 1 -> TaskStatus.NEW;
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.DONE;
            default -> task.getStatus();
        };

        task.setStatus(newStatus);

        return task;
    }

    private void createNewTask() {
        System.out.println("Какую задачу вы хотите создать?");
        printTaskTypes();
        String input = scanner.nextLine();
        BaseTask newTask = switch (input) {
            case "1" -> createNewStandardTask();
            case "2" -> createNewSubTask();
            case "3" -> createNewEpic();
            default -> null;
        };

        if (newTask == null) {
            System.out.println("Произошла ошибка при создании задачи.");
            return;
        }

        taskManager.addTask(newTask);
        System.out.println("Задача добавлена успешно.");
    }

    private BaseTask createNewEpic() {
        BaseTask baseTask = createNewStandardTask();
        return new EpicTask(baseTask.getTitle(), baseTask.getDescription(), null, baseTask.getStatus());
    }

    private BaseTask createNewSubTask() {
        BaseTask baseTask = createNewStandardTask();
        System.out.println("Введите id эпика(или пустую строку если его еще нет):");
        Integer epicId = null;
        try {
            epicId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ignored) {
        }
        return new SubTask(baseTask.getTitle(),
                baseTask.getDescription(), null, baseTask.getStatus(), epicId);
    }

    private BaseTask createNewStandardTask() {
        System.out.println("Введите название задачи:");
        String title = scanner.nextLine();
        System.out.println("Введите описание задачи:");
        String description = scanner.nextLine();
        TaskStatus status = TaskStatus.NEW;
        return new Task(title, description, null, status);
    }

    private Integer getNumberFromUser() {
        int number;
        try {
            number = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Не число!");
            return null;
        }
        return number;
    }

    private void printAllTasksByType() {
        System.out.println("Какой тип задач вы хотите увидеть?");
        printTaskTypes();
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> taskManager.getAllStandartTasks()
                    .forEach((id, task)
                            -> System.out.printf("id = %d \n%s\n", id, task));
            case "2" -> taskManager.getAllSubTasks()
                    .forEach((id, subTask)
                            -> System.out.printf("id = %d, epicId = %d \n%s\n", id, subTask.getEpicTaskId(), subTask));
            case "3" -> taskManager.getAllEpicsTasks()
                    .forEach((id, epicTask)
                            -> System.out.printf("%s \n id = %d \n%s\n", epicTask.getSubTasks(), id, epicTask));
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
                case Task task -> System.out.printf("id = %d \n%s\n", taskEntry.getKey(), task);
            }
        }
    }

    private static void printTaskTypes() {
        System.out.println("""
                1. Обычные задачи
                2. Подзадачи
                3. Эпики""");
    }

    private void printTaskFields(BaseTask task) {
        System.out.println("""
                1. Название
                2. Описание
                3. Статус""");
        if (task instanceof EpicTask) {
            System.out.println("""
                    4. Добавить подзадачу
                    5. Удалить подзадачу""");
        } else if (task instanceof SubTask) {
            System.out.println("4. Изменить id эпика");
        }
    }

    private void printMenu() {
        for (MenuOption option : MenuOption.values()) {
            System.out.printf("%d - %s\n", option.ordinal() + 1, option.getDescription());
        }
    }
}
