import java.util.ArrayList;

public class TaskManager {

    private final ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
    }

    public String viewAllTasks() {
        StringBuilder taskList = new StringBuilder();
        int index = 1;
        for (Task task : tasks) {
            taskList.append(index).append(". ").append(task.getDisplayString()).append("\n");
            index++;
        }
        if (taskList.isEmpty()) {
            return "You don't have any tasks yet.\n";
        }
        return taskList.toString();
    }

    public boolean deleteTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            tasks.remove(taskNumber - 1);
            return true;
        }
        return false;
    }

    public boolean completeTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            tasks.get(taskNumber - 1).complete();
            return true;
        }
        return false;
    }

    private boolean isValidTaskNumber(int taskNumber) {
        return taskNumber > 0 && taskNumber <= tasks.size();
    }

}
