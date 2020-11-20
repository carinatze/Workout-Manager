package persistence;

import exception.InvalidLevelException;
import model.Workout;
import model.WorkoutCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Unit tests for JsonReader
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkoutCollection wc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkoutCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkoutCollection.json");
        try {
            WorkoutCollection wc = reader.read();
            assertEquals("My workout collection", wc.getName());
            assertEquals(0, wc.getWorkouts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderInvalidLevelWorkoutCollection() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidLevelWorkoutCollection.json");
        try {
            WorkoutCollection wc = reader.read();
            assertEquals("My workout collection", wc.getName());
            assertEquals(1, wc.getWorkouts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkoutCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkoutCollection.json");
        try {
            WorkoutCollection wc = reader.read();
            assertEquals("My workout collection", wc.getName());
            List<Workout> workouts = wc.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout("workout1", "beginner", workouts.get(0));
            checkWorkout("workout2","n/a", workouts.get(1));

            assertEquals(1, workouts.get(0).getExercises().size());
            assertEquals(1, workouts.get(1).getExercises().size());
            assertEquals("e1", workouts.get(0).getExercises().getFirst().getExerciseName());
            assertEquals("e2", workouts.get(1).getExercises().getFirst().getExerciseName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
