package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class WorkoutCollection {
    private ArrayList<Workout> workouts;

    // EFFECTS: constructs an empty list of workouts
    public WorkoutCollection() {
        this.workouts = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds workout to the list of workouts
    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
    }
    // EFFECTS: returns the amount of workouts in the collection
    public int length() {
        return workouts.size();
    }

    // EFFECTS: returns all the workouts in the collection
    public ArrayList<Workout> getListOfWorkouts() {
        return workouts;
    }

    // REQUIRES: workouts is not empty && workout is in workouts
    // EFFECTS: returns specific workout from the collection
    public Workout getWorkout(String workoutName) {
        for (int i = 0; i < workouts.size(); i++) {
            if (workoutName == workouts.get(i).getWorkoutName()) {
                return workouts.get(i);
            }
        }
        return workouts.get(0);
    }
}
