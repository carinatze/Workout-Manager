package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseWorkoutTest {
    private Workout testWorkout1;
    private Workout testWorkout2;
    private WorkoutCollection testAllWorkouts;
    private Exercise testExercise1;
    private Exercise testExercise2;

    @BeforeEach
    public void runBefore() {
        testWorkout1 = new Workout("workout");
        testWorkout2 = new Workout("workout2");
        testAllWorkouts = new WorkoutCollection();
        testExercise1 = new Exercise("exercise1", 1);
        testExercise2 = new Exercise("exercise2", 2);
    }

    @Test
    public void testExerciseConstructor() {
        assertEquals("exercise1", testExercise1.getExercise());
        assertEquals(1, testExercise1.getReps());
    }

    @Test
        // converts exercise into string
    public void testToString() {
        assertEquals("1 exercise1", testExercise1.toString());
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
    }

    @Test
    // sets workout level for a workout
    public void testSetWorkoutLevel(){
        assertEquals("n/a", testWorkout1.getWorkoutLevel());
        testWorkout1.setWorkoutLevel("beginner");
        assertEquals("beginner", testWorkout1.getWorkoutLevel());
    }

    @Test
    // adds workout to the collection
    public void testAddWorkout() {
        assertEquals(0, testAllWorkouts.length());
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(1, testAllWorkouts.length());
    }

    @Test
    // adds workout to the collection
    public void testGetListOfWorkouts() {
        assertEquals(0, testAllWorkouts.length());
        testAllWorkouts.addWorkout(testWorkout1);
        testAllWorkouts.addWorkout(testWorkout2);

        ArrayList<String> result = new ArrayList<>();
        result.add("workout");
        result.add("workout2");

        assertEquals(result, testAllWorkouts.getListOfWorkouts());
        assertEquals(2, testAllWorkouts.length());
    }

    @Test
    // returns the workout in the collection
    public void testGetWorkout() {
        testAllWorkouts.addWorkout(testWorkout1);
        testAllWorkouts.addWorkout(testWorkout2);
        assertEquals(testWorkout1, testAllWorkouts.getWorkout(testWorkout1.getWorkoutName()));
    }


}
