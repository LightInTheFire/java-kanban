import view.ConsoleTaskManager;
import model.TaskManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        ConsoleTaskManager consoleTaskManager = new ConsoleTaskManager(taskManager, scanner);
        consoleTaskManager.start();
    }
}
