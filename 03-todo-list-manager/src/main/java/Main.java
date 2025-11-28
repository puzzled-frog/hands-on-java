import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean running = true;
        TaskManager taskManager = new TaskManager();
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                displayMenu();
                System.out.print("Choose an option: ");
                String menuChoice = scanner.nextLine();
                switch (menuChoice) {
                    case "1" -> addTask(scanner, taskManager);
                    case "2" -> listTasks(taskManager);
                    case "3" -> completeTask(scanner, taskManager);
                    case "4" -> deleteTask(scanner, taskManager);
                    case "5" -> {
                        System.out.println("Goodbye!\n");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice\n");
                }
            }
        }

    }

    private static void displayMenu() {
        System.out.println("""
                === Todo List Manager ===
                1. Add task
                2. View all tasks
                3. Complete task
                4. Delete task
                5. Exit
                """);
    }

    private static void addTask(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter task description: ");
        String taskDescription = scanner.nextLine().trim();
        if (taskDescription.isEmpty()) {
            System.out.println("Task description cannot be empty.\n");
            return;
        }
        taskManager.addTask(taskDescription);
        System.out.println("Task added successfully!\n");
    }

    private static void listTasks(TaskManager taskManager) {
        String tasksList = taskManager.viewAllTasks();
        System.out.println(tasksList + "\n");
    }

    private static void completeTask(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter task number to complete: ");
        int taskNumber = 0;
        try {
            taskNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.\n");
            return;
        }
        if (taskManager.completeTask(taskNumber)) {
            System.out.println("Task marked as completed.\n");
        } else {
            System.out.println("Invalid task number.\n");
        }
    }

    private static void deleteTask(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter task number to delete: ");
        int taskNumber = 0;
        try {
            taskNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.\n");
            return;
        }
        if (taskManager.deleteTask(taskNumber)) {
            System.out.println("Task deleted.\n");
        } else {
            System.out.println("Invalid task number.\n");
        }
    }

}
