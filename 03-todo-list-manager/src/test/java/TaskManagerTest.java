import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    // ===== addTask() tests =====

    @Test
    void addTask_ShouldAddTaskToEmptyList() {
        // Act
        taskManager.addTask("First task");

        // Assert
        String result = taskManager.viewAllTasks();
        assertTrue(result.contains("First task"));
        assertTrue(result.contains("1."));
    }

    @Test
    void addTask_ShouldAddMultipleTasks() {
        // Act
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.addTask("Task 3");

        // Assert
        String result = taskManager.viewAllTasks();
        assertTrue(result.contains("1. [ ] Task 1"));
        assertTrue(result.contains("2. [ ] Task 2"));
        assertTrue(result.contains("3. [ ] Task 3"));
    }

    @Test
    void addTask_WithEmptyDescription_ShouldStillAdd() {
        // Act
        taskManager.addTask("");

        // Assert
        String result = taskManager.viewAllTasks();
        assertFalse(result.contains("You don't have any tasks yet"));
    }

    // ===== viewAllTasks() tests =====

    @Test
    void viewAllTasks_WhenEmpty_ShouldReturnEmptyMessage() {
        // Act
        String result = taskManager.viewAllTasks();

        // Assert
        assertEquals("You don't have any tasks yet.\n", result);
    }

    @Test
    void viewAllTasks_ShouldShowAllTasks() {
        // Arrange
        taskManager.addTask("Buy milk");
        taskManager.addTask("Walk dog");

        // Act
        String result = taskManager.viewAllTasks();

        // Assert
        assertTrue(result.contains("1. [ ] Buy milk"));
        assertTrue(result.contains("2. [ ] Walk dog"));
    }

    @Test
    void viewAllTasks_ShouldShowTaskNumbers() {
        // Arrange
        taskManager.addTask("Task A");
        taskManager.addTask("Task B");
        taskManager.addTask("Task C");

        // Act
        String result = taskManager.viewAllTasks();

        // Assert
        assertTrue(result.contains("1."));
        assertTrue(result.contains("2."));
        assertTrue(result.contains("3."));
    }

    @Test
    void viewAllTasks_ShouldShowCompletedStatus() {
        // Arrange
        taskManager.addTask("Completed task");
        taskManager.addTask("Pending task");
        taskManager.completeTask(1);

        // Act
        String result = taskManager.viewAllTasks();

        // Assert
        assertTrue(result.contains("[X] Completed task"));
        assertTrue(result.contains("[ ] Pending task"));
    }

    // ===== completeTask() tests =====

    @Test
    void completeTask_WithValidNumber_ShouldReturnTrue() {
        // Arrange
        taskManager.addTask("Task to complete");

        // Act
        boolean result = taskManager.completeTask(1);

        // Assert
        assertTrue(result);
    }

    @Test
    void completeTask_WithValidNumber_ShouldMarkTaskCompleted() {
        // Arrange
        taskManager.addTask("Task to complete");

        // Act
        taskManager.completeTask(1);

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("[X]"));
    }

    @Test
    void completeTask_WithInvalidNumber_ShouldReturnFalse() {
        // Arrange
        taskManager.addTask("Task 1");

        // Act
        boolean result = taskManager.completeTask(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void completeTask_WithZero_ShouldReturnFalse() {
        // Arrange
        taskManager.addTask("Task 1");

        // Act
        boolean result = taskManager.completeTask(0);

        // Assert
        assertFalse(result);
    }

    @Test
    void completeTask_WithNegativeNumber_ShouldReturnFalse() {
        // Arrange
        taskManager.addTask("Task 1");

        // Act
        boolean result = taskManager.completeTask(-1);

        // Assert
        assertFalse(result);
    }

    @Test
    void completeTask_OnEmptyList_ShouldReturnFalse() {
        // Act
        boolean result = taskManager.completeTask(1);

        // Assert
        assertFalse(result);
    }

    @Test
    void completeTask_MultipleTasks_ShouldCompleteCorrectOne() {
        // Arrange
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.addTask("Task 3");

        // Act
        taskManager.completeTask(2);

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("1. [ ] Task 1"));
        assertTrue(tasks.contains("2. [X] Task 2"));
        assertTrue(tasks.contains("3. [ ] Task 3"));
    }

    @Test
    void completeTask_AlreadyCompleted_ShouldStillReturnTrue() {
        // Arrange
        taskManager.addTask("Task");
        taskManager.completeTask(1);

        // Act
        boolean result = taskManager.completeTask(1);

        // Assert
        assertTrue(result);
    }

    // ===== deleteTask() tests =====

    @Test
    void deleteTask_WithValidNumber_ShouldReturnTrue() {
        // Arrange
        taskManager.addTask("Task to delete");

        // Act
        boolean result = taskManager.deleteTask(1);

        // Assert
        assertTrue(result);
    }

    @Test
    void deleteTask_WithValidNumber_ShouldRemoveTask() {
        // Arrange
        taskManager.addTask("Task to delete");

        // Act
        taskManager.deleteTask(1);

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertEquals("You don't have any tasks yet.\n", tasks);
    }

    @Test
    void deleteTask_WithInvalidNumber_ShouldReturnFalse() {
        // Arrange
        taskManager.addTask("Task 1");

        // Act
        boolean result = taskManager.deleteTask(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteTask_WithZero_ShouldReturnFalse() {
        // Arrange
        taskManager.addTask("Task 1");

        // Act
        boolean result = taskManager.deleteTask(0);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteTask_WithNegativeNumber_ShouldReturnFalse() {
        // Arrange
        taskManager.addTask("Task 1");

        // Act
        boolean result = taskManager.deleteTask(-1);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteTask_OnEmptyList_ShouldReturnFalse() {
        // Act
        boolean result = taskManager.deleteTask(1);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteTask_FromMiddle_ShouldShiftIndices() {
        // Arrange
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.addTask("Task 3");

        // Act
        taskManager.deleteTask(2);

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("1. [ ] Task 1"));
        assertTrue(tasks.contains("2. [ ] Task 3"));
        assertFalse(tasks.contains("Task 2"));
    }

    @Test
    void deleteTask_FirstTask_ShouldWorkCorrectly() {
        // Arrange
        taskManager.addTask("First");
        taskManager.addTask("Second");

        // Act
        taskManager.deleteTask(1);

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("1. [ ] Second"));
        assertFalse(tasks.contains("First"));
    }

    @Test
    void deleteTask_LastTask_ShouldWorkCorrectly() {
        // Arrange
        taskManager.addTask("First");
        taskManager.addTask("Second");

        // Act
        taskManager.deleteTask(2);

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("1. [ ] First"));
        assertFalse(tasks.contains("Second"));
    }

    @Test
    void deleteTask_AllTasks_ShouldResultInEmptyList() {
        // Arrange
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");

        // Act
        taskManager.deleteTask(1);
        taskManager.deleteTask(1);  // Now deleting what was task 2

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertEquals("You don't have any tasks yet.\n", tasks);
    }

    // ===== Integration/scenario tests =====

    @Test
    void scenario_AddCompleteAndView() {
        // Arrange & Act
        taskManager.addTask("Morning workout");
        taskManager.addTask("Buy groceries");
        taskManager.completeTask(1);

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("[X] Morning workout"));
        assertTrue(tasks.contains("[ ] Buy groceries"));
    }

    @Test
    void scenario_AddMultipleCompleteMultipleDeleteSome() {
        // Arrange
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.addTask("Task 3");
        taskManager.addTask("Task 4");

        // Act
        taskManager.completeTask(1);
        taskManager.completeTask(3);
        taskManager.deleteTask(2);  // Delete Task 2

        // Assert
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("[X] Task 1"));
        assertTrue(tasks.contains("[X] Task 3"));
        assertTrue(tasks.contains("[ ] Task 4"));
        assertFalse(tasks.contains("Task 2"));
    }

    @Test
    void scenario_CompleteAllTasks() {
        // Arrange
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.addTask("Task 3");

        // Act
        taskManager.completeTask(1);
        taskManager.completeTask(2);
        taskManager.completeTask(3);

        // Assert
        String tasks = taskManager.viewAllTasks();
        int completedCount = tasks.length() - tasks.replace("[X]", "").length();
        assertEquals(9, completedCount);  // 3 tasks * 3 characters "[X]"
    }

    @Test
    void scenario_DeleteAfterCompletion() {
        // Arrange
        taskManager.addTask("Completed task");
        taskManager.completeTask(1);

        // Act
        boolean deleted = taskManager.deleteTask(1);

        // Assert
        assertTrue(deleted);
        assertEquals("You don't have any tasks yet.\n", taskManager.viewAllTasks());
    }

    @Test
    void scenario_StressTest_ManyTasks() {
        // Arrange & Act
        for (int i = 1; i <= 100; i++) {
            taskManager.addTask("Task " + i);
        }

        // Assert
        taskManager.completeTask(50);
        String tasks = taskManager.viewAllTasks();
        assertTrue(tasks.contains("50. [X] Task 50"));
        assertTrue(tasks.contains("100. [ ] Task 100"));
    }
}

