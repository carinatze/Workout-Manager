package ui;

import model.Exercise;
import model.Workout;

import java.util.Scanner;

// code based on TellerApp from AccountNotRobust and InfoManager from FitLifeGymChain
// Workout manager application
public class WorkoutManager {
    private static final String SEE_EXERCISES_COMMAND = "see e";
    private static final String SEE_INCOMPLETE_EXERCISES_COMMAND = "see ie";
    private static final String ADD_EXERCISE_COMMAND = "add e";
    private static final String EXERCISE_NAME_COMMAND = "n";
    private static final String RATE_WORKOUT_COMMAND = "rate";
    private static final String VIEW_WORKOUT_INFO_COMMAND = "see rating";
    private static final String QUIT_COMMAND = "quit";

    private Workout workout;
    private Scanner input;
    private String workoutName;


    // EFFECTS: runs the teller application
    public WorkoutManager() {
        runTeller();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTeller() {
        boolean keepGoing = true;
        String command = null;

        init(workoutName);

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

    public void init(String workoutName) {
        this.workout = new Workout(workoutName);
        input = new Scanner(System.in);
    }

    //EFFECTS: prints instructions to use workout manager
    private void displayMenu() {
        System.out.println("\nYou can request the following information:\n");
        System.out.println("Enter '" + ADD_EXERCISE_COMMAND + "' to add an exercise to " + workout.getWorkoutName());
        System.out.println("Enter '" + SEE_EXERCISES_COMMAND + "' to view all the exercises in "
                + workout.getWorkoutName());
        System.out.println("Enter '" + SEE_INCOMPLETE_EXERCISES_COMMAND + "' to view all incomplete exercises in "
                + workout.getIncompleteExercises());
        System.out.println("Enter '" + RATE_WORKOUT_COMMAND + "' to rate the level of " + workout.getWorkoutName());
        System.out.println("Enter '" + VIEW_WORKOUT_INFO_COMMAND + "' to view " + workout.getWorkoutName()
                + " information.");
        System.out.println("To quit at any time, enter '" + QUIT_COMMAND + "'.");
    }

    //EFFECTS: stops receiving user input
    public void endProgram() {
        System.out.println("Quitting...");
        input.close();
    }

    private void printAddExercise(Workout workout) {
        System.out.println("Enter '" + EXERCISE_NAME_COMMAND + "' to create a new exercise");
        String str = getUserInputString();
        if (str.length() > 0 && str.equals(EXERCISE_NAME_COMMAND)) {
            System.out.println("Please enter the number of reps for:" + str);
            String reps = getUserInputString();
            createNewExercise(str, reps);
            System.out.println(reps + " " + str + " has been added to " + workout.getWorkoutName());
        }
    }

    private void createNewExercise(String name, String reps) {
        Integer repsInt = Integer.parseInt(reps);
        Exercise newExercise = new Exercise(name, repsInt);
        workout.addExercise(newExercise);
    }

    private void printWorkoutExercises(Workout workout) {
        System.out.println("All Exercises: " + workout.getExercises());
    }

    private void printIncompleteWorkoutExercises(Workout workout) {
        System.out.println("All Exercises: " + workout.getIncompleteExercises());
    }

    private void printRateWorkout(Workout workout) {
        System.out.println("rate " + workout.getWorkoutName() + " as either: beginner, intermediate, or advanced");
    }

    private void printWorkoutInfo(Workout workout) {
        System.out.println("Name: " + workout.getWorkoutName() + "Level: " + workout.getWorkoutLevel());
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals(ADD_EXERCISE_COMMAND)) {
            printAddExercise(workout);
        } else if (command.equals(SEE_EXERCISES_COMMAND)) {
            printWorkoutExercises(workout);
        } else if (command.equals(SEE_INCOMPLETE_EXERCISES_COMMAND)) {
            printIncompleteWorkoutExercises(workout);
        } else if (command.equals(RATE_WORKOUT_COMMAND)) {
            printRateWorkout(workout);
        } else if (command.equals(VIEW_WORKOUT_INFO_COMMAND)) {
            printWorkoutInfo(workout);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }

    private String getUserInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
            str = makePrettyString(str);
        }
        return str;
    }
}
