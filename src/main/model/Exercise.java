package model;


// Represents an exercise with the name of the exercise and number of repetitions of the exercise
public class Exercise {
    private final String exercise;
    private final int reps;

    // REQUIRES: reps > 0
    // MODIFIES: this
    // EFFECTS: constructs a new exercise with a name and number of repetitions for the exercise
    public Exercise(String exercise, int reps) {
        this.exercise = exercise;
        this.reps = reps;
    }

    // REQUIRES: exercise is not empty
    // EFFECTS: returns exercise and repetition in format: "reps exercise"
    public String toString() {
        String repsString = Integer.toString(reps);
        return repsString + " " + exercise;
    }

    // EFFECTS: return number of repetitions for the exercise
    public int getReps() {
        return reps;
    }

    // EFFECTS: return exercise
    public String getExercise() {
        return exercise;
    }

}
