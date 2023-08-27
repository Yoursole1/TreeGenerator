package me.yoursole.skeleton;

import me.yoursole.skeleton.generator.Generator;

import javax.swing.*;
import java.awt.*;

public class TreeGUI {
    public static void open() {
        JFrame frame = new JFrame("Tree Generator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JButton button = new JButton("Generate Tree");
        JSlider spreadSlider = new JSlider(0, 100, 50);
        JSlider splitSlider = new JSlider(0, 100, 50);
        JSlider branchSlider = new JSlider(0, 100, 50);
        JSlider pullSlider = new JSlider(0, 100, 50);
        JSlider branchHeightSlider = new JSlider(0, 100, 50);
        JLabel imageLabel = new JLabel();

        button.addActionListener(e -> {
            TreeSkeleton skeleton = new Generator(0.7f, 0.6f, 0.7f, 1f, 1).generate();
            Image treeImage = skeleton.render();
            ImageIcon icon = new ImageIcon(treeImage);
            imageLabel.setIcon(icon);
            frame.pack(); // To adapt the frame size to the new image
        });

        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.add(imageLabel);
        frame.setVisible(true);
    }
}
