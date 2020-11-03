package ui;

import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

// citation: https://www.youtube.com/watch?v=iE8tZ0hn2Ws
// gui class for workout manager
public class WorkoutManagerGUI extends JFrame implements ActionListener {

    private JTextArea userTextArea;
    private JLabel workoutNameLabel;
    private JLabel exerciseNameLabel;
    private JLabel exerciseRepsLabel;

    private JPanel panel1;
    private JPanel panel2;
    private JPanel gridPanel;

    private JButton addWorkoutButton;
    private JButton addExerciseButton;
    private JButton saveCollectionButton;
    private JButton loadCollectionButton;

    private JTextField workoutNameText;
    private JTextField exerciseNameText;
    private JTextField exerciseRepsText;

    // Workout Instance Data:
    private LinkedList<Workout> workoutLinkedList = new LinkedList<>();

    // EFFECTS: gui constructor for workout manager
    public WorkoutManagerGUI() {
        workoutNameLabel = new JLabel("workout name:");
        workoutNameText = new JTextField(8);
        exerciseNameLabel = new JLabel("exercise name:");
        exerciseNameText = new JTextField(8);
        exerciseRepsLabel = new JLabel("reps:");
        exerciseRepsText = new JTextField(4);

        addWorkoutButton = new JButton("add workout");
        addExerciseButton = new JButton("add exercise to workout");
        saveCollectionButton = new JButton("save");
        loadCollectionButton = new JButton("load");

        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridPanel = new JPanel(new GridLayout(2,1));

        userTextArea = new JTextArea();
        userTextArea.setEditable(false);

        panel1.add(workoutNameLabel);
        panel1.add(workoutNameText);
        panel1.add(exerciseNameLabel);
        panel1.add(exerciseNameText);
        panel1.add(exerciseRepsLabel);
        panel1.add(exerciseRepsText);

        panel2.add(addWorkoutButton);
        panel2.add(addExerciseButton);
        panel2.add(saveCollectionButton);
        panel2.add(loadCollectionButton);

        gridPanel.add(panel1);
        gridPanel.add(panel2);

        add(gridPanel, BorderLayout.SOUTH);
        add(userTextArea, BorderLayout.CENTER);

        addWorkoutButton.addActionListener(event -> addWorkout());
        addExerciseButton.addActionListener(event -> addExercise());
        saveCollectionButton.addActionListener(event -> saveCollection());

        loadCollectionButton.addActionListener(this);
    }

    public static void main(String[] args) {
        WorkoutManagerGUI app = new WorkoutManagerGUI();
        app.setVisible(true);
        app.setSize(600,600);
        app.setLocation(200, 100);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addWorkout() {
        workoutLinkedList.add(new Workout(workoutNameText.getText()));
        userTextArea.setText("");

        for (Workout workout : workoutLinkedList) {
            userTextArea.append(workout.getWorkoutName() + "\n");
        }
    }

    private void saveCollection() {

    }

    private void addExercise() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String workoutName = workoutNameText.getText();
        Workout workout = new Workout(workoutName);
    }
}
