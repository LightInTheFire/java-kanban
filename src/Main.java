import HistoryManagers.InMemoryHistoryManager;
import TaskManagers.InMemoryTaskManager;
import tasks.*;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

        taskManager.addTask(new Task("Задача 1", "описание ", null, TaskStatus.NEW));
        taskManager.addTask(new Task("Задача 1", "описание ", null, TaskStatus.DONE));
        taskManager.addTask(new Task("Задача 1", "описание ", null, TaskStatus.IN_PROGRESS));

        EpicTask epicTask1 = new EpicTask("Эпик 1", "описание", null);
        taskManager.addTask(epicTask1);

        EpicTask epicTask2 = new EpicTask("Эпик 2", "описание", null);
        taskManager.addTask(epicTask2);
        SubTask subTask1 = new SubTask("Подзадача 1", "описание", null, TaskStatus.NEW, 3);
        SubTask subTask2 = new SubTask("Подзадача 2", "описание", null, TaskStatus.NEW, 3);
        SubTask subTask3 = new SubTask("Подзадача 3", "описание", null, TaskStatus.NEW, 3);
        SubTask subTask4 = new SubTask("Подзадача 4", "описание", null, TaskStatus.IN_PROGRESS, epicTask2.getId());
        SubTask subTask5 = new SubTask("Подзадача 5", "описание", null, TaskStatus.DONE, epicTask2.getId());
        taskManager.addTask(subTask4);
        taskManager.addTask(subTask5);

        taskManager.addTask(subTask1);
        taskManager.addTask(subTask2);
        printTasks(taskManager.getTasks());
        System.out.println();

        taskManager.addTask(subTask3);
        printTasks(taskManager.getTasks());
        System.out.println();

        SubTask subTask3Upd = new SubTask("Подзадача 3", "описание", subTask3.getId(), TaskStatus.DONE, epicTask1.getId());
        taskManager.updateTask(subTask3Upd);

        printTasks(taskManager.getTasks());
        System.out.println();

        taskManager.removeById(7);
        taskManager.removeById(8);
        printTasks(taskManager.getTasks());
        System.out.println();

        taskManager.removeById(4);

        printTasks(taskManager.getTasks());
        System.out.println();
    }

    private static void printTasks(Map<Integer, BaseTask> tasks) {
        for (Map.Entry<Integer, BaseTask> taskEntry : tasks.entrySet()) {
            switch (taskEntry.getValue()) {
                case EpicTask epicTask -> {
                    System.out.println(epicTask.getSubTasks());
                    System.out.printf("%d: %s%n", taskEntry.getKey(), epicTask);
                }
                case SubTask subTask -> System.out.printf("%d: %s + epicId %d%n",
                        taskEntry.getKey(), subTask, subTask.getEpicTaskId());
                case Task task -> System.out.printf("%d: %s%n",
                        taskEntry.getKey(), taskEntry.getValue());
            }

        }
    }
}
