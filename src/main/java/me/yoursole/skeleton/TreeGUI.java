package me.yoursole.skeleton;

import me.yoursole.skeleton.generator.Generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TreeGUI {
    public static void open() {
        JFrame frame = new JFrame("Tree Generator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton button = new JButton("Generate Tree");
        JButton saveButton = new JButton("Save to File");
        saveButton.setMaximumSize(new Dimension(100, 30));  // Set maximum size for the Save button
        JCheckBox autoUpdate = new JCheckBox("Auto Update");

        String[] variableNames = {"Spread", "Split", "Branch", "Variability", "Branch Height"};

        JLabel[] labels = new JLabel[variableNames.length];
        JSlider[] sliders = new JSlider[variableNames.length];
        JLabel imageLabel = new JLabel();

        for (int i = 0; i < variableNames.length; i++) {
            sliders[i] = new JSlider(0, 100, 50);
            sliders[i].setName(variableNames[i]);
            labels[i] = new JLabel(variableNames[i] + ": 0.5");

            final int index = i;
            sliders[i].addChangeListener(e -> {
                labels[index].setText(sliders[index].getName() + ": " + sliders[index].getValue() / 100.0);
                if (autoUpdate.isSelected()) {
                    generateTree(sliders, imageLabel, frame);
                }
            });
            panel.add(labels[i]);
            panel.add(sliders[i]);
        }

        button.addActionListener(e -> generateTree(sliders, imageLabel, frame));

        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                BufferedImage img = new BufferedImage(imageLabel.getWidth(), imageLabel.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics g = img.createGraphics();
                imageLabel.paint(g);
                g.dispose();

                try {
                    ImageIO.write(img, "JPG", fileToSave);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.WEST);
        frame.add(button, BorderLayout.SOUTH);
        frame.add(saveButton, BorderLayout.EAST);
        frame.add(autoUpdate, BorderLayout.NORTH);
        frame.add(imageLabel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void generateTree(JSlider[] sliders, JLabel imageLabel, JFrame frame) {
        TreeSkeleton skeleton = new Generator(
                sliders[0].getValue() / 100f,
                sliders[1].getValue() / 100f,
                sliders[2].getValue() / 100f,
                sliders[3].getValue() / 100f,
                sliders[4].getValue() / 100f
        ).generate();

        Image treeImage = skeleton.render();
        ImageIcon icon = new ImageIcon(treeImage);
        imageLabel.setIcon(icon);
        frame.pack();
    }
}
