package persistence;

import model.Exercise;
import model.Workout;
import model.WorkoutCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
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
    void testReaderGeneralWorkoutCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkoutCollection.json");
        try {
            WorkoutCollection wc = reader.read();
            assertEquals("My workout collection", wc.getName());
            List<Workout> workouts = wc.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout("workout1", workouts.get(0));
            checkWorkout("workout2",workouts.get(1));

            assertEquals(1, workouts.get(0).getExercises().size());
            assertEquals(1, workouts.get(1).getExercises().size());
            assertEquals("e1", workouts.get(0).getExercises().getFirst().getExercise());
            assertEquals("e2", workouts.get(1).getExercises().getFirst().getExercise());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
