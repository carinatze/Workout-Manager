package ui;

import java.io.FileNotFoundException;

// main class for workout manager
public class Main {
    public static void main(String[] args) {
        try {
            new WorkoutManager();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
