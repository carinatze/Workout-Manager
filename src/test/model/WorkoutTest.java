package model;

import exception.InvalidLevelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Workout
public class WorkoutTest {
    private Workout testWorkout1;
    private Workout testWorkout2;
    private Exercise testExercise1;
    private Exercise testExercise2;

    @BeforeEach
    public void runBefore() {
        testWorkout1 = new Workout("workout");
        testWorkout2 = new Workout("workout2");
        testExercise1 = new Exercise("exercise1", 1);
        testExercise2 = new Exercise("exercise2", 2);
    }

    @Test
    // new constructor with no exercises
    public void testWorkoutConstructor() {
        assertEquals("workout", testWorkout1.getWorkoutName());
        assertEquals("n/a", testWorkout1.getWorkoutLevel());
        assertEquals(0, testWorkout1.getExercises().size());
    }

    @Test
    // adds one exercise to empty workout
    public void testAddExerciseOne() {
        testWorkout1.addExercise(testExercise1);
        LinkedList<Exercise> allExercises = new LinkedList<>();
        allExercises.add(testExercise1);
        assertEquals(allExercises, testWorkout1.getExercises());
        assertEquals(testExercise1, testWorkout1.getExercises().get(0));
    }

    @Test
    // adds 3 exercises to an empty workout
    public void testAddExerciseMultiple() {
        testWorkout1.addExercise(testExercise1);
        testWorkout1.addExercise(testExercise2);
        testWorkout1.addExercise(testExercise1);

        LinkedList<Exercise> allExercises = new LinkedList<>();
        allExercises.add(testExercise1);
        allExercises.add(testExercise2);
        allExercises.add(testExercise1);

        assertEquals(3, allExercises.size());
        assertEquals(testExercise1, testWorkout1.getExercises().get(0));
        assertEquals(testExercise2, testWorkout1.getExercises().get(1));
        assertEquals(testExercise1, testWorkout1.getExercises().get(2));
    }

    @Test
    // sets valid workout level for a workout
    public void testSetWorkoutLevel(){
        assertEquals("n/a", testWorkout2.getWorkoutLevel());
        assertEquals("workout2", testWorkout2.getWorkoutName());
        try {
            testWorkout2.setWorkoutLevel("beginner");
            assertEquals("beginner", testWorkout2.getWorkoutLevel());
            assertEquals("workout2", testWorkout2.getWorkoutName());
        } catch (InvalidLevelException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    // changes valid workout level to another valid level for a workout
    public void testSetWorkoutLevelChange(){
        assertEquals("n/a", testWorkout1.getWorkoutLevel());
        assertEquals("workout", testWorkout1.getWorkoutName());
        try {
            testWorkout1.setWorkoutLevel("beginner");
            assertEquals("beginner", testWorkout1.getWorkoutLevel());
        } catch (InvalidLevelException e) {
            fail("Exception should not have been thrown");
        }

        try {
            testWorkout1.setWorkoutLevel("advanced");
        } catch (InvalidLevelException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals("advanced", testWorkout1.getWorkoutLevel());
        assertEquals("workout", testWorkout1.getWorkoutName());
    }

    @Test
    public void testSetWorkoutLevelInvalidException() {
        assertEquals("n/a", testWorkout1.getWorkoutLevel());
        try {
            testWorkout1.setWorkoutLevel("invalid");
            fail("Exception should have been thrown");
        } catch (InvalidLevelException e) {
            // pass
        }
    }

    @Test
    public void testSetWorkoutLevelValidToInvalidException() {
        assertEquals("n/a", testWorkout1.getWorkoutLevel());
        try {
            testWorkout1.setWorkoutLevel("beginner");
            assertEquals("beginner", testWorkout1.getWorkoutLevel());
        } catch (InvalidLevelException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals("beginner", testWorkout1.getWorkoutLevel());
        try {
            testWorkout1.setWorkoutLevel("invalid");
            fail("Exception should have been thrown");
        } catch (InvalidLevelException e) {
            // pass
        }
    }
}
