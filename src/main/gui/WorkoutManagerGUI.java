package gui;

import exception.InvalidLevelException;
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
// citation: https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git
// GUI class for workout manager
public class WorkoutManagerGUI extends JFrame implements ActionListener {

    private JPanel textPanel;
    private JPanel buttonPanel;
    private JPanel gridPanel;

    private ImageIcon workoutImage;
    private ImageIcon exerciseImage;
    private ImageIcon saveImage;
    private ImageIcon levelImage;

    private JButton addWorkoutButton;
    private JButton addExerciseButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton setLevelButton;

    private JTextField workoutLevelText;
    private JTextField workoutNameText;
    private JTextField exerciseNameText;
    private JTextField exerciseRepsText;

    private JList<String> workoutJList;
    private DefaultListModel<String> workoutModel;

    private JList<String> exerciseJList;
    private DefaultListModel<String> exerciseModel;

    private static final String JSON_STORE = "./data/workout.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Workout Instance Data:
    private WorkoutCollection collection;

    public WorkoutManagerGUI() {
        initializeGraphics();
        initializeFields();

        setUpButtons();
        setUpLabels();
        setUpLayout();

        selectionActionListener();
        setsActionCommands();
        addActionListeners();
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this WorkoutManager will operate, and tools to manipulate this
    //          workout manager
    private void initializeGraphics() {
        textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridPanel = new JPanel(new GridLayout(2, 1));
        add(new JScrollPane(workoutJList));
    }

    // MODIFIES: this
    // EFFECTS:  instantiates a new WorkoutCollection with name "My workout collection"
    //           instantiates jsonWriter, jsonReader stored in JSON_STORE
    //           instantiates workoutModel, exerciseModel with DefaultListModel
    //           instantiates workoutJList, exerciseJList with JList
    //           this method is called by the WorkoutManagerGUI constructor
    private void initializeFields() {
        collection = new WorkoutCollection("My workout collection");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        workoutModel = new DefaultListModel<>();
        workoutJList = new JList<>(workoutModel);
        exerciseModel = new DefaultListModel<>();
        exerciseJList = new JList<>(exerciseModel);
        workoutJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // EFFECTS: loads images
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        levelImage = new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "level.png");
        workoutImage = new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "workout.png");
        exerciseImage = new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "exercise.png");
        saveImage = new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "save.png");
    }

    // MODIFIES: this
    // EFFECTS: instantiates and add labels and text fields in JFrame window
    private void setUpLabels() {
        JLabel workoutLevelLabel = new JLabel("workout level:");
        workoutLevelText = new JTextField(8);
        JLabel workoutNameLabel = new JLabel("workout:");
        workoutNameText = new JTextField(8);
        JLabel exerciseNameLabel = new JLabel("exercise:");
        exerciseNameText = new JTextField(8);
        JLabel exerciseRepsLabel = new JLabel("reps:");
        exerciseRepsText = new JTextField(4);

        textPanel.add(workoutLevelLabel);
        textPanel.add(workoutLevelText);
        textPanel.add(workoutNameLabel);
        textPanel.add(workoutNameText);
        textPanel.add(exerciseNameLabel);
        textPanel.add(exerciseNameText);
        textPanel.add(exerciseRepsLabel);
        textPanel.add(exerciseRepsText);

        gridPanel.add(textPanel);
        gridPanel.add(buttonPanel);
        add(gridPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons in JFrame window
    private void setUpButtons() {
        loadImages();
        setLevelButton = new JButton(levelImage);
        setLevelButton.setText("set level");
        setLevelButton.setHorizontalTextPosition(JButton.RIGHT);

        addWorkoutButton = new JButton(workoutImage);
        addWorkoutButton.setText("add workout");
        addWorkoutButton.setHorizontalTextPosition(JButton.RIGHT);

        addExerciseButton = new JButton(exerciseImage);
        addExerciseButton.setText("add exercise");
        addExerciseButton.setVerticalTextPosition(JButton.BOTTOM);

        saveButton = new JButton(saveImage);
        saveButton.setText("save");
        saveButton.setVerticalTextPosition(JButton.BOTTOM);

        loadButton = new JButton("load");

        buttonPanel.add(setLevelButton);
        buttonPanel.add(addWorkoutButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(addExerciseButton);
    }

    // MODIFIES: this
    // EFFECTS:  sets action commands to buttons
    private void setsActionCommands() {
        setLevelButton.setActionCommand("set level");
        addWorkoutButton.setActionCommand("add workout");
        addExerciseButton.setActionCommand("add exercise");
        saveButton.setActionCommand("save");
        loadButton.setActionCommand("load");
    }

    // MODIFIES: this
    // EFFECTS: adds action listener to each button
    private void addActionListeners() {
        setLevelButton.addActionListener(this);
        addWorkoutButton.addActionListener(this);
        addExerciseButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    // EFFECTS: selects workout and displays its exercises
    public void selectionActionListener() {
        workoutJList.getSelectionModel().addListSelectionListener(e -> {
            String selected = workoutJList.getSelectedValue();
            int end = selected.indexOf(":");
            if (end != -1) {
                String subString = selected.substring(0,end);
                Workout w = collection.getWorkout(subString);
                exerciseModel.removeAllElements();
                for (int i = 0; i < w.getExercises().size(); i++) {
                    String exerciseString = w.getExercises().get(i).getReps() + " "
                            + w.getExercises().get(i).getExerciseName();
                    exerciseModel.addElement(exerciseString);
                }
            } else {
                Workout w = collection.getWorkout(selected);
                exerciseModel.removeAllElements();
                for (int i = 0; i < w.getExercises().size(); i++) {
                    String exerciseString = w.getExercises().get(i).getReps() + " "
                            + w.getExercises().get(i).getExerciseName();
                    exerciseModel.addElement(exerciseString);
                }
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: sets up split pane layout
    //          adds exercise and workout panel to GUI
    private void setUpLayout() {
        JPanel workoutPanel = new JPanel();
        JPanel exercisePanel = new JPanel();
        JSplitPane splitPane = new JSplitPane();

        splitPane.setLeftComponent(workoutPanel);
        workoutPanel.add(new JLabel("Workouts"));
        workoutPanel.add(workoutJList);

        splitPane.setRightComponent(exercisePanel);
        exercisePanel.add(new JLabel("Exercises"));
        exercisePanel.add(exerciseJList);

        add(splitPane, BorderLayout.CENTER);
    }

    // EFFECTS: performs actions of buttons
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "set level" :
                addWorkoutLevelAction();

                break;
            case "add workout":
                addWorkoutAction();

                break;
            case "add exercise":
                workoutJList.getSelectionModel().addListSelectionListener(e -> {
                    workoutJList.getSelectedValue();
                });
                addExerciseAction();

                break;
            case "save":
                saveAction();

                break;
            case "load":
                loadAction();

                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets level of the workout selected to one of: beginner, intermediate, or advanced
    public void addWorkoutLevelAction() {
        Workout w = collection.getWorkout(workoutJList.getSelectedValue());
        try {
            w.setWorkoutLevel(workoutLevelText.getText());
            workoutModel.setElementAt(w.getWorkoutName() + ":" + workoutLevelText.getText(),
                    workoutJList.getSelectedIndex());
        } catch (InvalidLevelException e) {
            System.out.println(workoutLevelText.getText() + " is an invalid workout level");
        }
        Toolkit.getDefaultToolkit().beep();
    }

    // MODIFIES: this
    // EFFECTS: adds new workout to the workout collection
    public void addWorkoutAction() {
        Workout w = new Workout(workoutNameText.getText());
        collection.addWorkout(w);
        workoutModel.addElement(workoutNameText.getText());
        Toolkit.getDefaultToolkit().beep();
    }

    // REQUIRES: workout != null
    // MODIFIES: this
    // EFFECTS: adds new exercise to selected workout
    public void addExerciseAction() {
        Workout w = collection.getWorkout(workoutJList.getSelectedValue());
        int repsStringToInt = Integer.parseInt(exerciseRepsText.getText());
        Exercise exercise = new Exercise(exerciseNameText.getText(), repsStringToInt);
        w.addExercise(exercise);
        exerciseModel.addElement(repsStringToInt + " " + exerciseNameText.getText());
        Toolkit.getDefaultToolkit().beep();
    }

    // MODIFIES: this
    // EFFECTS: saves GUI collection to JSON_STORE
    public void saveAction() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            Toolkit.getDefaultToolkit().beep();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved collection to GUI
    public void loadAction() {
        try {
            workoutModel.removeAllElements();
            exerciseModel.removeAllElements();
            collection = jsonReader.read();
            for (Workout w : collection.getWorkouts()) {
                workoutModel.addElement(w.getWorkoutName() + ":" + w.getWorkoutLevel());
            }
            for (Exercise e : collection.getWorkouts().get(0).getExercises()) {
                exerciseModel.addElement(e.getReps() + " " + e.getExerciseName());
            }
            Toolkit.getDefaultToolkit().beep();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
