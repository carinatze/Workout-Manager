package ui;

import exception.InvalidLevelException;

import java.io.FileNotFoundException;

// testing push and commit
// main user interface for workout manager
public class Main {
    public static void main(String[] args) throws InvalidLevelException {
        try {
            new WorkoutManager();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
