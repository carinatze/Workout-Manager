package model;

import org.json.JSONObject;
import persistence.Writable;

// code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents an exercise with the name of the exercise and number of repetitions of the exercise
public class Exercise implements Writable {
    private String exerciseName;
    private int reps;

    // REQUIRES: reps > 0
    // MODIFIES: this
    // EFFECTS: constructs a new exercise with a name and number of repetitions for the exercise
    public Exercise(String exercise, int reps) {
        this.exerciseName = exercise;
        this.reps = reps;
    }

    // REQUIRES: exercise is not empty
    // EFFECTS: returns exercise and repetition of exercises in format: "reps exercise"
    public String toString() {
        String repsString = Integer.toString(reps);
        return repsString + " " + exerciseName;
    }

    // EFFECTS: return number of repetitions for the exercise
    public int getReps() {
        return reps;
    }

    // EFFECTS: return exercise
    public String getExerciseName() {
        return exerciseName;
    }

    @Override
    // key words for Exercise toJson
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("exercise", exerciseName);
        json.put("reps", reps);
        return json;
    }
}
