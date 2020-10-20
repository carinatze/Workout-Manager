package persistence;

import model.Exercise;
import model.Workout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// code modified from JasonReader in JsonSerializationDemo
// Represents a reader that reads workout from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Workout read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkout(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout from JSON object and returns it
    private Workout parseWorkout(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Workout w = new Workout(name);
        addExercises(w, jsonObject);
        return w;
    }

    // EFFECTS: parses workout level from JSON object and returns it
    private void parseWorkoutLevel(Workout w, JSONObject jsonObject) {
        String level = jsonObject.getString("level");
        System.out.println(jsonObject.getString("level"));
        w.setWorkoutLevel(level);
    }

    // MODIFIES: w
    // EFFECTS: parses exercises from JSON object and adds them to workout
    private void addExercises(Workout w, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(w, nextExercise);
        }
    }

    // MODIFIES: w
    // EFFECTS: parses exercise from JSON object and adds it to workout
    private void addExercise(Workout w, JSONObject jsonObject) {
        String name = jsonObject.getString("exercise");
//        System.out.println(jsonObject);
//        System.out.println(jsonObject.get("reps"));
        int reps = (int) jsonObject.get("reps");
        Exercise exercise = new Exercise(name, reps);
        w.addExercise(exercise);
    }
}
