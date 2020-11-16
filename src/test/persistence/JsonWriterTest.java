package persistence;

import exception.InvalidLevelException;
import model.Exercise;
import model.Workout;
import model.WorkoutCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Unit tests for JsonWriter
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Workout w = new Workout("My workout");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkoutCollection() {
        try {
            WorkoutCollection wc = new WorkoutCollection("My workout collection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkoutCollection.json");
            writer.open();
            writer.write(wc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkoutCollection.json");
            wc = reader.read();
            assertEquals("My workout collection", wc.getName());
            assertEquals(0, wc.getWorkouts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkoutCollection() {
        try {
            WorkoutCollection wc = new WorkoutCollection("My workout collection");
            Workout workout1 = new Workout("workout1");
            Workout workout2 = new Workout("workout2");
            workout1.setWorkoutLevel("beginner");
            wc.addWorkout(workout1);
            wc.addWorkout(workout2);
            Exercise exercise1 = new Exercise("e1", 1);
            Exercise exercise2 = new Exercise("e2", 2);
            workout1.addExercise(exercise1);
            workout2.addExercise(exercise2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkoutCollection.json");
            writer.open();
            writer.write(wc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkoutCollection.json");
            wc = reader.read();
            assertEquals("My workout collection", wc.getName());
            List<Workout> workouts = wc.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout("workout1", "beginner", workouts.get(0));
            checkWorkout("workout2", "n/a", workouts.get(1));

            checkExercise("e1", 1, workout1.getExercises().getFirst());
            checkExercise("e2", 2, workout2.getExercises().getFirst());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidLevelException e) {
            fail("Invalid Level exception should not have been thrown");
        }
    }
}
