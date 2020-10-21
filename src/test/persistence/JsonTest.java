package persistence;

import model.WorkoutCollection;
import model.Workout;
import model.Exercise;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkExercise(String name, Integer reps, Exercise exercise) {
        assertEquals(name, exercise.getExercise());
        assertEquals(reps, exercise.getReps());
    }

    protected void checkWorkout(String name, String level, Workout workout) {
        assertEquals(name, workout.getWorkoutName());
        assertEquals(level, workout.getWorkoutLevel());
    }
}
