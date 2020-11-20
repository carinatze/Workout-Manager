package gui;

import javax.swing.*;

// Represents main graphical user interface for workout manager
public class MainGUI {
    public static void main(String[] args) {
        WorkoutManagerGUI app = new WorkoutManagerGUI();
        app.setVisible(true);
        app.setSize(550, 300);
        app.setLocation(200, 100);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
