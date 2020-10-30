package persistence;

import model.Exercise;
import model.Workout;

import static org.junit.jupiter.api.Assertions.assertEquals;

// code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents JsonTest class
public class JsonTest {
    protected void checkWorkout(String name, String level, Workout workout) {
        assertEquals(name, workout.getWorkoutName());
        assertEquals(level, workout.getWorkoutLevel());
    }

    protected void checkExercise(String exerciseName, Integer reps, Exercise exercise) {
        assertEquals(exerciseName, exercise.getExerciseName());
        assertEquals(reps, exercise.getReps());
    }
}
