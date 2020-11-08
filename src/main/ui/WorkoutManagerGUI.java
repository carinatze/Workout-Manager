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

    private JFrame frame;

    private JList<String> workoutJList;
    private DefaultListModel<String> workoutModel;

    private JList<String> exerciseJList;
    private DefaultListModel<String> exerciseModel;

    private static final String JSON_STORE = "./data/workout.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Workout Instance Data:
    private WorkoutCollection collection = new WorkoutCollection("My workout collection");

    // EFFECTS: gui constructor for workout manager
    public WorkoutManagerGUI() {
        frame = new JFrame("Workout Manager");
        workoutModel = new DefaultListModel<>();
        workoutJList = new JList<>(workoutModel);
        exerciseModel = new DefaultListModel<>();
        exerciseJList = new JList<>(exerciseModel);

        workoutJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(workoutJList));

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridPanel = new JPanel(new GridLayout(2, 1));

        setUpButtons();
        setUpLayout();
        selectionActionListener();

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
        app.setSize(500, 400);
        app.setLocation(200, 100);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void selectionActionListener() {
        workoutJList.getSelectionModel().addListSelectionListener(e -> {
            workoutJList.getSelectedValue();
            Workout w = collection.getWorkout(workoutJList.getSelectedValue());
            exerciseModel.removeAllElements();
            for (int i = 0; i < w.getExercises().size(); i++) {
                String exerciseString = w.getExercises().get(i).getReps() + " "
                        + w.getExercises().get(i).getExerciseName();
                exerciseModel.addElement(exerciseString);
            }
        });
    }

    // EFFECTS: sets up buttons, labels and text fields
    public void setUpButtons() {
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
    }

    // EFFECTS: sets up layout and panels to GUI
    public void setUpLayout() {
        workoutPanel = new JPanel();
        exercisePanel = new JPanel();
        splitPane = new JSplitPane();

        splitPane.setLeftComponent(workoutPanel);
        workoutPanel.add(new JLabel("Workouts"));
        workoutPanel.add(workoutJList);

        splitPane.setRightComponent(exercisePanel);
        exercisePanel.add(new JLabel("Exercises"));
        exercisePanel.add(exerciseJList);

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
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("add workout")) {
            addWorkoutAction();

        } else if (actionEvent.getActionCommand().equals("add exercise")) {
            workoutJList.getSelectionModel().addListSelectionListener(e -> {
                workoutJList.getSelectedValue();
            });
            addExerciseAction();

        } else if (actionEvent.getActionCommand().equals("save")) {
            saveAction();

        } else if (actionEvent.getActionCommand().equals("load")) {
            loadAction();
        }
    }

    // MODIFIES: collection
    // EFFECTS: adds workout to the workout collection
    public void addWorkoutAction() {
        Workout w = new Workout(workoutNameText.getText());
        collection.addWorkout(w);
        workoutModel.addElement(workoutNameText.getText());
    }

    // MODIFIES: collection
    // EFFECTS: adds exercise to selected workout
    public void addExerciseAction() {
        Workout w = collection.getWorkout(workoutJList.getSelectedValue());
        int repsStringToInt = Integer.parseInt(exerciseRepsText.getText());
        Exercise exercise = new Exercise(exerciseNameText.getText(), repsStringToInt);
        w.addExercise(exercise);
        exerciseModel.addElement(repsStringToInt + " " + exerciseNameText.getText());
    }

    // EFFECTS: saves GUI collection
    public void saveAction() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads previously saved collection to GUI
    public void loadAction() {
        try {
            workoutModel.removeAllElements();
            exerciseModel.removeAllElements();
            collection = jsonReader.read();
            for (Workout w : collection.getWorkouts()) {
                workoutModel.addElement(w.getWorkoutName());
            }
            for (Exercise e : collection.getWorkouts().get(0).getExercises()) {
                exerciseModel.addElement(e.getReps() + " " + e.getExerciseName());
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
