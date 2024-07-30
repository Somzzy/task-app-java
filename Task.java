public class Task {
    private String id;
    private String description;
    private boolean completed;

    public Task(String id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public String getDescription() { 
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCompleted() { 
        return completed;
    }
    public void setCompleted(boolean completed) { 
        this.completed = completed;
    }

    @Override
    public String toString() {
        return (completed ? "[✓] " : "[ ] ") + description;
    }
}