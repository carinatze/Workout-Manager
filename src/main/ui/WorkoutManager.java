package ui;

import model.WorkoutCollection;
import model.Exercise;
import model.Workout;

import java.util.Scanner;

// code based on TellerApp from AccountNotRobust and InfoManager from FitLifeGymChain
// Workout manager application
public class WorkoutManager {
    private static final String SEE_ALL_WORKOUTS_COMMAND = "see";
    private static final String ADD_WORKOUT_COMMAND = "add";
    private static final String SELECT_WORKOUT_COMMAND = "select";
    private static final String RATE_WORKOUT_COMMAND = "rate";
    private static final String SEE_EXERCISES_COMMAND = "all";
    private static final String SEE_INCOMPLETE_EXERCISES_COMMAND = "incomplete";
    private static final String ADD_EXERCISE_COMMAND = "add exercise";

    private WorkoutCollection collection;
    private Scanner input;
    private Workout workout;

    // EFFECTS: runs the workout manager application
    public WorkoutManager() {
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
                processExerciseCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processExerciseCommand(String command) {
        if (command.equals(SEE_ALL_WORKOUTS_COMMAND)) {
            doAllWorkouts();
        } else if (command.equals(SELECT_WORKOUT_COMMAND)) {
            doSelectedWorkout();
        } else if (command.equals(ADD_WORKOUT_COMMAND)) {
            doAddWorkout();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes workouts
    public void init() {
        collection = new WorkoutCollection();
        input = new Scanner(System.in);
    }

    //EFFECTS: prints instructions to use workout manager
    private void displayMenu() {
        System.out.println("\nYou can request the following information:\n");
        System.out.println("Enter '" + SEE_ALL_WORKOUTS_COMMAND + "' to view all workouts in the collection");
        System.out.println("Enter '" + ADD_WORKOUT_COMMAND + "' to add a workout to the collection");
        System.out.println("Enter '" + SELECT_WORKOUT_COMMAND + "' to select a specific workout in the collection");
    }

    private void displayWorkoutMenu(Workout workout) {
        System.out.println("Enter '" + RATE_WORKOUT_COMMAND + "' to rate the level of " + workout.getWorkoutName());
        System.out.println("Enter '" + ADD_EXERCISE_COMMAND + "' to add an exercise to " + workout.getWorkoutName());
        System.out.println("Enter '" + SEE_EXERCISES_COMMAND + "' to view all the exercises in "
                + workout.getWorkoutName());
        System.out.println("Enter '" + SEE_INCOMPLETE_EXERCISES_COMMAND + "' to view all incomplete exercises in "
                + workout.getWorkoutName());
        processExerciseCommand(workout);
    }

    private void processExerciseCommand(Workout workout) {
        String str = input.next();

        if (str.equals(RATE_WORKOUT_COMMAND)) {
            doRateWorkout(workout);
        } else if (str.equals(ADD_EXERCISE_COMMAND)) {
            doAddExercise(workout);
        } else if (str.equals(SEE_EXERCISES_COMMAND)) {
            printWorkoutExercises(workout);
        } else if (str.equals(SEE_INCOMPLETE_EXERCISES_COMMAND)) {
            printIncompleteWorkoutExercises(workout);
        } else {
            System.out.println("Sorry, I didn't understand that command. Please try again.");
        }
    }

    private void doAddExercise(Workout workout) {
        System.out.println("Please enter the name of of your new exercise.");
        String str = input.next();
        if (str.length() > 0) {
            System.out.println("Please enter the number of reps for:" + str);
            String reps = input.next();
            Integer repsInt = Integer.parseInt(reps);

            Exercise newExercise = new Exercise(str, repsInt);
            workout.addExercise(newExercise);
            System.out.println(reps + " " + str + " has been added to " + workout.getWorkoutName());
        }
    }

    private void doAddWorkout() {
        System.out.println("Enter the name of your new workout");
        String str = input.next();

        if (str.length() > 0) {
            Workout newWorkout = new Workout(str);
            collection.addWorkout(newWorkout);
            displayWorkoutMenu(newWorkout);
        }
    }

    private void doSelectedWorkout() {
        System.out.println("Indicate which workout you wish to select: [workout name]");
        String str = input.next();
        for (int i = 0; collection.length() > i; i++) {
            if (str == collection.getWorkout(str).getWorkoutName()) {
                System.out.println("You have selected: " + collection.getWorkout(str).getWorkoutName());
                this.workout = collection.getWorkout(str);
                displayWorkoutMenu(workout);
            }
        }
    }

    private void doAllWorkouts() {
        System.out.println("All Workouts: " + collection.getListOfWorkouts());
    }

    private void printWorkoutExercises(Workout workout) {
        System.out.println("All Exercises: " + workout.getExercises());
    }

    private void printIncompleteWorkoutExercises(Workout workout) {
        System.out.println("All Exercises: " + workout.getIncompleteExercises());
    }

    private void doRateWorkout(Workout workout) {
        System.out.println("set " + workout.getWorkoutName() + " level as either: beginner, intermediate, or advanced");
        String str = input.next();
        if (str == "beginner") {
            workout.setWorkoutLevel("beginner");
        } else if (str == "intermediate") {
            workout.setWorkoutLevel("intermediate");
        } else {
            workout.setWorkoutLevel("advanced");
        }
        System.out.println(workout.getWorkoutName() + " has been set to level: " + str);
    }
}
