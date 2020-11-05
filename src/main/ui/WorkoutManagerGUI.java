package ui;

import model.Exercise;
import model.Workout;
import model.WorkoutCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// citation: https://www.youtube.com/watch?v=iE8tZ0hn2Ws
// gui class for workout manager
public class WorkoutManagerGUI extends JFrame implements ActionListener {

    private JTextArea userTextArea;
    private JLabel workoutNameLabel;
    private JLabel exerciseNameLabel;
    private JLabel exerciseRepsLabel;

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel gridPanel;
    private JPanel workoutPanel;
    private JPanel exercisePanel;
    private JSplitPane splitPane;

    private JButton addWorkoutButton;
    private JButton addExerciseButton;
    private JButton saveCollectionButton;
    private JButton loadCollectionButton;

    private JTextField workoutNameText;
    private JTextField exerciseNameText;
    private JTextField exerciseRepsText;

    private JFrame frame;
    private JList<Workout> workoutList;
    private DefaultListModel<Workout> model;

    private static final String JSON_STORE = "./data/workout.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Workout Instance Data:
    private WorkoutCollection collection = new WorkoutCollection("My workout collection");

    // EFFECTS: gui constructor for workout manager
    public WorkoutManagerGUI() {
        frame = new JFrame("Workout Manager");
        workoutList = new JList<>();
        model = new DefaultListModel<>();
        workoutPanel = new JPanel();
        exercisePanel = new JPanel();
        splitPane = new JSplitPane();

        workoutList.setModel(model);
        //model.addElement(new Workout());

        splitPane.setLeftComponent(workoutPanel);
        splitPane.setRightComponent(exercisePanel);
        workoutPanel.add(new JLabel("Workouts"));
        exercisePanel.add(new JLabel("Exercises"));

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        workoutNameLabel = new JLabel("workout name:");
        workoutNameText = new JTextField(8);
        exerciseNameLabel = new JLabel("exercise name:");
        exerciseNameText = new JTextField(8);
        exerciseRepsLabel = new JLabel("reps:");
        exerciseRepsText = new JTextField(4);

        addWorkoutButton = new JButton("add workout");
        addExerciseButton = new JButton("add exercise");
        saveCollectionButton = new JButton("save");
        loadCollectionButton = new JButton("load");

        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridPanel = new JPanel(new GridLayout(2, 1));

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

        panel3.add(saveCollectionButton);
        panel3.add(loadCollectionButton);

        gridPanel.add(panel1);
        gridPanel.add(panel2);
        gridPanel.add(panel3);

        add(gridPanel, BorderLayout.SOUTH);
        add(splitPane, BorderLayout.CENTER);

        addWorkoutButton.setActionCommand("add workout");
        addExerciseButton.setActionCommand("add exercise");
        saveCollectionButton.setActionCommand("save");
        loadCollectionButton.setActionCommand("load");

        addWorkoutButton.addActionListener(this);
        addExerciseButton.addActionListener(this);
        saveCollectionButton.addActionListener(this);
        loadCollectionButton.addActionListener(this);
    }

    public static void main(String[] args) {
        WorkoutManagerGUI app = new WorkoutManagerGUI();
        app.setVisible(true);
        app.setSize(600, 400);
        app.setLocation(200, 100);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Workout w = new Workout(workoutNameText.getText());
        int repsStringToInt = Integer.parseInt(exerciseRepsText.getText());
        Exercise exercise = new Exercise(exerciseNameText.getText(), repsStringToInt);

        if (actionEvent.getActionCommand().equals("add workout")) {

            collection.addWorkout(w);
            w.addExercise(exercise);
            userTextArea.setText("");
            userTextArea.append(w.getWorkoutName() + w.getExercises() + "\n");

        } else if (actionEvent.getActionCommand().equals("add exercise")) {
            for (int i = 0; i < collection.numWorkouts(); i++) {
                if (w.getWorkoutName() == workoutNameText.getText()) {
                    w.addExercise(exercise);
                    userTextArea.append(w.getWorkoutName() + w.getExercises() + "\n");
                }
            }
        } else if (actionEvent.getActionCommand().equals("save")) {
            try {
                jsonWriter.open();
                jsonWriter.write(collection);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        } else if (actionEvent.getActionCommand().equals("load")) {
            try {
                collection = jsonReader.read();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }
}
