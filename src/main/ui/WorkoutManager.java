package ui;

import model.WorkoutCollection;
import model.Exercise;
import model.Workout;

import java.util.Scanner;

// code based on TellerApp from AccountNotRobust and InfoManager from FitLifeGymChain
// Workout manager application
public class WorkoutManager {
    private static final String SEE_ALL_WORKOUTS_COMMAND = "view";
    private static final String ADD_WORKOUT_COMMAND = "add";
    private static final String SELECT_WORKOUT_COMMAND = "select";
    private static final String RATE_WORKOUT_COMMAND = "rate";
    private static final String SEE_EXERCISES_COMMAND = "see";
    private static final String ADD_EXERCISE_COMMAND = "add";
    private static final String GO_BACK_COMMAND = "back";

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
                processWorkoutCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processWorkoutCommand(String command) {
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
    // EFFECTS: processes user command after they have selected or added a new workout
    private void processWorkoutCommand(Workout workout) {
        String str = input.next();

        if (str.equals(RATE_WORKOUT_COMMAND)) {
            doRateWorkout(workout);
        } else if (str.equals(ADD_EXERCISE_COMMAND)) {
            doAddExercise(workout);
        } else if (str.equals(SEE_EXERCISES_COMMAND)) {
            printWorkoutExercises(workout);
        } else if (str.equals(GO_BACK_COMMAND)) {
            System.out.println("going back to main menu");
        } else {
            System.out.println("Sorry, I didn't understand that command. Please try again.");
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
        System.out.println(SEE_ALL_WORKOUTS_COMMAND + " -> view all workouts in the collection");
        System.out.println(ADD_WORKOUT_COMMAND + " -> add a workout to the collection");
        System.out.println(SELECT_WORKOUT_COMMAND + " -> select a specific workout in the collection");
        System.out.println("q -> to quit program");
    }

    // EFFECTS: displays menu of options to user after they have selected or added a new workout
    private void displayWorkoutMenu(Workout workout) {
        System.out.println("\nChoose one of the following for your workout: " + "'" + workout.getWorkoutName() + "'\n");
        System.out.println(RATE_WORKOUT_COMMAND + " -> rate the level of " + "'" + workout.getWorkoutName()
                + "'");
        System.out.println(ADD_EXERCISE_COMMAND + " -> add an exercise to " + "'" + workout.getWorkoutName()
                + "'");
        System.out.println(SEE_EXERCISES_COMMAND + " -> view all the exercises in " + "'" + workout.getWorkoutName()
                + "'");
        System.out.println(GO_BACK_COMMAND + " -> to go back to main menu ");
        processWorkoutCommand(workout);
    }

    // REQUIRES: name of the exercise cannot include spaces
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
        System.out.println("Enter the name of your new workout:");
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
        for (int i = 0; collection.length() > i; i++) {
            if (str.equals(collection.getWorkout(str).getWorkoutName())) {
                System.out.println("You have selected: " + collection.getWorkout(str).getWorkoutName());
                this.workout = collection.getWorkout(str);
                displayWorkoutMenu(workout);
            }
        }
    }

    // EFFECTS: prints all the workouts in the collection
    private void doAllWorkouts() {
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
}
