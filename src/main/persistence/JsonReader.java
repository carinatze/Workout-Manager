//package persistence;
//
//import model.Exercise;
//import model.Workout;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.stream.Stream;
//
//import model.WorkoutCollection;
//import org.json.*;
//
//// code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//// Represents a reader that reads workout from JSON data stored in file
//public class JsonReader {
//    private String source;
//
//    // EFFECTS: constructs reader to read from source file
//    public JsonReader(String source) {
//        this.source = source;
//    }
//
//    // EFFECTS: reads workout collection from file and returns it;
//    // throws IOException if an error occurs reading data from file
//    public WorkoutCollection readCollection() throws IOException {
//        String jsonData = readFile(source);
//        JSONObject jsonObject = new JSONObject(jsonData);
//        return parseWorkoutCollection(jsonObject);
//    }
//
//    // EFFECTS: reads workout from file and returns it;
//    // throws IOException if an error occurs reading data from file
//    public Workout readWorkout() throws IOException {
//        String jsonData = readFile(source);
//        JSONObject jsonObject = new JSONObject(jsonData);
//        return parseWorkout(jsonObject);
//    }
//
//    // EFFECTS: reads source file as string and returns it
//    private String readFile(String source) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//
//        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
//            stream.forEach(s -> contentBuilder.append(s));
//        }
//
//        return contentBuilder.toString();
//    }
//
//
//    // EFFECTS: parses workout from JSON object and returns it
//    private Workout parseWorkout(JSONObject jsonObject) {
//        String workoutName = jsonObject.getString("name");
//        Workout w = new Workout(workoutName);
//        addExercises(w, jsonObject);
//        //parseWorkoutLevel(w, jsonObject);
//        return w;
//    }
//
//    // EFFECTS: parses workout level from JSON object and returns it
//    private void parseWorkoutLevel(Workout w, JSONObject jsonObject) {
//        String level = jsonObject.getString("level");
//        w.setWorkoutLevel(level);
//    }
//
//    // EFFECTS: parses workout collection from JSON object and returns it
//    private WorkoutCollection parseWorkoutCollection(JSONObject jsonObject) {
//        String collectionName = jsonObject.getString("Collection");
//        WorkoutCollection wc = new WorkoutCollection(collectionName);
//        addWorkouts(wc, jsonObject);
//        return wc;
//    }
//
//    // MODIFIES: wc
//    // EFFECTS: parses workout from JSON object and adds it to the collection
//    private void addWorkouts(WorkoutCollection wc, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
//        for (Object json : jsonArray) {
//            JSONObject nextWorkout = (JSONObject) json;
//            addWorkout(wc, nextWorkout);
//        }
//    }
//
//    // MODIFIES: wc
//    // EFFECTS: parses workout from JSON object and adds it to workout
//    private void addWorkout(WorkoutCollection wc, JSONObject jsonObject) {
//        String workoutName = jsonObject.getString("name");
//        Workout workout = new Workout(workoutName);
//        wc.addWorkout(workout);
//    }
//
//    // MODIFIES: w
//    // EFFECTS: parses exercises from JSON object and adds them to workout
//    private void addExercises(Workout w, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
//        for (Object json : jsonArray) {
//            JSONObject exercises = (JSONObject)json;
//            JSONObject workout = (JSONObject)json;
//            String level = workout.getString("level");
//            w.setWorkoutLevel(level);
//            for (Object json1 : exercises.getJSONArray("exercises")) {
//                JSONObject nextExercise = (JSONObject) json1;
//                addExercise(w, nextExercise);
//            }
//        }
//    }
//
//    // MODIFIES: w
//    // EFFECTS: parses exercise from JSON object and adds it to workout
//    private void addExercise(Workout w, JSONObject jsonObject) {
//        String exerciseName = jsonObject.getString("exercise");
//        int reps = (int) jsonObject.get("reps");
//        Exercise exercise = new Exercise(exerciseName, reps);
//        w.addExercise(exercise);
//    }
//}

package persistence;

import exception.InvalidLevelException;
import model.Exercise;
import model.Workout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.WorkoutCollection;
import org.json.*;

// citation https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads workout from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkoutCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout collection from JSON object and returns it
    private WorkoutCollection parseWorkoutCollection(JSONObject jsonObject) {
        String collectionName = jsonObject.getString("name");
        WorkoutCollection wc = new WorkoutCollection(collectionName);
        addWorkouts(wc, jsonObject);
        return wc;
    }

    // MODIFIES: wc
    // EFFECTS: parses workout from JSON object and adds it to the collection
    private void addWorkouts(WorkoutCollection wc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            addWorkout(wc, nextWorkout);
        }
    }

    // MODIFIES: wc
    // EFFECTS: parses workout from JSON object and adds it to workout
    private void addWorkout(WorkoutCollection wc, JSONObject jsonObject) {
        String workoutName = jsonObject.getString("name");
        Workout workout = new Workout(workoutName);
        wc.addWorkout(workout);

        String level = jsonObject.getString("level");
        try {
            workout.setWorkoutLevel(level);
        } catch (InvalidLevelException e) {
            System.out.println(level + " is an invalid level");
        }
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        for (Object exercise : exercises) {
            JSONObject nextExercise = (JSONObject) exercise;
            addExercise(workout, nextExercise);
        }
    }

    //MODIFIES: wc, workout
    // EFFECTS: parses exercise from JSON object and adds it to workout in workout collection
    private void addExercise(Workout w, JSONObject jsonObject) {
        String exerciseName = jsonObject.getString("exercise");
        int reps = (int) jsonObject.get("reps");
        w.addExercise(new Exercise(exerciseName, reps));
    }
}