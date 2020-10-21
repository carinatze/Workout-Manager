package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.LinkedList;

// Represents a workout guide with a workout name, level and list of exercises
public class Workout implements Writable {
    private String workoutName;
    private String workoutLevel;
    private LinkedList<Exercise> exercises;

    // MODIFIES: this
    // EFFECTS: constructs a new workout with given name; sets workout level to "n/a"; list of exercises set to
    // empty list
    public Workout(String workoutName) {
        this.workoutName = workoutName;
        workoutLevel = "n/a";
        exercises = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the exercise to the workout
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    // REQUIRES: level is one of: beginner, intermediate, or advanced
    // MODIFIES: this
    // EFFECTS: sets workout level
    public void setWorkoutLevel(String level) {
        this.workoutLevel = level;
    }

    // EFFECTS: returns level of the workout
    public String getWorkoutLevel() {
        return workoutLevel;
    }

    // EFFECTS: returns list of all the exercises for the workout
    public LinkedList<Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: returns name of the workout
    public String getWorkoutName() {
        return workoutName;
    }

    //code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",workoutName);
        json.put("level", workoutLevel);
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns exercises in this workout as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
