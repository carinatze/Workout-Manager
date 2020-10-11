package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseWorkoutTest {
    private Workout testWorkout;
    private Exercise testExercise1;
    private Exercise testExercise2;

    @BeforeEach
    public void runBefore() {
        testWorkout = new Workout("workout");
        testExercise1 = new Exercise("exercise1", 1);
        testExercise2 = new Exercise("exercise2", 2);
    }

    @Test
    public void testExerciseConstructor() {
        assertEquals("exercise1", testExercise1.getExercise());
        assertEquals(1, testExercise1.getReps());
        assertFalse(testExercise1.isDoneExercise());
    }

    @Test
        // converts exercise into string
    public void testToString() {
        assertEquals("1 exercise1", testExercise1.toString());
    }

    @Test
    // checks exercise as complete
    public void testFinished(){
        assertFalse(testExercise1.isDoneExercise());
        testExercise1.finished();
        assertTrue(testExercise1.isDoneExercise());
    }

    @Test
    // checks exercise as incomplete
    public void testNotFinished(){
        assertFalse(testExercise1.isDoneExercise());
        testExercise1.notFinished();
        assertFalse(testExercise1.isDoneExercise());
    }

    @Test
    // new constructor with no exercises
    public void testWorkoutConstructor() {
        assertEquals("workout", testWorkout.getWorkoutName());
        assertEquals("n/a", testWorkout.getWorkoutLevel());
        assertEquals(0, testWorkout.getExercises().size());
    }

    @Test
        // adds one exercise to empty workout
    public void testAddExerciseOne() {
        testWorkout.addExercise(testExercise1);
        LinkedList<Exercise> allExercises = new LinkedList<>();
        allExercises.add(testExercise1);
        assertEquals(allExercises, testWorkout.getExercises());
    }

    @Test
    // adds 3 exercises to an empty workout
    public void testAddExerciseMultiple() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        testWorkout.addExercise(testExercise1);

        LinkedList<Exercise> allExercises = new LinkedList<>();
        allExercises.add(testExercise1);
        allExercises.add(testExercise2);
        allExercises.add(testExercise1);

        assertEquals(3, allExercises.size());
    }

    @Test
    // gets one incomplete exercise with it being the only exercise in the list
    public void testGetIncompleteExercisesOnlyOneElement() {
        testWorkout.addExercise(testExercise1);
        testExercise1.notFinished();
        assertFalse(testExercise1.isDoneExercise());

        LinkedList<Exercise> allExercises = new LinkedList<>();
        allExercises.add(testExercise1);

        assertEquals(allExercises, testWorkout.getIncompleteExercises());
    }

    @Test
    // gets one incomplete exercise in a list of two exercises
    public void testGetIncompleteExercisesMultipleElementsOne() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);

        LinkedList<Exercise> allExercises = new LinkedList<>();
        allExercises.add(testExercise1);
        allExercises.add(testExercise2);

        assertEquals(allExercises, testWorkout.getExercises());

        testExercise1.notFinished();
        testExercise2.finished();

        assertFalse(testExercise1.isDoneExercise());
        assertTrue(testExercise2.isDoneExercise());

        LinkedList<Exercise> notFinishedExercises = new LinkedList<>();
        notFinishedExercises.add(testExercise1);

        assertEquals(notFinishedExercises, testWorkout.getIncompleteExercises());
    }

    @Test
    // gets more than one incomplete exercise
    public void testGetIncompleteExercisesMultipleElementsTwo() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        Exercise testExercise3 = new Exercise("three", 10);
        testWorkout.addExercise(testExercise3);

        LinkedList<Exercise> allExercises = new LinkedList<>();
        allExercises.add(testExercise1);
        allExercises.add(testExercise2);
        allExercises.add(testExercise3);

        assertEquals(allExercises, testWorkout.getExercises());

        testExercise1.notFinished();
        testExercise2.finished();
        testExercise3.notFinished();

        assertFalse(testExercise1.isDoneExercise());
        assertTrue(testExercise2.isDoneExercise());
        assertFalse(testExercise3.isDoneExercise());

        LinkedList<Exercise> notFinishedExercises = new LinkedList<>();
        notFinishedExercises.add(testExercise1);
        notFinishedExercises.add(testExercise3);

        assertEquals(notFinishedExercises, testWorkout.getIncompleteExercises());
    }

    @Test
    // sets workout level for a workout
    public void testSetWorkoutLevel(){
        assertEquals("n/a", testWorkout.getWorkoutLevel());
        testWorkout.setWorkoutLevel("beginner");
        assertEquals("beginner", testWorkout.getWorkoutLevel());
    }

}
