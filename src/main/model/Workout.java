package model;

import java.util.LinkedList;

// Represents a workout guide with a workout name, level and list of exercises
public class Workout {
    private final String workoutName;
    private String workoutLevel;
    private final LinkedList<Exercise> exercises;

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
}
