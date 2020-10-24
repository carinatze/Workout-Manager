package persistence;

import model.Exercise;
import model.Workout;
import model.WorkoutCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
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

//    @Test
//    void testWriterEmptyWorkout() {
//        try {
//            Workout w = new Workout("My workout");
//            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkout.json");
//            writer.open();
//            writer.write(w);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkout.json");
//            w = reader.readW();
//            assertEquals("My workout", w.getWorkoutName());
//            assertEquals("n/a", w.getWorkoutLevel());
//            assertEquals(0, w.getExercises().size());
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }

    @Test
    void testWriterEmptyWorkoutCollection() {
        try {
            WorkoutCollection wc = new WorkoutCollection("My workout collection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkoutCollection.json");
            writer.open();
            writer.write(wc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkoutCollection.json");
            wc = reader.readC();
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
            wc.addWorkout(workout1);
            wc.addWorkout(workout2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkoutCollection.json");
            writer.open();
            writer.write(wc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkoutCollection.json");
            wc = reader.readC();
            assertEquals("My workout collection", wc.getName());
            List<Workout> workouts = wc.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout("workout1", workouts.get(0));
            checkWorkout("workout2", workouts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

//    @Test
//    void testWriterGeneralWorkout() {
//        try {
//            Workout w = new Workout("My workout");
//            w.setWorkoutLevel("beginner");
//            w.addExercise(new Exercise("abs", 2));
//            w.addExercise(new Exercise("butt", 4));
//            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkout.json");
//            writer.open();
//            writer.write(w);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkout.json");
//            w = reader.readW();
//            assertEquals("My workout", w.getWorkoutName());
//            assertEquals("beginner", w.getWorkoutLevel());
//            List<Exercise> exercises = w.getExercises();
//            assertEquals(2, exercises.size());
//            checkExercise("abs", 2, exercises.get(0));
//            checkExercise("butt", 4, exercises.get(1));
//
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
}
