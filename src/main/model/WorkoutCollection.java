package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a collection of a list of workouts
public class WorkoutCollection implements Writable {
    private String name;
    private List<Workout> collection;

    // MODIFIES: sets workout collection as an empty list
    // EFFECTS: constructs an empty list of workouts
    public WorkoutCollection(String collectionName) {
        this.name = collectionName;
        this.collection = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds workout to the list collection
    public void addWorkout(Workout workout) {
        collection.add(workout);
    }

    // EFFECTS: returns the names of all the workouts in the collection
    public ArrayList<String> getListOfWorkouts() {
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            nameList.add(collection.get(i).getWorkoutName());
        }
        return nameList;
    }

    // EFFECTS: returns an unmodifiable list of workouts in this collection
    public List<Workout> getWorkouts() {
        return Collections.unmodifiableList(collection);
    }

    // EFFECTS: returns the amount of workouts in the collection
    public int numWorkouts() {
        return collection.size();
    }

    // REQUIRES: collection is not empty
    // EFFECTS: returns specific workout from the collection given the workout name, if workout name is not found
    //          returns the first workout name in the collection
    public Workout getWorkout(String workoutName) {
        for (int i = 0; i < collection.size(); i++) {
            if (workoutName.equals(collection.get(i).getWorkoutName())) {
                return collection.get(i);
            }
        }
        return collection.get(0);
    }

    public boolean isWorkoutInCollection(String workoutName) {
        for (int i = 0; collection.size() > i; i++) {
            if (workoutName.equals(collection.get(i).getWorkoutName())) {
                return true;
            }
        }
        return false;
    }

    // code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Collection",name);
        json.put("workouts", workoutsToJson());
        return json;
    }

    // EFFECTS: returns workouts in this collection as a JSON array
    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout w : collection) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }
}
