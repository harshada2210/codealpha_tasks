package com.demo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordCounter {
    private JFrame frame;
    private JTextArea textArea;
    private JButton countButton;
    private JLabel resultLabel;

    public WordCounter() {
        // Creation of frame
        frame = new JFrame("Word Count Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Creating the textArea
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(240, 248, 255)); // Light blue background
        textArea.setForeground(new Color(0, 0, 139)); // Dark blue text
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Creating the Button
        countButton = new JButton("Count Words");
        countButton.setBackground(new Color(100, 149, 237)); // Cornflower blue
        countButton.setForeground(Color.WHITE); // White text
        countButton.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(countButton, BorderLayout.SOUTH);

        // Create the result label
        resultLabel = new JLabel("Word Count: 0");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(new Color(34, 139, 34)); // Forest green
        frame.add(resultLabel, BorderLayout.NORTH);

        // Adding action listener to the button
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                int wordCount = countWords(text);
                resultLabel.setText("Word Count: " + wordCount);
            }
        });

        // Making the frame visible
        frame.setVisible(true);
    }

    // Method to count words
    private int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordCounter());
    }
}