import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TaskApp extends JFrame {
    private TaskManager taskManager;
    private JList<Task> taskList;
    private DefaultListModel<Task> listModel;
    private JTextField taskInput;
    private JButton addButton;
    private JButton deleteButton;
    private JButton markCompletedButton;
    private JButton saveButton;

    public TaskApp() {
        taskManager = new TaskManager();
        initComponents();
        initLayout();
        loadTasksFromFile();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskInput = new JTextField(20);
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        markCompletedButton = new JButton("Mark as Completed");
        saveButton = new JButton("Save Tasks");

        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        markCompletedButton.addActionListener(e -> markTaskAsCompleted());
        saveButton.addActionListener(e -> saveTasksToFile());
    }

    private void initLayout() {
        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskInput);
        inputPanel.add(addButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(markCompletedButton);
        buttonPanel.add(saveButton);  // Add the save button to the button panel

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setSize(500, 300);  // Increased width to accommodate all buttons
    }

    private void addTask() {
        String description = taskInput.getText().trim();
        if (!description.isEmpty()) {
            taskManager.addTask(description);
            refreshTaskList();
            taskInput.setText("");
        }
    }

    private void deleteTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            taskManager.deleteTask(selectedTask.getId());
            refreshTaskList();
        }
    }

    private void markTaskAsCompleted() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            selectedTask.setCompleted(true);
            refreshTaskList();
        }
    }

    private void saveTasksToFile() {
        try (FileWriter writer = new FileWriter("tasks.txt")) {
            for (Task task : taskManager.getAllTasks()) {
                writer.write(task.getId() + "," + task.getDescription() + "," + task.isCompleted() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Tasks saved successfully to tasks.txt!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTasksFromFile() {
        File file = new File("tasks.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Task task = new Task(parts[0], parts[1]);
                        task.setCompleted(Boolean.parseBoolean(parts[2]));
                        taskManager.addExistingTask(task);
                    }
                }
                refreshTaskList();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshTaskList() {
        listModel.clear();
        for (Task task : taskManager.getAllTasks()) {
            listModel.addElement(task);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TaskApp().setVisible(true);
        });
    }
}