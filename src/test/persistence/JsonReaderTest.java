package persistence;

import model.Exercise;
import model.Workout;
import model.WorkoutCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code from JsonReaderTest in JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Workout w = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }
    @Test
    void testReaderEmptyWorkout() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkout.json");
        try {
            Workout w = reader.read();
            assertEquals("My workout", w.getWorkoutName());
            assertEquals("n/a", w.getWorkoutLevel());
            assertEquals(0, w.getExercises().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkout() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkout.json");
        try {
            Workout w = reader.read();
            assertEquals("My workout", w.getWorkoutName());
            assertEquals("advanced", w.getWorkoutLevel());
            List<Exercise> exercises = w.getExercises();
            assertEquals(2, exercises.size());
            checkExercise("abs", 2, exercises.get(0));
            checkExercise("butt", 3, exercises.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
