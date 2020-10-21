package ui;

import model.WorkoutCollection;
import model.Exercise;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// code based on WorkRoomApp from JsonSerializationDemo
// code based on TellerApp from AccountNotRobust and InfoManager from FitLifeGymChain
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
    public WorkoutManager() throws FileNotFoundException {
        input = new Scanner(System.in);
        collection = new WorkoutCollection("My workout collection");
        workout = new Workout("new workout");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWorkoutManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWorkoutManager() {
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
                processWorkoutCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processWorkoutCommand(String command) {
        if (command.equals(PRINT_WORKOUTS_COMMAND)) {
            printWorkouts();
        } else if (command.equals(SELECT_WORKOUT_COMMAND)) {
            doSelectedWorkout();
        } else if (command.equals(ADD_WORKOUT_COMMAND)) {
            doAddWorkout();
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
            doRateWorkout(workout);
        } else if (str.equals(ADD_EXERCISE_COMMAND)) {
            doAddExercise(workout);
        } else if (str.equals(PRINT_EXERCISES_COMMAND)) {
            printWorkoutExercises(workout);
        } else if (str.equals(GO_BACK_COMMAND)) {
            System.out.println("going back to main menu");
        } else if (str.equals("s")) {
            saveWorkout();
        } else if (str.equals("l")) {
            loadWorkout();
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
        System.out.println("s -> save exercises to workout file");
        System.out.println(GO_BACK_COMMAND + " -> to go back to main menu ");
        processWorkoutCommand(workout);
    }

    // MODIFIES: this
    // EFFECTS: adds a new exercise to the chosen workout, with exercise name and reps
    private void doAddExercise(Workout workout) {
        System.out.println("Please enter the name of of your new exercise.");
        String str = input.next();

        if (str.length() > 0) {
            System.out.println("Please enter the number of reps for: " + str);
            String reps = input.next();

            Integer repsInt = Integer.parseInt(reps);
            Exercise newExercise = new Exercise(str, repsInt);
            workout.addExercise(newExercise);

            System.out.println(reps + " " + str + " has been added to " + workout.getWorkoutName());
            displayWorkoutMenu(workout);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new workout to the collection
    private void doAddWorkout() {
        System.out.println("Please enter the name of your new workout:");
        String str = input.next();

        if (str.length() > 0) {
            Workout newWorkout = new Workout(str);
            collection.addWorkout(newWorkout);
            displayWorkoutMenu(newWorkout);
        }
    }

    // REQUIRES: workout must be in the collection
    // EFFECTS: selects the specified workout user wishes to choose
    private void doSelectedWorkout() {
        System.out.println("Indicate which workout you wish to select: [workout name]");
        String str = input.next();
        for (int i = 0; collection.numWorkouts() > i; i++) {
            if (str.equals(collection.getWorkout(str).getWorkoutName())) {
                System.out.println("You have selected: " + collection.getWorkout(str).getWorkoutName());
                this.workout = collection.getWorkout(str);
                displayWorkoutMenu(workout);
            }
        }
    }

    // EFFECTS: prints all the workouts in the collection
    private void printWorkouts() {
        //System.out.println("All Workouts: " + collection.getListOfWorkouts());
        List<Workout> workouts = collection.getWorkouts();

        for (Workout w : workouts) {
            System.out.println(w);
        }
    }

    // EFFECTS: prints all the exercises in the specified workout
    private void printWorkoutExercises(Workout workout) {
        System.out.println("All Exercises in " + "'" + workout.getWorkoutName() + "':"
                + workout.getExercises().toString());
        displayWorkoutMenu(workout);
    }


    // MODIFIES: this
    // EFFECTS: rates a workout with the user input
    private void doRateWorkout(Workout workout) {
        System.out.println("set " + workout.getWorkoutName() + " level as either: beginner, intermediate, or advanced");
        String str = input.next();
        if (str.equals("beginner")) {
            workout.setWorkoutLevel("beginner");
        } else if (str.equals("intermediate")) {
            workout.setWorkoutLevel("intermediate");
        } else {
            workout.setWorkoutLevel("advanced");
        }
        System.out.println(workout.getWorkoutName() + " has been set to level: " + str);
        displayWorkoutMenu(workout);
    }

    // EFFECTS: saves the workout to file
    private void saveWorkout() {
        try {
            jsonWriter.open();
            jsonWriter.write(workout);
            jsonWriter.close();
            System.out.println("Saved " + workout.getWorkoutName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workout from file
    private void loadWorkout() {
        try {
            workout = jsonReader.readW();
            System.out.println("Loaded '" + workout.getWorkoutName() + "' from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

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
    private void loadCollection() {
        try {
            collection = jsonReader.readC();
            System.out.println("Loaded '" + collection.getName() + "' from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
