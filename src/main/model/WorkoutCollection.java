package model;

import java.util.ArrayList;

// Represents a collection of a list of workouts
public class WorkoutCollection {
    private ArrayList<Workout> collection;

    // MODIFIES: sets workout collection as an empty list
    // EFFECTS: constructs an empty list of workouts
    public WorkoutCollection() {
        this.collection = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds workout to the list collection
    public void addWorkout(Workout workout) {
        this.collection.add(workout);
    }

    // EFFECTS: returns the amount of workouts in the collection
    public int length() {
        return collection.size();
    }

    // REQUIRES: collection is not empty
    // EFFECTS: returns the names of all the workouts in the collection
    public ArrayList<String> getListOfWorkouts() {
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            nameList.add(collection.get(i).getWorkoutName());
        }
        return nameList;
    }

    // REQUIRES: collection is not empty
    // EFFECTS: returns specific workout from the collection given the workout name, if workout name is not found
    // returns the first workout name in the collection
    public Workout getWorkout(String workoutName) {
        for (int i = 0; i < collection.size(); i++) {
            if (workoutName.equals(collection.get(i).getWorkoutName())) {
                return collection.get(i);
            }
        }
        return collection.get(0);
    }
}
