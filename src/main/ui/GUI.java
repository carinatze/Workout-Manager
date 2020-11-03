package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// gui class for workout manager
public class GUI implements ActionListener {

    private JFrame frame;
    private JLabel label;
    private JPanel panel;
    private JButton addWorkoutButton;
    private JButton saveCollectionButton;
    private JButton loadCollectionButton;

    // EFFECTS: gui constructor for workout manager
    public GUI() {
        frame = new JFrame();

        addWorkoutButton = new JButton("add workout");
        saveCollectionButton = new JButton("save collection");
        loadCollectionButton = new JButton("load collection");

        addWorkoutButton.addActionListener(this);
//        label = new JLabel();

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(addWorkoutButton);
        panel.add(loadCollectionButton);
        panel.add(saveCollectionButton);

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

    }
}
