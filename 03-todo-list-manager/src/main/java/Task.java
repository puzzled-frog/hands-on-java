public class Task {
    private final String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    @Override
    public String toString() {
        return "Task{description=" + description + ", completed=" + completed + "}";
    }

    public String getDisplayString() {
        String status = completed ? "[X]" : "[ ]";
        return status + " " + description;
    }

    public void complete() {
        this.completed = true;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}
