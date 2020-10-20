package persistence;

import model.Workout;
import model.Exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkExercise(String name, Integer reps, Exercise exercise) {
        assertEquals(name, exercise.getExercise());
        assertEquals(reps, exercise.getReps());
    }
}
