import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void constructor_ShouldCreateTaskWithDescription() {
        // Arrange & Act
        Task task = new Task("Buy groceries");

        // Assert
        assertEquals("Buy groceries", task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    void constructor_ShouldInitializeTaskAsNotCompleted() {
        // Arrange & Act
        Task task = new Task("Walk the dog");

        // Assert
        assertFalse(task.isCompleted());
    }

    @Test
    void complete_ShouldMarkTaskAsCompleted() {
        // Arrange
        Task task = new Task("Clean room");

        // Act
        task.complete();

        // Assert
        assertTrue(task.isCompleted());
    }

    @Test
    void complete_WhenCalledMultipleTimes_ShouldRemainCompleted() {
        // Arrange
        Task task = new Task("Study Java");

        // Act
        task.complete();
        task.complete();
        task.complete();

        // Assert
        assertTrue(task.isCompleted());
    }

    @Test
    void getDisplayString_WhenNotCompleted_ShouldShowEmptyCheckbox() {
        // Arrange
        Task task = new Task("Write tests");

        // Act
        String displayString = task.getDisplayString();

        // Assert
        assertEquals("[ ] Write tests", displayString);
    }

    @Test
    void getDisplayString_WhenCompleted_ShouldShowCheckedCheckbox() {
        // Arrange
        Task task = new Task("Write tests");
        task.complete();

        // Act
        String displayString = task.getDisplayString();

        // Assert
        assertEquals("[X] Write tests", displayString);
    }

    @Test
    void toString_ShouldReturnDebugFormat() {
        // Arrange
        Task task = new Task("Debug task");

        // Act
        String result = task.toString();

        // Assert
        assertTrue(result.contains("Task{"));
        assertTrue(result.contains("description=Debug task"));
        assertTrue(result.contains("completed=false"));
    }

    @Test
    void toString_WhenCompleted_ShouldShowCompletedTrue() {
        // Arrange
        Task task = new Task("Complete task");
        task.complete();

        // Act
        String result = task.toString();

        // Assert
        assertTrue(result.contains("completed=true"));
    }

    @Test
    void getDescription_ShouldReturnOriginalDescription() {
        // Arrange
        String description = "Original description";
        Task task = new Task(description);

        // Act & Assert
        assertEquals(description, task.getDescription());
    }

    @Test
    void constructor_WithEmptyString_ShouldCreateTask() {
        // Arrange & Act
        Task task = new Task("");

        // Assert
        assertEquals("", task.getDescription());
        assertEquals("[ ] ", task.getDisplayString());
    }

    @Test
    void constructor_WithLongDescription_ShouldHandleCorrectly() {
        // Arrange
        String longDescription = "This is a very long task description that contains many words and spans multiple lines conceptually";

        // Act
        Task task = new Task(longDescription);

        // Assert
        assertEquals(longDescription, task.getDescription());
        assertTrue(task.getDisplayString().contains(longDescription));
    }
}

