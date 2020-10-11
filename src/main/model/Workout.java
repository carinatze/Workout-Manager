package model;

import java.time.DayOfWeek;
import java.util.LinkedList;

// Represents a workout guide for a week having a guide name, level and exercises
public class Workout {
    private String workoutName;
    private String workoutLevel;
    private final LinkedList<Exercise> exercises;


    // EFFECTS: constructs a new workout with given name; sets workout level to "n/a"; exercise set to empty list
    // of workouts
    public Workout(String workoutName) {
        this.workoutName = workoutName;
        workoutLevel = "n/a";
        exercises = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds exercise to the workout
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    // REQUIRES: exercises is not empty
    // EFFECTS: returns all the exercises in the workout that are not completed
    public LinkedList<Exercise> getIncompleteExercises() {
        LinkedList<Exercise> exercisesToDo = new LinkedList<>();
        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).isDoneExercise() == false) {
                exercisesToDo.add(exercises.get(i));
            }
        }
        return exercisesToDo;
    }


    // REQUIRES: level > 0 && < 3
    // MODIFIES: this
    // EFFECTS: adds level to workout, level 1 being beginner, level 2 as intermediate, and level 3 as advanced
    public void setWorkoutLevel(String level) {
        this.workoutLevel = level;
    }

    // EFFECTS: returns level of the workout
    public String getWorkoutLevel() {
        return workoutLevel;
    }

    // REQUIRES: exercises cannot be empty
    // EFFECTS: returns list of all the exercises for the workout
    public LinkedList<Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: returns name of the workout
    public String getWorkoutName() {
        return workoutName;
    }
}
