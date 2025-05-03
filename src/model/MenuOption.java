package model;

public enum MenuOption {
    GET_ALL_TASKS("Получить все задачи"),
    GET_ALL_TASKS_BY_TYPE("Получить все задачи определенного типа"),
    GET_TASK_BY_ID("Получить задачу по её id"),
    GET_ALL_SUBTASKS_OF_EPIC_BY_ID("Получить все подзадачи эпика по его id"),
    DELETE_ALL_TASKS_BY_TYPE("Удалить все задачи определенного типа"),
    CREATE_NEW_TASK("Создать новую задачу"),
    UPDATE_TASK("Обновить задачу"),
    DELETE_TASK_BY_ID("Удалить задачу по её id"),
    EXIT_APP("Выйти из приложения");

    private final String description;

    MenuOption(String description) {
        this.description = description;
    }

    public static MenuOption parseValue(int value) {
        return MenuOption.values()[value - 1];
    }

    public String getDescription() {
        return description;
    }
}
