package model;


// Represents an exercise with a name of the exercise and number of repetitions
public class Exercise {
    private final String exercise;
    private final int reps;
    private boolean state;

    // REQUIRES: reps > 0
    // EFFECTS: constructs a new exercise with the name of exercise, amount of repetitions, and not yet completed state
    // of completion
    public Exercise(String exercise, int reps) {
        this.exercise = exercise;
        this.reps = reps;
        this.state = false;
    }

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

    // EFFECTS: returns true if exercise is completed, false otherwise
    public boolean isDoneExercise() {
        return state;
    }

    // MODIFIES: this
    // EFFECTS: marks exercise as complete
    public void finished() {
        state = true;
    }

    // MODIFIES: this
    // EFFECTS: marks exercise as incomplete
    public void notFinished() {
        state = false;
    }

}
