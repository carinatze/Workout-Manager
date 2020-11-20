package gui;

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
// GUI class for workout manager
public class WorkoutManagerGUI extends JFrame implements ActionListener {

    private JLabel workoutNameLabel;
    private JLabel exerciseNameLabel;
    private JLabel exerciseRepsLabel;

    private JPanel textPanel;
    private JPanel buttonPanel;
    private JPanel gridPanel;

    private ImageIcon workoutImage;
    private ImageIcon exerciseImage;
    private ImageIcon saveImage;

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

    private static final String JSON_STORE = "./data/workout.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Workout Instance Data:
    private WorkoutCollection collection = new WorkoutCollection("My workout collection");

    // EFFECTS: gui constructor for workout manager
    public WorkoutManagerGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

//        textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        gridPanel = new JPanel(new GridLayout(2, 1));
        initializeGraphics();

        initializeFields();

        workoutJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(workoutJList));

        setUpButtons();
        setUpLayout();
        selectionActionListener();
        setsActionCommands();

        addWorkoutButton.addActionListener(this);
        addExerciseButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this WorkoutManager will operate, and tools to manipulate this
    //          workout manager
    private void initializeGraphics() {
        textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridPanel = new JPanel(new GridLayout(2, 1));
    }

    // MODIFIES: this
    // EFFECTS:  instantiates workoutModel, exerciseModel with DefaultListModel
    //           instantiates workoutJList, exerciseJList with JList
    //           this method is called by the WorkoutManagerGUI constructor
    private void initializeFields() {
        workoutModel = new DefaultListModel<>();
        workoutJList = new JList<>(workoutModel);
        exerciseModel = new DefaultListModel<>();
        exerciseJList = new JList<>(exerciseModel);
    }

    // MODIFIES: this
    // EFFECTS:  sets action commands to buttons
    private void setsActionCommands() {
        addWorkoutButton.setActionCommand("add workout");
        addExerciseButton.setActionCommand("add exercise");
        saveButton.setActionCommand("save");
        loadButton.setActionCommand("load");
    }

    // EFFECTS: creates a new workout manager GUI
    public static void main(String[] args) {
        WorkoutManagerGUI app = new WorkoutManagerGUI();
        app.setVisible(true);
        app.setSize(550, 300);
        app.setLocation(200, 100);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: selects workout and displays its exercises
    public void selectionActionListener() {
//        workoutJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        add(new JScrollPane(workoutJList));

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

    // MODIFIES: this
    // EFFECTS: sets up layout and panels to GUI
    public void setUpLayout() {
        JPanel workoutPanel = new JPanel();
        JPanel exercisePanel = new JPanel();
        JSplitPane splitPane = new JSplitPane();

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

        gridPanel.add(textPanel);
        gridPanel.add(buttonPanel);

        add(gridPanel, BorderLayout.SOUTH);
        add(splitPane, BorderLayout.CENTER);
    }

    // EFFECTS: loads images
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        workoutImage = new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "workout.png");
        exerciseImage = new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "exercise.png");
        saveImage = new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "save.png");
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons, labels and text fields
    public void setUpButtons() {
        loadImages();
        workoutNameLabel = new JLabel("workout:");
        workoutNameText = new JTextField(8);
        exerciseNameLabel = new JLabel("exercise:");
        exerciseNameText = new JTextField(8);
        exerciseRepsLabel = new JLabel("reps:");
        exerciseRepsText = new JTextField(4);

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

        buttonPanel.add(addWorkoutButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(addExerciseButton);
    }

    // EFFECTS: performs actions of buttons
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
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

    // MODIFIES: collection
    // EFFECTS: adds workout to the workout collection
    public void addWorkoutAction() {
        Workout w = new Workout(workoutNameText.getText());
        collection.addWorkout(w);
        workoutModel.addElement(workoutNameText.getText());
        Toolkit.getDefaultToolkit().beep();
    }

    // REQUIRES: workout to be selected
    // MODIFIES: collection
    // EFFECTS: adds exercise to selected workout
    public void addExerciseAction() {
        Workout w = collection.getWorkout(workoutJList.getSelectedValue());
        int repsStringToInt = Integer.parseInt(exerciseRepsText.getText());
        Exercise exercise = new Exercise(exerciseNameText.getText(), repsStringToInt);
        w.addExercise(exercise);
        exerciseModel.addElement(repsStringToInt + " " + exerciseNameText.getText());
        Toolkit.getDefaultToolkit().beep();
    }

    // MODIFIES: this
    // EFFECTS: saves GUI collection
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
                workoutModel.addElement(w.getWorkoutName());
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
