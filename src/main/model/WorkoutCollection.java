package model;

import java.util.ArrayList;

public class WorkoutCollection {
    private final ArrayList<Workout> collection;

    // EFFECTS: constructs an empty list of workouts
    public WorkoutCollection() {
        this.collection = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds workout to the list of workouts
    public void addWorkout(Workout workout) {
        this.collection.add(workout);
    }

    // EFFECTS: returns the amount of workouts in the collection
    public int length() {
        return collection.size();
    }

    // EFFECTS: returns all the workouts in the collection
    public ArrayList<String> getListOfWorkouts() {
        ArrayList<String> nameList = new ArrayList();
        for (int i = 0; i < collection.size(); i++) {
            nameList.add(collection.get(i).getWorkoutName());
        }
        return nameList;
    }

    // REQUIRES: workouts is not empty && workoutName is in workouts
    // EFFECTS: returns specific workout from the collection
    public Workout getWorkout(String workoutName) {
        for (int i = 0; i < collection.size(); i++) {
            if (workoutName.equals(collection.get(i).getWorkoutName())) {
                return collection.get(i);
            }
        }
        return collection.get(0);
    }
}
