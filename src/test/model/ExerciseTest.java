package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Exercise
public class ExerciseTest {
    private Exercise testExercise1;
    private Exercise testExercise2;

    @BeforeEach
    public void runBefore() {
        testExercise1 = new Exercise("exercise1", 1);
        testExercise2 = new Exercise("exercise2", 2);
    }

    @Test
    // tests exercise constructor
    public void testExerciseConstructor() {
        assertEquals("exercise1", testExercise1.getExerciseName());
        assertEquals(1, testExercise1.getReps());
    }

    @Test
    // tests changing exercise into a string
    public void testToString() {
        assertEquals("exercise2", testExercise2.getExerciseName());
        assertEquals("2 exercise2", testExercise2.toString());
    }

    @Test
    // tests get exercise name
    public void testGetExercise() {
        assertEquals("exercise2", testExercise2.getExerciseName());
    }

    @Test
    // tests get exercise reps
    public void testGetReps() {
        assertEquals(2, testExercise2.getReps());
    }



}

