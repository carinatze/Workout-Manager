package ui;

import exception.InvalidLevelException;
import model.WorkoutCollection;
import model.Exercise;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// citation: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git and InfoManager from FitLifeGymChain
// Workout manager application
public class WorkoutManager {
    private static final String PRINT_WORKOUTS_COMMAND = "p";
    private static final String ADD_WORKOUT_COMMAND = "a";
    private static final String SELECT_WORKOUT_COMMAND = "sel";
    private static final String RATE_WORKOUT_COMMAND = "r";
    private static final String PRINT_EXERCISES_COMMAND = "p";
    private static final String ADD_EXERCISE_COMMAND = "a";
    private static final String GO_BACK_COMMAND = "b";

    private static final String JSON_STORE = "./data/workout.json";
    private WorkoutCollection collection;
    private Scanner input;
    private Workout workout;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the workout manager application
    public WorkoutManager() throws FileNotFoundException, InvalidLevelException {
        input = new Scanner(System.in);
        collection = new WorkoutCollection("My workout collection");
        workout = new Workout("new workout");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWorkoutManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWorkoutManager() throws InvalidLevelException {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws InvalidLevelException {
        if (command.equals(PRINT_WORKOUTS_COMMAND)) {
            printWorkouts();
        } else if (command.equals(SELECT_WORKOUT_COMMAND)) {
            selectWorkout();
        } else if (command.equals(ADD_WORKOUT_COMMAND)) {
            addWorkout();
        } else if (command.equals("l")) {
            loadCollection();
        } else if (command.equals("s")) {
            saveCollection();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command after they have selected or added a new workout
    private void processWorkoutCommand(Workout workout) {
        String str = input.next();

        if (str.equals(RATE_WORKOUT_COMMAND)) {
            rateWorkout(workout);
        } else if (str.equals(ADD_EXERCISE_COMMAND)) {
            addExercise(workout);
        } else if (str.equals(PRINT_EXERCISES_COMMAND)) {
            printWorkoutExercises(workout);
        } else if (str.equals(GO_BACK_COMMAND)) {
            System.out.println("going back to main menu");
        } else {
            System.out.println("Sorry, I didn't understand that command. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes workout collection
    public void init() {
        collection = new WorkoutCollection("My workout collection");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //EFFECTS: prints instructions to use workout manager
    private void displayMenu() {
        System.out.println("\nSelect from:\n");
        System.out.println(ADD_WORKOUT_COMMAND + " -> add a workout");
        System.out.println(PRINT_WORKOUTS_COMMAND + " -> print workouts");
        System.out.println("s -> save workout collection to file");
        System.out.println("l -> load workout collection from file");
        System.out.println(SELECT_WORKOUT_COMMAND + " -> select a workout to edit");
        System.out.println("q -> to quit program");
    }

    // EFFECTS: displays menu of options to user after they have selected or added a new workout
    private void displayWorkoutMenu(Workout workout) {
        System.out.println("\nChoose one of the following for your workout: " + "'" + workout.getWorkoutName() + "'\n");
        System.out.println(RATE_WORKOUT_COMMAND + " -> rate level of " + "'" + workout.getWorkoutName()
                + "'");
        System.out.println(ADD_EXERCISE_COMMAND + " -> add an exercise to " + "'" + workout.getWorkoutName()
                + "'");
        System.out.println(PRINT_EXERCISES_COMMAND + " -> print exercises in " + "'" + workout.getWorkoutName()
                + "'");
        System.out.println(GO_BACK_COMMAND + " -> to go back to main menu ");
        processWorkoutCommand(workout);
    }

    // MODIFIES: this
    // EFFECTS: adds a new exercise to the chosen workout, with exercise name and reps
    private void addExercise(Workout workout) {
        System.out.println("Please enter the name of of your new exercise.");
        String exerciseName = input.next();

        if (exerciseName.length() > 0) {
            System.out.println("Please enter the number of reps for: " + exerciseName);
            String reps = input.next();

            workout.addExercise(new Exercise(exerciseName, Integer.parseInt(reps)));

            System.out.println(reps + " " + exerciseName + " has been added to " + workout.getWorkoutName());
            displayWorkoutMenu(workout);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new workout to the collection
    private void addWorkout() {
        System.out.println("Please enter the name of your new workout:");
        String workoutName = input.next();

        if (workoutName.length() > 0) {
            Workout newWorkout = new Workout(workoutName);
            collection.addWorkout(newWorkout);
            displayWorkoutMenu(newWorkout);
        }
    }

    // REQUIRES: workout must be in the collection
    // EFFECTS: selects the specified workout user wishes to choose
    private void selectWorkout() {
        System.out.println("Indicate which workout you wish to select: [workout name]");
        String workoutName = input.next();
        if (collection.isWorkoutInCollection(workoutName)) {
            System.out.println("You have selected: " + workoutName);
            this.workout = collection.getWorkout(workoutName);
            displayWorkoutMenu(workout);
        }
    }

    // EFFECTS: prints all the workouts in the collection
    private void printWorkouts() {
        System.out.println("All Workouts: " + collection.getListOfWorkouts());
    }

    // EFFECTS: prints all the exercises in the specified workout
    private void printWorkoutExercises(Workout workout) {
        System.out.println("All Exercises in " + "'" + workout.getWorkoutName() + "':"
                + workout.getExercises().toString());
        displayWorkoutMenu(workout);
    }


    // MODIFIES: this
    // EFFECTS: rates a workout with the user input
    private void rateWorkout(Workout workout) {
        System.out.println("set " + workout.getWorkoutName() + " level as either: beginner, intermediate, or advanced");
        String level = input.next();
        try {
            workout.setWorkoutLevel(level);
            System.out.println(workout.getWorkoutName() + " has been set to level: " + level);
            displayWorkoutMenu(workout);
        } catch (InvalidLevelException e) {
            System.out.println(level + " is not a valid level");
        }
    }

    // citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: saves the collection to file
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("Saved " + collection.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads collection from file
    private void loadCollection() throws InvalidLevelException {
        try {
            collection = jsonReader.read();
            System.out.println("Loaded '" + collection.getName() + "' from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}