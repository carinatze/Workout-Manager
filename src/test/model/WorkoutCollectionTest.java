package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutCollectionTest {
    private Workout testWorkout1;
    private Workout testWorkout2;
    private WorkoutCollection testAllWorkouts;

    @BeforeEach
    public void runBefore() {
        testWorkout1 = new Workout("workout");
        testWorkout2 = new Workout("workout2");
        testAllWorkouts = new WorkoutCollection("collection");
    }

    @Test
    // adds workout to the collection
    public void testWorkoutCollectionConstructor() {
        assertEquals("collection", testAllWorkouts.getName());
        assertEquals(0, testAllWorkouts.numWorkouts());
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(testWorkout1, testAllWorkouts.getWorkout("workout"));
    }

    @Test
    // adds workout to the collection
    public void testAddWorkoutOne() {
        assertEquals(0, testAllWorkouts.numWorkouts());
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(1, testAllWorkouts.numWorkouts());
        assertEquals(1, testAllWorkouts.getWorkouts().size());
    }

    @Test
    // adds multiple workouts to the collection
    public void testAddWorkoutMultiple() {
        assertEquals(0, testAllWorkouts.numWorkouts());
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(1, testAllWorkouts.numWorkouts());
        testAllWorkouts.addWorkout(testWorkout2);
        assertEquals(2, testAllWorkouts.numWorkouts());
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(3, testAllWorkouts.numWorkouts());
    }

    @Test
    // gets list workouts from the collection
    public void testGetListOfWorkoutOne() {
        assertEquals(0, testAllWorkouts.numWorkouts());
        testAllWorkouts.addWorkout(testWorkout1);

        ArrayList<String> result = new ArrayList<>();
        result.add("workout");

        assertEquals(result, testAllWorkouts.getListOfWorkouts());
        assertEquals(1, testAllWorkouts.numWorkouts());
    }

    @Test
    // gets list workouts from the collection
    public void testGetListOfWorkoutMultiple() {
        assertEquals(0, testAllWorkouts.numWorkouts());
        testAllWorkouts.addWorkout(testWorkout1);
        testAllWorkouts.addWorkout(testWorkout2);

        ArrayList<String> result = new ArrayList<>();
        result.add("workout");
        result.add("workout2");

        assertEquals(result, testAllWorkouts.getListOfWorkouts());
        assertEquals(2, testAllWorkouts.numWorkouts());
    }

    @Test
    // returns the first workout in collection when given a workout that is not in collection
    public void testGetWorkoutNone() {
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(1, testAllWorkouts.numWorkouts());

        assertEquals(testWorkout1, testAllWorkouts.getWorkout("workout2"));
    }

    @Test
    // returns the workout in the collection with only one workout
    public void testGetWorkoutOne() {
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(1, testAllWorkouts.numWorkouts());

        assertEquals(testWorkout1, testAllWorkouts.getWorkout(testWorkout1.getWorkoutName()));
    }

    @Test
    // returns the workout in the collection with multiple workouts
    public void testGetWorkoutMultiple() {
        testAllWorkouts.addWorkout(testWorkout1);
        testAllWorkouts.addWorkout(testWorkout2);
        testAllWorkouts.addWorkout(testWorkout1);
        testAllWorkouts.addWorkout(testWorkout1);
        assertEquals(4, testAllWorkouts.numWorkouts());

        assertEquals(testWorkout2, testAllWorkouts.getWorkout(testWorkout2.getWorkoutName()));
    }
}
