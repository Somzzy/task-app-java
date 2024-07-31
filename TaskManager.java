import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public Task addTask(String description) {
        String id = UUID.randomUUID().toString();
        Task newTask = new Task(id, description);
        tasks.add(newTask);
        return newTask;
    }

    public void addExistingTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(String id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}