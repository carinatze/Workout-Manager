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

    private JLabel workoutNameLabel;
    private JLabel exerciseNameLabel;
    private JLabel exerciseRepsLabel;

    private JPanel textPanel;
    private JPanel buttonPanel;
    private JPanel gridPanel;
    private JPanel workoutPanel;
    private JPanel exercisePanel;

    private JSplitPane splitPane;

    private JButton addWorkoutButton;
    private JButton addExerciseButton;
    private JButton saveButton;
    private JButton loadButton;

    private JTextField workoutNameText;
    private JTextField exerciseNameText;
    private JTextField exerciseRepsText;

    private JList<String> workoutJList;
    private DefaultListModel<String> workoutModel;

    private JList<String> exerciseJList;
    private DefaultListModel<String> exerciseModel;

    private JFrame frame;

    private static final String JSON_STORE = "./data/workout.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Workout Instance Data:
    private WorkoutCollection collection = new WorkoutCollection("My workout collection");

    // EFFECTS: gui constructor for workout manager
    public WorkoutManagerGUI() {
        frame = new JFrame("Workout Manager");
        workoutPanel = new JPanel();
        exercisePanel = new JPanel();
        splitPane = new JSplitPane();
        workoutModel = new DefaultListModel<>();
        workoutJList = new JList<>(workoutModel);
        exerciseModel = new DefaultListModel<>();
        exerciseJList = new JList<>(exerciseModel);

        workoutJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(workoutJList));

        splitPane.setLeftComponent(workoutPanel);
        workoutPanel.add(new JLabel("Workouts"));
        workoutPanel.add(workoutJList);

        splitPane.setRightComponent(exercisePanel);
        exercisePanel.add(new JLabel("Exercises"));
        exercisePanel.add(exerciseJList);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        workoutNameLabel = new JLabel("workout:");
        workoutNameText = new JTextField(8);
        exerciseNameLabel = new JLabel("exercise:");
        exerciseNameText = new JTextField(8);
        exerciseRepsLabel = new JLabel("reps:");
        exerciseRepsText = new JTextField(4);

        addWorkoutButton = new JButton("add workout");
        addExerciseButton = new JButton("add exercise");
        saveButton = new JButton("save");
        loadButton = new JButton("load");

        textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridPanel = new JPanel(new GridLayout(2, 1));

        textPanel.add(workoutNameLabel);
        textPanel.add(workoutNameText);
        textPanel.add(exerciseNameLabel);
        textPanel.add(exerciseNameText);
        textPanel.add(exerciseRepsLabel);
        textPanel.add(exerciseRepsText);

        buttonPanel.add(addWorkoutButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(addExerciseButton);

        gridPanel.add(textPanel);
        gridPanel.add(buttonPanel);

        add(gridPanel, BorderLayout.SOUTH);
        add(splitPane, BorderLayout.CENTER);

        workoutJList.getSelectionModel().addListSelectionListener(e -> {
            workoutJList.getSelectedValue();
            Workout w = collection.getWorkout(workoutJList.getSelectedValue());
            exerciseModel.removeAllElements();
            for (int i = 0; i < w.getExercises().size(); i++) {
                String exerciseString = w.getExercises().get(i).getExerciseName()
                        + w.getExercises().get(i).getReps();
                exerciseModel.addElement(exerciseString);
            }
        });

        addWorkoutButton.setActionCommand("add workout");
        addExerciseButton.setActionCommand("add exercise");
        saveButton.setActionCommand("save");
        loadButton.setActionCommand("load");

        addWorkoutButton.addActionListener(this);
        addExerciseButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
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
        if (actionEvent.getActionCommand().equals("add workout")) {
            Workout w = new Workout(workoutNameText.getText());
            collection.addWorkout(w);
            workoutModel.addElement(workoutNameText.getText());

        } else if (actionEvent.getActionCommand().equals("add exercise")) {
            workoutJList.getSelectionModel().addListSelectionListener(e -> {
                workoutJList.getSelectedValue();
            });
            Workout w = collection.getWorkout(workoutJList.getSelectedValue());
            int repsStringToInt = Integer.parseInt(exerciseRepsText.getText());
            Exercise exercise = new Exercise(exerciseNameText.getText(), repsStringToInt);
            w.addExercise(exercise);
            exerciseModel.addElement(repsStringToInt + " " + exerciseNameText.getText());

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
