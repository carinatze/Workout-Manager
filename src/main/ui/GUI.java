package ui;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// citation: https://www.youtube.com/watch?v=iE8tZ0hn2Ws
// gui class for workout manager
public class GUI implements ActionListener {

    private JFrame frame;
    private JLabel workoutNameLabel;
    private JLabel workoutAddedLabel;
    private JPanel panel;
    private JButton addWorkoutButton;
    private JButton saveCollectionButton;
    private JButton loadCollectionButton;
    private JTextField workoutNameText;

    // EFFECTS: gui constructor for workout manager
    public GUI() {
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        addWorkoutButton = new JButton("add workout");
        saveCollectionButton = new JButton("save collection");
        loadCollectionButton = new JButton("load collection");

        panel.add(addWorkoutButton);

        workoutNameLabel = new JLabel("Enter new workout name");
        panel.add(workoutNameLabel);

        workoutNameText = new JTextField(20);
        workoutNameText.setBounds(100, 20, 165, 25);
        panel.add(workoutNameText);

        addWorkoutButton.addActionListener(this);

        workoutAddedLabel = new JLabel("");
        panel.add(workoutAddedLabel);


        panel.add(loadCollectionButton);
        panel.add(saveCollectionButton);

        loadCollectionButton.addActionListener(this);

        frame = new JFrame();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Workout Manager");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String workoutName = workoutNameText.getText();
        Workout workout = new Workout(workoutName);
        workoutAddedLabel.setText(workoutName + " was added to your collection!");

    }
}
